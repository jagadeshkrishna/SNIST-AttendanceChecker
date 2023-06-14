package com.example.scrapeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scrapeme.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            if (binding.username.text!!.length == 10 && binding.password.text!!.length == 10) {
                if (binding.username.text!!.isEmpty()) {
                    binding.username.setError("Use your Roll.No")
                } else if (binding.password.text!!.isEmpty()) {
                    binding.password.setError("Need Password")
                } else if (binding.username.text!!.isEmpty() && binding.password.text!!.isEmpty()) {
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else if (binding.username.text!!.isNotEmpty() && binding.password.text!!.isNotEmpty() && binding.username.text.toString() == binding.password.text.toString()) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("user", binding.username.text.toString())
                    intent.putExtra("password", binding.password.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Incorrect Details", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Incorrect Details", Toast.LENGTH_LONG).show()
            }

        }
        binding.logo.setOnClickListener {
            Toast.makeText(this,"Chaduvukondi First UUU....",Toast.LENGTH_LONG).show()
        }
    }


}