package com.nurikhsan.tugasptcms.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nurikhsan.tugasptcms.adapter.AdapterPersonalList
import com.nurikhsan.tugasptcms.databinding.FragmentWatchlistBinding
import com.nurikhsan.tugasptcms.utils.PrefAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountId = PrefAuth(requireContext()).getSessionId()
        val sessionId = PrefAuth(requireContext()).getSessionId()
        val adapterPersonalList = AdapterPersonalList()
        if (accountId != null && sessionId != null){
            viewModel.getMyPersonalList(accountId, sessionId).observe(viewLifecycleOwner) {
                if (it != null) {
                    adapterPersonalList.submitList(it)
                    binding.apply {
                        rvPersonalList.adapter = adapterPersonalList
                        rvPersonalList.layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            adapterPersonalList.deleteList(object : AdapterPersonalList.DeleteList{
                override fun delete(listId: String, name: String) {
                    viewModel.deletePersonalList(listId, sessionId).observe(viewLifecycleOwner){
                        Snackbar.make(binding.root, "Managed to delete $name", Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}