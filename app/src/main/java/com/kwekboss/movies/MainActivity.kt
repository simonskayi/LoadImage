package com.kwekboss.movies


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.movies.models.MoviesAdapter
import com.kwekboss.movies.models.RetrofitInstance
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progressBar2)

       // Checking For Internet Connection
        if (hasInternet(this)) {
            // Loading an API GET function
            loadData()
        } else {
            val alert = AlertDialog.Builder(this)
                .setTitle("Internet Connection")
                .setIcon(R.drawable.ic_error_24)
                .setMessage("Kindly Turn On Your Mobile Internet")
                .setNegativeButton("Close") { _, _ -> finish() }
                .setPositiveButton("Retry") { _, _ -> connectionRestored() }
                .create()
            alert.show()
        }

    }

    private fun loadData() {
        progressBar.isVisible = true

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.retrofit.getRequest()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    withContext(Dispatchers.Main) {
                        progressBar.isVisible = false

                        recyclerView.apply {
                            val imageFetched = MoviesAdapter(data)
                            adapter = imageFetched
                            layoutManager = LinearLayoutManager(this@MainActivity)
                        }
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Unknown Error Occurred", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    private fun hasInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        /* if the android version is equal to M
        or greater we need to use the
        NetworkCapabilities to check what type of
         network has the internet connection
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        }
        // if the android version is below M
        else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun connectionRestored() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
