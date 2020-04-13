package io.moresushant48.whatstatus

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), OnStatusItemClickListener {

    private val BASE_PATH: String =
        Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses/"

    private val STORAGE_PERMISSION_REQUEST_CODE: Int = 1000
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

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(this, "Please allow Storage Permission.", Toast.LENGTH_LONG).show()
            } else askForPermission()
        } else {
            startApp()
        }

        fileRefreshLayout.setOnRefreshListener {
            startApp()
        }
    }

    override fun onStatusItemClick(position: Int) {

        val pos = (recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
        getSharedPreferences("rv", Context.MODE_PRIVATE).edit().putInt("pos", pos).apply()
        val i = Intent(this@MainActivity, ViewStatus::class.java)
        i.putExtra(
            "uri",
            BASE_PATH + fileNames[position]
        )
        startActivity(i)

    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    private fun startApp() {

        fileNames = GetStatuses().getStatusFiles(baseContext)

        if (fileNames != null) {
            recyclerView.adapter = ImageAdapter(this, fileNames, this)
            recyclerView.scrollToPosition(getSharedPreferences("rv", Context.MODE_PRIVATE).getInt("pos", 0))
        } else Toast.makeText(this, "No Statuses available.", Toast.LENGTH_LONG).show()

        fileRefreshLayout.isRefreshing = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startApp()
                else {
                    Toast.makeText(this, "App requires storage access.", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForPermission()
        } else startApp()
    }
}