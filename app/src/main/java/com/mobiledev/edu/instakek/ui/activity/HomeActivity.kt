package com.mobiledev.edu.instakek.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.ui.adapter.PostsAdapter
import com.mobiledev.edu.instakek.utils.extentions.makeVisible

class HomeActivity : AppCompatActivity(), PostsAdapter.PostsAdapterOnClickHandler {

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }*/

    private var mRecyclerView: RecyclerView? = null
    private var mPostsAdapter: PostsAdapter? = null

//    private var mErrorMessageDisplay: TextView? = null

//    private var mLoadingIndicator: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mRecyclerView = findViewById(R.id.recyclerview_posts) as RecyclerView

//        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display) as TextView

        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)

        mRecyclerView!!.layoutManager = layoutManager

        mRecyclerView!!.setHasFixedSize(true)

        mPostsAdapter = PostsAdapter(this)

        mRecyclerView!!.adapter = mPostsAdapter

//        mLoadingIndicator = findViewById(R.id.pb_loading_indicator) as ProgressBar

        loadPostsData()
    }

    private fun loadPostsData() {
        showPostsDataView()

        // FetchWeatherTask().execute(location)
    }

    override fun onClick(weatherForDay: String) {
        val context = this
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show()
    }

    private fun showPostsDataView() {
        //mErrorMessageDisplay!!.makeInvisible()
        mRecyclerView!!.makeVisible()
    }

    private fun showErrorMessage() {
        mRecyclerView!!.makeVisible()
        //mErrorMessageDisplay!!.makeInvisible()
    }

    /*inner class FetchWeatherTask : AsyncTask<String, Void, Array<String>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            mLoadingIndicator!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String): Array<String>? {

            /* If there's no zip code, there's nothing to look up. */
            if (params.size == 0) {
                return null
            }

            val location = params[0]
            val weatherRequestUrl = NetworkUtils.buildUrl(location)

            try {
                val jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl)

                return OpenWeatherJsonUtils
                .getSimpleWeatherStringsFromJson(this@MainActivity, jsonWeatherResponse)

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        override fun onPostExecute(weatherData: Array<String>?) {
            mLoadingIndicator!!.visibility = View.INVISIBLE
            if (weatherData != null) {
                showPostsDataView()
                mPostsAdapter!!.setWeatherData(weatherData)
            } else {
                showErrorMessage()
            }
        }
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.forecast, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_refresh) {
            mPostsAdapter!!.setWeatherData(null)
            loadPostsData()
            return true
        }

        return super.onOptionsItemSelected(item)
    }*/

}
