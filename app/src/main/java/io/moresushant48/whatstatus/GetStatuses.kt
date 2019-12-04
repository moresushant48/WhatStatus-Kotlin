package io.moresushant48.whatstatus

import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import java.io.File

class GetStatuses {

    private var path: String =
        Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses/"

    fun getStatusFiles(): Array<Uri> {

        val dir = File(path)
        val files = dir.listFiles()

        val fileNames = arrayOfNulls<Uri>(files.size)

        for ((index, file) in files.withIndex()) {

            fileNames.set(index, file.name.toUri())
        }

        return fileNames.requireNoNulls()
    }
}