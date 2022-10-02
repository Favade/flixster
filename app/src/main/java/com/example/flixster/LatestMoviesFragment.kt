package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray


private const val API_KEY = "748f1b3dbb2a932ecb10d5cd907947e3"


class LatestMoviesFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.latest_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY
        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=748f1b3dbb2a932ecb10d5cd907947e3&language=en-US&page=1",
                params,
                object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                val moviesJson: String = resultsJSON.toString()
                val gson = Gson()
                val arrayMoviesType = object : TypeToken<List<LatestMovies>>() {}.type
                val models : List<LatestMovies> = gson.fromJson(moviesJson, arrayMoviesType)
                recyclerView.adapter = LatestMoviesRecyclerViewAdapter(models, this@LatestMoviesFragment)
                progressBar.setVisibility(View.GONE)
                // Look for this in Logcat:
                Log.d("LatestMoviesFragment", "response successful")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()
                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("LatestMoviesFragment", errorResponse)
                }
            }
        }]


    }

    override fun onItemClick(item: LatestMovies) {
        Toast.makeText(context, "test: " + item.movieTitle, Toast.LENGTH_LONG).show()
    }

}
