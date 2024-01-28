package com.nurikhsan.tugasptcms.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nurikhsan.tugasptcms.data.response.RequestCreateList
import com.nurikhsan.tugasptcms.databinding.BottomSheetPersonalListBinding
import com.nurikhsan.tugasptcms.databinding.FragmentProfileBinding
import com.nurikhsan.tugasptcms.ui.activity.AuthActivity
import com.nurikhsan.tugasptcms.ui.activity.AuthViewModel
import com.nurikhsan.tugasptcms.ui.activity.RatingMovieActivity
import com.nurikhsan.tugasptcms.utils.Constant.URL_AVATAR
import com.nurikhsan.tugasptcms.utils.PrefAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRating.setOnClickListener {
            startActivity(Intent(context, RatingMovieActivity::class.java))
        }

        val prefAuth = PrefAuth(requireContext())
        binding.btnLogout.setOnClickListener {
            prefAuth.logoOut()
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val accountId = prefAuth.getSessionId()
        val sessionId = prefAuth.getSessionId()
        if (accountId != null && sessionId != null){
            viewModel.getDetailAccount(accountId, sessionId).observe(viewLifecycleOwner){
                if (it != null){
                    binding.tvUsername.text = it.username
                    binding.tvName.text = it.name

                    Glide.with(this)
                        .load(URL_AVATAR + it.avatar.tmdb.avatarPath)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgProfile)
                }
            }
        }

        binding.btnCreateList.setOnClickListener {
            showBottomSheerPersonalList()
        }
    }

    private fun showBottomSheerPersonalList() {
        val bottomSheetPersonalListBinding = BottomSheetPersonalListBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetPersonalListBinding.root)
        bottomSheetDialog.show()

        bottomSheetPersonalListBinding.toolbarPersonalList.setNavigationOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetPersonalListBinding.btnPersonalList.setOnClickListener {
            val listName = bottomSheetPersonalListBinding.edtListName.text.toString().trim()
            val listDesc = bottomSheetPersonalListBinding.edtListDesc.text.toString().trim()

            if (listName.isEmpty() && listDesc.isEmpty()){
                Toast.makeText(context, "There cannot be any blanks in the input form", Toast.LENGTH_SHORT).show()
            }else if (listName.isEmpty()){
                Toast.makeText(context, "List name cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                val requestCreateList = RequestCreateList(listName, listDesc)
                val sessionId = PrefAuth(requireContext()).getSessionId()
                bottomSheetDialog.dismiss()

                if (sessionId != null){

                    viewModel.createPersonalList(sessionId, requestCreateList).observe(viewLifecycleOwner){
                        if (it != null){
                            bottomSheetPersonalListBinding.apply {
                                edtListName.setText(requestCreateList.listName)
                                edtListDesc.setText(requestCreateList.listDescription)
                                Toast.makeText(requireContext(), "Success create personal list", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}