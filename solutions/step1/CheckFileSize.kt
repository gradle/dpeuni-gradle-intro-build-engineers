import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class CheckFileSize : DefaultTask() {

    @TaskAction
    fun action() {
        logger.lifecycle("hello")
    }
}