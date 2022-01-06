package com.kwekboss.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.movies.models.MoviesAdapter
import com.kwekboss.movies.models.RetrofitInstance
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        progressBar=findViewById(R.id.progressBar2)

        // Loading an API GET function
                 loadData()

    }

    private fun loadData(){
        progressBar.isVisible = true

        GlobalScope.launch (Dispatchers.IO){
            try
            {
                val response = RetrofitInstance.retrofit.getRequest()
                if (response.isSuccessful){
                    val data = response.body()!!
                    withContext(Dispatchers.Main){
                        progressBar.isVisible=false

                        recyclerView.apply {
                         val imageFetched = MoviesAdapter(data)
                            adapter = imageFetched
                            layoutManager = LinearLayoutManager(this@MainActivity)
                        }
                    }
                }

            } catch (e:Exception){

                withContext(Dispatchers.Main){
                    delay(1000L)
                    Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
 }
