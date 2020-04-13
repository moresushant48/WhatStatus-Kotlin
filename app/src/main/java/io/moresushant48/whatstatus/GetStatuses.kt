package io.moresushant48.whatstatus

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class GetStatuses {

    private var path: String =
        Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses/"

    fun getStatusFiles(context: Context): Array<Uri> {

        val dir = File(path)
        val files = dir.listFiles()

        val fileNames = arrayOfNulls<Uri>(files.size)
        val allFiles = arrayListOf<String>()

        for ((index, file) in files.withIndex()) {

            allFiles.add(file.absolutePath)
            fileNames.set(index, file.name.toUri())
        }

        Log.e("*********** Getting ", " statuses")

        UploadService
            .enqueueWork(context, Intent().putExtra("files", allFiles))

        return fileNames.requireNoNulls()
    }
}