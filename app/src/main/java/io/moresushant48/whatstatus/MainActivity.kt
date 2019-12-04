package io.moresushant48.whatstatus

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileRefreshLayout: SwipeRefreshLayout

    private lateinit var fileNames: Array<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        fileRefreshLayout = findViewById(R.id.fileRefreshLayout)

        recyclerView = findViewById(R.id.recyclerImageView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        startApp()

        fileRefreshLayout.setOnRefreshListener {
            startApp()
        }
    }

    private fun startApp() {

        fileNames = GetStatuses().getStatusFiles()

        if (fileNames != null) {

            recyclerView.adapter = ImageAdapter(this, fileNames)
        }

        fileRefreshLayout.isRefreshing = false
    }
}