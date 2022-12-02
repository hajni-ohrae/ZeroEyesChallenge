package biz.ohrae.challenge_repo.util

import android.content.Context
import android.os.Build
import android.os.Environment
import me.echodev.resizer.Resizer
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {
    @Throws(IOException::class)
    fun createTemporalFileFrom(inputStream: InputStream?, context: Context): File? {
        var targetFile: File? = null
        return if (inputStream == null) targetFile
        else {
            var read: Int
            val buffer = ByteArray(8 * 1024)
            targetFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                createTemporalFile(context)
            } else {
                createTemporalFileLegacy()
            }
            FileOutputStream(targetFile).use { out ->
                while (inputStream.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                out.flush()
            }
            targetFile
        }
    }

    private fun createTemporalFile(context: Context): File = File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "tempPicture.jpg")
    private fun createTemporalFileLegacy(): File = File(Environment.getExternalStorageDirectory(), "tempPicture.jpg")


    @Throws(IOException::class)
    fun resizeFile(context: Context, imagePath: String): File {
        val originFile = File(imagePath)
        val outputPath = context.filesDir.absolutePath
        val resizedImage = Resizer(outputPath)
            .setTargetLength(1080)
            .setQuality(80)
            .setOutputFormat("JPEG")
            .setOutputFilename("resized_image")
            .setOutputDirPath(outputPath)
            .setSourceImage(originFile)
            .resizedFile

        Timber.d("resizedImage : ${resizedImage.absolutePath}")
        return resizedImage
    }
}