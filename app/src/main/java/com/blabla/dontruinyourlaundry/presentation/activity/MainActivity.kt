package com.blabla.dontruinyourlaundry.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blabla.dontruinyourlaundry.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var layout: View
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        layout = binding.mainLayout
        setContentView(view)



    }
}


