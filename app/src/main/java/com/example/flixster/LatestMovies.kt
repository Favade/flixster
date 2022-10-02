package com.example.flixster

import com.google.gson.annotations.SerializedName

class LatestMovies {
    @SerializedName("poster_path")
    var movieImage: String? = null

    @SerializedName("title")
    var movieTitle: String? = null

    @SerializedName("overview")
    var description: String? = null
}

