package com.example.myapplication.covid19.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.covid19.R
import com.example.myapplication.covid19.databinding.ActivityNoCountryBinding


class NoCountryActivity : AppCompatActivity() {
    private lateinit var binding1: ActivityNoCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding1= ActivityNoCountryBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        binding1.btn1.setOnClickListener {
            Intent(this,MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}