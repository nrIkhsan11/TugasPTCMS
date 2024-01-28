package com.nurikhsan.tugasptcms.ui.activity

import android.content.Intent
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.nurikhsan.tugasptcms.R
import com.nurikhsan.tugasptcms.adapter.AdapterAddToPersonalList
import com.nurikhsan.tugasptcms.adapter.AdapterTrailers
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.RatingRequest
import com.nurikhsan.tugasptcms.databinding.ActivityDetailMovieBinding
import com.nurikhsan.tugasptcms.databinding.BottomSheetAddRatingBinding
import com.nurikhsan.tugasptcms.databinding.BottomSheetAddToPersonalListBinding
import com.nurikhsan.tugasptcms.ui.watchlist.WatchlistViewModel
import com.nurikhsan.tugasptcms.utils.Constant.SHARE
import com.nurikhsan.tugasptcms.utils.Constant.URL_BACKDROP_PATH
import com.nurikhsan.tugasptcms.utils.Constant.URL_POSTER
import com.nurikhsan.tugasptcms.utils.DateFormat
import com.nurikhsan.tugasptcms.utils.PrefAuth
import com.nurikhsan.tugasptcms.utils.PrefsRating
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel by viewModels<DetailMovieViewModel>()
    private val viewModelPersonalList by viewModels<WatchlistViewModel>()
    private lateinit var bottomSheetAddRating: BottomSheetDialog
    private lateinit var bottomSheetAddToPersonalList: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbarDetailMovie.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            toolbarDetailMovie.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.nav_home -> onBackPressedDispatcher.onBackPressed()
                }
                true
            }
        }

        val extras = intent?.extras
        if (extras != null){
            val movieId = extras.getString(MOVIE_ID)
            if (movieId != null){
                showDetailMovies(movieId)
                showTrailers(movieId)
            }
        }
    }

    private fun showTrailers(movieId: String) {
        viewModel.getTrailers(movieId).observe(this){
            if (it!!.isNotEmpty()){
                val adapterTrailers = AdapterTrailers()
                adapterTrailers.submitList(it)
                binding.apply {
                    rvTrailers.adapter = adapterTrailers
                    rvTrailers.layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
                    tvTrailers.visibility = View.VISIBLE
                    rvTrailers.visibility = View.VISIBLE
                }
            }else{
                binding.tvTrailers.visibility = View.GONE
                binding.rvTrailers.visibility = View.GONE
            }
        }
    }

    private fun showDetailMovies(movieId: String) {
        viewModel.detailMovie(movieId).observe(this){ detail->
            if (detail != null){
                binding.apply {
                    tvOriginalTitle.text = detail.originalTitle
                    tvOriginalTitle.isSelected = true

                    tvTitle.text = detail.title
                    tvReleasedate.text = DateFormat.formatDate(detail.releaseDate, "dd MMMM yyyy")

                    Glide.with(this@DetailMovieActivity)
                        .load(URL_BACKDROP_PATH + detail.backdropPath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgBackdropPath)

                    Glide.with(this@DetailMovieActivity)
                        .load(URL_POSTER + detail.posterPath)
                        .transform(RoundedCorners(10))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgPosterPath)

                    if (detail.tagline.isEmpty()){
                        tvTagline.text = "No tagline for this movie"
                    }else{
                        tvTagline.text = detail.tagline
                    }
                    tvOverview.text = detail.overview
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        tvOverview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                    }

                    val prefsRating = PrefsRating(this@DetailMovieActivity).getRating(detail.id)
                    binding.btnRating.text = prefsRating.toString()

                    binding.btnRating.setOnClickListener {
                        showBottomSheetRatingMovie(detail.id)
                    }
                    binding.btnAddTo.setOnClickListener {
                        showBottomSheetAddTo(detail.id, detail.title)
                    }

                    binding.toolbarDetailMovie.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.nav_home_page -> {
                                if (detail.homepage.isNotEmpty()){
                                    Intent(Intent.ACTION_VIEW).also { intent ->
                                        intent.data = Uri.parse(detail.homepage)
                                        startActivity(intent)
                                    }
                                }else{
                                    Snackbar.make(binding.root, "No homepage for this movie", Snackbar.LENGTH_SHORT).show()
                                }
                            }

                            R.id.nav_share -> Intent(Intent.ACTION_SEND).also { share->
                                share.type = "text/plain"
                                share.putExtra(Intent.EXTRA_TEXT, "$SHARE$movieId")
                                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(Intent.createChooser(share, "Share with..."))
                            }
                        }
                        true
                    }
                }
            }
        }
    }

    private fun showBottomSheetRatingMovie(movieId: String) {
        val bottomSheetAddRatingBinding = BottomSheetAddRatingBinding.inflate(layoutInflater)
        bottomSheetAddRating = BottomSheetDialog(this)
        bottomSheetAddRating.setContentView(bottomSheetAddRatingBinding.root)
        bottomSheetAddRating.show()
        bottomSheetAddRatingBinding.toolbarRating.setNavigationOnClickListener {
            bottomSheetAddRating.dismiss()
        }

        val rating = PrefsRating(this).getRating(movieId)

        if (rating != null) {

            bottomSheetAddRatingBinding.ratingMovie.rating = rating.toFloat()

            bottomSheetAddRatingBinding.btnSubmitRating.setOnClickListener {
                val isLogin = PrefAuth(this).isLogin()
                val sessionId = PrefAuth(this).getSessionId()
                val value = bottomSheetAddRatingBinding.ratingMovie.rating.toDouble()
                val data = RatingRequest(value)
                if (isLogin == true){
                    if (sessionId != null){
                        viewModel.addRating(movieId, sessionId, data).observe(this){
                            if (it != null)
                                binding.btnRating.text = data.value.toString()
                            Snackbar.make(binding.root, it?.statusMessage + data.value, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                bottomSheetAddRating.dismiss()
            }

            bottomSheetAddRatingBinding.btnDeleteRating.setOnClickListener {
                val prefsRating = PrefsRating(this)
                val sessionId = PrefAuth(this).getSessionId()
                if (sessionId != null){
                    viewModel.deleteRating(movieId, sessionId).observe(this){
                        Snackbar.make(binding.root, it?.statusMessage.toString(), Snackbar.LENGTH_SHORT).show()
                        binding.btnRating.text = 0.0f.toString()
                        prefsRating.removeData(movieId)
                    }
                }
                bottomSheetAddRating.dismiss()
            }
        }
    }

    private fun showBottomSheetAddTo(movieId: String, title: String) {
        val bottomSheetAddToPersonalListBinding = BottomSheetAddToPersonalListBinding.inflate(layoutInflater)
        bottomSheetAddToPersonalList = BottomSheetDialog(this)
        bottomSheetAddToPersonalList.setContentView(bottomSheetAddToPersonalListBinding.root)
        bottomSheetAddToPersonalList.show()

        bottomSheetAddToPersonalListBinding.toolbarPersonalList.setNavigationOnClickListener {
            bottomSheetAddToPersonalList.dismiss()
        }

        val sessionId = PrefAuth(this).getSessionId()
        val accountId = PrefAuth(this).getSessionId()
        val adapterAddToPersonalList = AdapterAddToPersonalList()

        if (sessionId != null && accountId != null){
            viewModelPersonalList.getMyPersonalList(accountId, sessionId).observe(this){
                adapterAddToPersonalList.submitList(it)
                bottomSheetAddToPersonalListBinding.apply {
                    rvPersonalList.adapter = adapterAddToPersonalList
                    rvPersonalList.layoutManager = LinearLayoutManager(this@DetailMovieActivity)
                }
            }

            val mediaId = MovieId(movieId)
            adapterAddToPersonalList.addTo(object : AdapterAddToPersonalList.AddTo{
                override fun addToPersonalList(id: String, name: String) {
                    viewModel.addMovieToPersonalList(id, sessionId, mediaId).observe(this@DetailMovieActivity){
                        Snackbar.make(binding.root, "Successfully added $title to $name", Snackbar.LENGTH_SHORT).show()
                    }
                    bottomSheetAddToPersonalList.dismiss()
                }
            })
        }
    }

    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }
}