package com.example.android.architecture

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.architecture.databinding.ActivityMainBinding
import com.example.android.architecture.viewModel.DiceViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: DiceViewModel
    private val imageViews by lazy {
        arrayOf<ImageView>(die1, die2, die3, die4, die5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        initViewModel()
        binding.viewmodel = viewmodel

    }

    /**
     * Initialize the viewmodel
     */
    private fun initViewModel() {
        viewmodel = ViewModelProviders.of(this)
            .get(DiceViewModel::class.java)
//        viewmodel.headline.observe(this, Observer {
//            headline.text = it
//        })
        viewmodel.dice.observe(this, Observer {
            updateDisplay(it)
        })
    }

    /**
     * Update the display of dice
     */
    private fun updateDisplay(dice: IntArray?) {
        for (i in 0 until imageViews.size) {
            val drawableId = when (dice?.get(i)) {
                1 -> R.drawable.die_1
                2 -> R.drawable.die_2
                3 -> R.drawable.die_3
                4 -> R.drawable.die_4
                5 -> R.drawable.die_5
                6 -> R.drawable.die_6
                else -> R.drawable.die_6
            }
            imageViews[i].setImageResource(drawableId)
        }
    }

}
