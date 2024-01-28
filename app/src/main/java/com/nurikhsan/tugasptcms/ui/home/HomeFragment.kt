package com.nurikhsan.tugasptcms.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurikhsan.tugasptcms.R
import com.nurikhsan.tugasptcms.adapter.AdapterMovies
import com.nurikhsan.tugasptcms.databinding.FragmentHomeBinding
import com.nurikhsan.tugasptcms.ui.activity.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.discover.observe(viewLifecycleOwner){
            val adapterMovies = AdapterMovies()
            if (it != null){
                adapterMovies.submitList(it)
                binding.rvMovie.adapter = adapterMovies
                binding.rvMovie.layoutManager = LinearLayoutManager(context)
            }
        }

        binding.toolbarHome.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search_movie -> startActivity(Intent(requireContext(), SearchActivity::class.java))
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}