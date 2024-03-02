package co.tiagoaguiar.course.instagram.post.view

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.course.instagram.R

class GalleryAdapter(private val onCLick: (Uri) -> Unit) : RecyclerView.Adapter<GalleryAdapter.PostViewHolder>() {

    var items: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Uri) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                itemView.context.contentResolver.loadThumbnail(image, Size(200, 200), null)
            } else {
                TODO("VERSION.SDK_INT < Q")
            }

            itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageBitmap(bitmap)
            itemView.setOnClickListener {
                onCLick.invoke(image)
            }
        }
    }
}