import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

/**
 * Creates a large file filled with random content.
 */
abstract class GenerateRandomFile : DefaultTask() {

    @get:Input
    abstract val sizeInBytes : Property<Long>

    @get:OutputFile
    abstract val outputFile : RegularFileProperty

    @TaskAction
    fun action() {
        val file = File(outputFile.get().asFile.absolutePath)
        val sizeRequired = sizeInBytes.get()

        FileOutputStream(file).use { outputStream ->
            val bufferSize = 4096 // 4KB buffer
            val buffer = ByteArray(bufferSize)
            var bytesWritten: Long = 0

            while (bytesWritten < sizeRequired) {
                Random.nextBytes(buffer) // Fill the buffer with random bytes
                val bytesToWrite = (sizeRequired - bytesWritten).coerceAtMost(bufferSize.toLong()).toInt()
                outputStream.write(buffer, 0, bytesToWrite)
                bytesWritten += bytesToWrite
            }
        }
        logger.lifecycle("Generated random file: ${file.absolutePath} with size ${file.length()} bytes")
    }
}