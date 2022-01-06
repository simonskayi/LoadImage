package com.kwekboss.movies.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kwekboss.movies.R


class MoviesAdapter(val movie: List<MoviesItem>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movie[position])
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(movies: MoviesItem) {

            val description = itemView.findViewById<TextView>(R.id.txt_main)
            val title = itemView.findViewById<TextView>(R.id.txt_second)
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            description.text = movies.description
            title.text = movies.title
            Glide.with(itemView.context).load(movies.image).centerCrop().into(image)
        }
    }


}