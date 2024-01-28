package com.nurikhsan.tugasptcms.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nurikhsan.tugasptcms.data.repositories.Repositories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repositories: Repositories): ViewModel() {

    val discover = repositories.getDiscoverMovies().asLiveData()

    fun searchMovie(query: String) = repositories.searchMovie(query).asLiveData()

}