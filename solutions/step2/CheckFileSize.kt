import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CheckFileSize : DefaultTask() {

    @get:Input
    abstract val maxFileSizeInBytes : Property<Long>

    @get:InputFile
    abstract val fileToValidate : RegularFileProperty

    @TaskAction
    fun action() {
        val file = File(fileToValidate.get().asFile.absolutePath)
        val length : Long = file.length()

        if (length > maxFileSizeInBytes.get()) {
            logger.error("Input file: ${file.absolutePath} is too large")
        } else {
            logger.lifecycle("Input file: ${file.absolutePath} is acceptable size")
        }
    }
}