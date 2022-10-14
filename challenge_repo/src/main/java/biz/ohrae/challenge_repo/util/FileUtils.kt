package biz.ohrae.challenge_repo.util

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {
    @Throws(IOException::class)
    fun createTemporalFileFrom(inputStream: InputStream?): File? {
        var targetFile: File? = null
        return if (inputStream == null) targetFile
        else {
            var read: Int
            val buffer = ByteArray(8 * 1024)
            targetFile = createTemporalFile()
            FileOutputStream(targetFile).use { out ->
                while (inputStream.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                out.flush()
            }
            targetFile
        }
    }

    private fun createTemporalFile(): File = File(Environment.getExternalStorageDirectory(), "tempPicture.jpg")
}