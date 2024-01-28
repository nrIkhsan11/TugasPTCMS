package com.nurikhsan.tugasptcms.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurikhsan.tugasptcms.adapter.AdapterMovies
import com.nurikhsan.tugasptcms.databinding.ActivityRatingMovieBinding
import com.nurikhsan.tugasptcms.utils.PrefAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingMovieBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarRating.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val accountId = PrefAuth(this).getSessionId()
        val sessionId = PrefAuth(this).getSessionId()

        if (accountId != null && sessionId != null){
            viewModel.getRatedMovies(accountId, sessionId).observe(this){ rated->
                if (rated != null){
                    val adapterMovies = AdapterMovies()
                    adapterMovies.submitList(rated)
                    binding.apply {
                        rvMovie.adapter = adapterMovies
                        rvMovie.layoutManager = LinearLayoutManager(this@RatingMovieActivity)
                    }
                }
            }
        }
    }
}