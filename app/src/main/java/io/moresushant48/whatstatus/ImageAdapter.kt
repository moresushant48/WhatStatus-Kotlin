package io.moresushant48.whatstatus

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ImageAdapter(private var context: Context, private var images: Array<Uri>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    private val BASE_PATH: String =
        Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(BASE_PATH + images[position])
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .centerCrop()
            .into(holder.imageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.grid_item_image)

    }

}