package io.moresushant48.whatstatus

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_status.*

class ViewStatus : AppCompatActivity() {

    private lateinit var imgBackButton: ImageView
    private lateinit var imgStatusVideoPlaySign: ImageView

    private lateinit var statusImage: ImageView
    private lateinit var statusVideo: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_status)

        imgStatusVideoPlaySign = findViewById(R.id.statusVideoPlaySign)
        imgBackButton = findViewById(R.id.imgBackButton)
        imgBackButton.setOnClickListener { finish() }

        statusImage = findViewById(R.id.statusImage)
        statusVideo = findViewById(R.id.statusVideo)

        val uri = intent.getStringExtra("uri")

        val mediaController = MediaController(this)

        if (intent.getStringExtra("uri").endsWith(".jpg")) {
            statusImage.visibility = View.VISIBLE
            Glide.with(this@ViewStatus)
                .load(uri)
                .into(statusImage)
        } else {

            statusImage.visibility = View.VISIBLE
            Glide.with(this@ViewStatus)
                .load(uri)
                .into(statusImage)

            statusVideoPlaySign.visibility = View.VISIBLE
            statusVideo.visibility = View.VISIBLE
            statusVideo.setVideoURI(Uri.parse(uri))

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

    override fun onBackPressed() {
        finish()
    }
}
