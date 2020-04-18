package io.moresushant48.whatstatus.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.moresushant48.whatstatus.*

class VideoFragment : Fragment(), OnStatusItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileRefreshLayout: SwipeRefreshLayout

    private lateinit var fileNames: Array<Uri>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.video_fragment, container, false)

        fileRefreshLayout = view.findViewById(R.id.fileRefreshLayout)

        recyclerView = view.findViewById(R.id.recyclerImageView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        context?.let { feedData(it) }

        fileRefreshLayout.setOnRefreshListener { context?.let { feedData(it) } }

        return view
    }

    private fun feedData(context: Context) {

        fileNames = GetStatuses().getStatusVideos(context)

        recyclerView.adapter = ImageAdapter(context, fileNames, this)
        recyclerView.scrollToPosition(
            context.getSharedPreferences(
                "rv",
                Context.MODE_PRIVATE
            ).getInt("pos", 0)
        )

        fileRefreshLayout.isRefreshing = false
    }

    override fun onStatusItemClick(position: Int) {

        val pos =
            (recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
        context?.getSharedPreferences("rv", Context.MODE_PRIVATE)?.edit()?.putInt("pos", pos)
            ?.apply()
        val i = Intent(context, ViewStatus::class.java)
        i.putExtra(
            "uri",
            MainActivity().BASE_PATH + fileNames[position]
        )
        startActivity(i)
    }

}