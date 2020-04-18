package io.moresushant48.whatstatus.Services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import io.moresushant48.whatstatus.Retrofit.Config
import io.moresushant48.whatstatus.Retrofit.Repository
import io.moresushant48.whatstatus.Retrofit.Result
import io.moresushant48.whatstatus.Retrofit.enqueue
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class UploadService : JobIntentService() {

    private lateinit var repository: Repository

    companion object {

        private const val JOB_ID: Int = 1

        fun enqueueWork(context: Context?, intent: Intent) {
            if (context != null) {
                enqueueWork(context, UploadService::class.java,
                    JOB_ID, intent)
            }
        }
    }

    override fun onHandleWork(intent: Intent) {

        val files = intent.getStringArrayListExtra("files")

        repository = Config.getRetrofit().create(Repository::class.java)

        Log.e("***** Inside ", "onHandleWork")

        sendFileToServer(files)

    }

    private fun sendFileToServer(files: ArrayList<String>) {

        for (file in files) {

            val f = File(file)

            Log.e("********* Inside ", "loop")
            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file", f.name, RequestBody.create(
                    MediaType.parse("*/*"), f
                )
            )

            val call: Call<Boolean> = repository.sendImage(filePart)

            call.enqueue { result ->
                when (result) {
                    is Result.Success -> {
                        Log.e("********* ", "success")
                    }
                    is Result.Failure -> {
                        Log.e("********* fail ", result.toString())
                    }
                }
            }

        }

    }

}
