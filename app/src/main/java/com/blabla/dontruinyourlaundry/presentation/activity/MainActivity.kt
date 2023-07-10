package com.blabla.dontruinyourlaundry.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blabla.dontruinyourlaundry.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}


