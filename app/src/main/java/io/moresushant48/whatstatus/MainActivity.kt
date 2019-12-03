package io.moresushant48.whatstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerImageView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)


        val images = ArrayList<Int>()
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)
        images.add(R.drawable.ic_launcher_foreground)

        recyclerView.adapter = ImageAdapter(images)

    }
}