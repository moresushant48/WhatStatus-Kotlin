package io.moresushant48.whatstatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(var images: ArrayList<Int>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.imageView.setImageResource(images.get(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.grid_item_image)
        }
    }

}