package com.nurikhsan.tugasptcms.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurikhsan.tugasptcms.adapter.AdapterMoviesPersonalList
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.PersonalListMovies
import com.nurikhsan.tugasptcms.databinding.ActivityDetailPersonalListBinding
import com.nurikhsan.tugasptcms.ui.watchlist.WatchlistViewModel
import com.nurikhsan.tugasptcms.utils.PrefAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPersonalListBinding
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPersonalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPersonalList.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val extras = intent.extras
        if (extras != null){
            val listId = extras.getString(LIST_ID)
            if (listId != null){
                showDetailPersonalList(listId)
            }
        }
    }

    private fun showDetailPersonalList(listId: String) {
        viewModel.detailPersonalList(listId).observe(this){
            if (it != null){
                binding.apply {
                    tvTitle.text = it.name
                    if (it.description.isEmpty()){
                        tvDesc.text = "-"
                    }else{
                        tvDesc.text = it.description
                    }
                }

                showListMoviePersonalList(it.items, it.id)
            }
        }
    }

    private fun showListMoviePersonalList(items: List<PersonalListMovies>, listId: String) {
        val adapterMovies = AdapterMoviesPersonalList()
        adapterMovies.submitList(items)
        binding.apply {
            rvMovie.adapter = adapterMovies
            rvMovie.layoutManager = LinearLayoutManager(this@DetailPersonalListActivity)
        }

        val sessionId = PrefAuth(this).getSessionId()
        adapterMovies.remove(object : AdapterMoviesPersonalList.Remove{
            override fun removeMovie(movieId: String) {
                val id = MovieId(movieId)
                if (sessionId != null){
                    viewModel.removeMovie(listId, sessionId, id).observe(this@DetailPersonalListActivity){
                        Toast.makeText(this@DetailPersonalListActivity, it?.statusMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    companion object{
        const val LIST_ID = "LIST_ID"
    }
}