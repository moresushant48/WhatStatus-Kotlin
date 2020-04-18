package io.moresushant48.whatstatus

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private lateinit var fabStatusShare: FloatingActionButton

    private lateinit var fileUriString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_status)

        imgStatusVideoPlaySign = findViewById(R.id.statusVideoPlaySign)
        imgBackButton = findViewById(R.id.imgBackButton)
        imgBackButton.setOnClickListener { finish() }

        statusImage = findViewById(R.id.statusImage)
        statusVideo = findViewById(R.id.statusVideo)

        fabStatusShare = findViewById(R.id.fabStatusShare)

        fileUriString = intent.getStringExtra("uri")
        val file = File(fileUriString)
        val fileUri: Uri = FileProvider.getUriForFile(this@ViewStatus, "io.moresushant48.fileprovider", file)

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

        fabStatusShare.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.putExtra(Intent.EXTRA_STREAM, fileUri)
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            if (isStatusAnImage())
                i.type = "image/*"
            else
                i.type = "video/*"

            startActivity(Intent.createChooser(i, "Choose"))
        }

    }

    private fun isStatusAnImage(): Boolean {

        return fileUriString.endsWith(".jpg")
    }

    override fun onBackPressed() {
        finish()
    }
}
