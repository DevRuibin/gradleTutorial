package license

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

class LicensePlugin: Plugin<Project>{
    override fun apply(project: Project){
        project.tasks.register("greeting"){ task ->
            task.doLast{
                println("Hello from plugin 'com.tutorial.greeting'")
            }
        }
        project.tasks.register("license", LicenseTask::class.java){ task ->
                    task.description = "Add a license header to source code"
                    task.group = "From license plugin"
        }
    }
}

abstract class LicenseTask : DefaultTask() {
    @get:Input
    val filename = project.rootDir.toString() + "/license.txt"

    @TaskAction
    fun action(){
        val licenseText = File(filename).readText()
        File(project.rootDir.toString()).walk().forEach {
            if(it.extension == "java"){
                val ins: InputStream = it.inputStream()
                val content = ins.readBytes().toString(Charset.defaultCharset())
                it.writeText(licenseText + content)
            }
        }
    }
}