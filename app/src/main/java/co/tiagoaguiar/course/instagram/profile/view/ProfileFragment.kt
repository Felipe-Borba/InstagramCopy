package co.tiagoaguiar.course.instagram.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.view.CustomDialog

class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.profile_rv)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)
        rv.adapter = PostAdapter()
    }

    private class PostAdapter() : Adapter<PostAdapter.PostViewHolder>() {
        private class PostViewHolder(itemView: View) : ViewHolder(itemView) {
            fun bind(image: Int) {
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageResource(image)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            return PostViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return 30
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(R.drawable.ic_insta_add)
        }
    }
}
