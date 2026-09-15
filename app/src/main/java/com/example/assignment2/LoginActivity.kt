package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.assignment2.ui.login.LoginState
import com.example.assignment2.ui.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername =
            findViewById<TextInputEditText>(R.id.etUsername)

        val etPassword =
            findViewById<TextInputEditText>(R.id.etPassword)

        val btnLogin =
            findViewById<View>(R.id.btnLogin)

        val progressBar =
            findViewById<View>(R.id.progressBar)

        val tvError =
            findViewById<TextView>(R.id.tvError)

        btnLogin.setOnClickListener {

            val username =
                etUsername.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            tvError.visibility = View.GONE

            viewModel.login(
                username = username,
                password = password
            )
        }

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.loginState.collect { state ->

                    when (state) {

                        is LoginState.Idle -> {

                            progressBar.visibility = View.GONE
                            btnLogin.isEnabled = true
                        }

                        is LoginState.Loading -> {

                            progressBar.visibility = View.VISIBLE
                            btnLogin.isEnabled = false
                            tvError.visibility = View.GONE
                        }

                        is LoginState.Success -> {

                            progressBar.visibility = View.GONE
                            btnLogin.isEnabled = true
                            tvError.visibility = View.GONE

                            Toast.makeText(
                                this@LoginActivity,
                                "Login successful!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Pass the keypass internally to Dashboard
                            val intent = Intent(
                                this@LoginActivity,
                                DashboardActivity::class.java
                            )

                            intent.putExtra(
                                DashboardActivity.KEYPASS_EXTRA,
                                state.keypass
                            )

                            startActivity(intent)

                            // Prevent returning to Login with Back button
                            finish()
                        }

                        is LoginState.Error -> {

                            progressBar.visibility = View.GONE
                            btnLogin.isEnabled = true

                            tvError.text = state.message
                            tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}