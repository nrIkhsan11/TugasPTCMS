package com.nurikhsan.tugasptcms.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nurikhsan.tugasptcms.data.response.LoginRequest
import com.nurikhsan.tugasptcms.databinding.ActivityAuthBinding
import com.nurikhsan.tugasptcms.utils.PrefAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModelAuth by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            requestToken()
        }

        binding.tvJointTmdb.setOnClickListener {
            Intent(Intent.ACTION_VIEW).also { intent ->
                intent.data = Uri.parse("https://www.themoviedb.org/signup")
                startActivity(intent)
            }
        }
    }

    private fun requestToken() {
        val username = binding.edtUsername.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (username.isEmpty() && password.isEmpty()) {
            Snackbar.make(binding.root, "There cannot be any blanks in the input form", Snackbar.LENGTH_SHORT
            ).show()
        } else if (username.isEmpty()) {
            Snackbar.make(binding.root, "Username cannot be empty", Snackbar.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Snackbar.make(binding.root, "Password cannot be empty", Snackbar.LENGTH_SHORT).show()
        } else {
            getToken(username, password)
        }
    }

    private fun getToken(username: String, password: String) {
        viewModelAuth.requestToken.observe(this) {
            val token = it
            val loginRequest = LoginRequest(username, password)
            if (token != null) {
                validateLogin(token, loginRequest)
            }
        }
    }

    private fun validateLogin(token: String, loginRequest: LoginRequest) {
        viewModelAuth.validateLogin(token, loginRequest).observe(this) {

            val username = loginRequest.username
            val password = loginRequest.password

            binding.edtUsername.setText(username)
            binding.edtPassword.setText(password)
            Toast.makeText(this, "Hello, $username", Toast.LENGTH_SHORT).show()

            val token = it
            if (token != null){
                createSession(token)
            }
        }
    }

    private fun createSession(token: String) {
        viewModelAuth.sessionId(token).observe(this){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val prefAuth = PrefAuth(this)
        if (prefAuth.isLogin() == true){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}