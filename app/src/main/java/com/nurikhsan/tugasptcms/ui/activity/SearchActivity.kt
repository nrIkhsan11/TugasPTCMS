package com.nurikhsan.tugasptcms.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurikhsan.tugasptcms.adapter.AdapterMovies
import com.nurikhsan.tugasptcms.databinding.ActivitySearchBinding
import com.nurikhsan.tugasptcms.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchView.onActionViewExpanded()
        binding.toolbarSearch.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()) {
            val adapterMovie = AdapterMovies()
            viewModel.searchMovie(query).observe(this) { item ->
                adapterMovie.submitList(item)
                binding.apply {
                    rvMovie.adapter = adapterMovie
                    rvMovie.layoutManager = LinearLayoutManager(this@SearchActivity)
                }
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}