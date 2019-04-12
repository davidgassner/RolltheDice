package com.example.android.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.architecture.databinding.ActivityMainBinding
import com.example.android.architecture.viewModel.DiceViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        binding.lifecycleOwner = this

        val viewmodel = ViewModelProviders.of(this)
            .get(DiceViewModel::class.java)
        binding.viewmodel = viewmodel

    }

}
