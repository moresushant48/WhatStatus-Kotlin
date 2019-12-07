package io.moresushant48.whatstatus

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_view_status.*
import java.io.File

class ViewStatus : AppCompatActivity() {

    private lateinit var imgBackButton: ImageView
    private lateinit var imgStatusVideoPlaySign: ImageView

    private lateinit var statusImage: ImageView
    private lateinit var statusVideo: VideoView

    private lateinit var fileUriString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_status)

        imgStatusVideoPlaySign = findViewById(R.id.statusVideoPlaySign)
        imgBackButton = findViewById(R.id.imgBackButton)
        imgBackButton.setOnClickListener { finish() }

        statusImage = findViewById(R.id.statusImage)
        statusVideo = findViewById(R.id.statusVideo)

        fileUriString = intent.getStringExtra("uri")
        var file = File(fileUriString)
        var fileUri: Uri = FileProvider.getUriForFile(this@ViewStatus, "io.moresushant48.fileprovider", file)

        val mediaController = MediaController(this)

        if (isStatusAnImage()) {
            statusImage.visibility = View.VISIBLE
            Glide.with(this@ViewStatus)
                .load(fileUri)
                .into(statusImage)
        } else {

            statusImage.visibility = View.VISIBLE
            Glide.with(this@ViewStatus)
                .load(fileUri)
                .into(statusImage)

            statusVideoPlaySign.visibility = View.VISIBLE
            statusVideo.visibility = View.VISIBLE
            statusVideo.setVideoURI(fileUri)

            statusVideo.setMediaController(mediaController)
            mediaController.setAnchorView(statusVideo)
        }

        imgStatusVideoPlaySign.setOnClickListener {
            imgStatusVideoPlaySign.visibility = View.INVISIBLE
            statusImage.visibility = View.INVISIBLE
            statusVideo.start()
        }

        statusVideo.setOnCompletionListener {
            imgStatusVideoPlaySign.visibility = View.VISIBLE
            statusImage.visibility = View.VISIBLE
        }
    }

    private fun isStatusAnImage(): Boolean {

        return fileUriString.endsWith(".jpg")
    }

    override fun onBackPressed() {
        finish()
    }
}
