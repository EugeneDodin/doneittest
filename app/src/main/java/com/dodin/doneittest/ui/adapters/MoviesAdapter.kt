package com.dodin.doneittest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dodin.doneittest.R
import com.dodin.doneittest.data.remote.BASE_POSTER_URL
import com.dodin.doneittest.data.remote.dto.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textTitle: TextView = view.findViewById(R.id.textTitle)
    private val imagePoseter: ImageView = view.findViewById(R.id.imagePoster)

    fun bind(item: Movie) {
        textTitle.text = item.title

        item.posterPath?.let {
            Picasso.get().load(BASE_POSTER_URL + it).into(imagePoseter)
        }
    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}