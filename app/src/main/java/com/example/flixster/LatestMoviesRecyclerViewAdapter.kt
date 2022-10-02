package com.example.flixster

import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id

class LatestMoviesRecyclerViewAdapter(
    private val movies: List<LatestMovies>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<LatestMoviesRecyclerViewAdapter.LatestMoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing, parent, false)
        return LatestMoviesViewHolder(view)
    }


    inner class LatestMoviesViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: LatestMovies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.tvMovieTitle) as TextView
        val mMovieOverview: TextView = mView.findViewById<View>(id.tvMovieDesc) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movieImageView) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    override fun onBindViewHolder(holder: LatestMoviesViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.movieTitle
        holder.mMovieOverview.text = movie.description

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/${movie.movieImage}")
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }

    }
    override fun getItemCount(): Int {
        return movies.size
    }

}