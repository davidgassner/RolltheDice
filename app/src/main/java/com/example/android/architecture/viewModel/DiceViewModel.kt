package com.example.android.architecture.viewModel

import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.R
import com.example.android.architecture.util.DiceHelper

class DiceViewModel(app: Application) : AndroidViewModel(app) {
    private lateinit var soundPool: SoundPool

    val headline = MutableLiveData<String>()
    val drawables = MutableLiveData<MutableMap<Int, Int>>()

    private var soundId = 0
    private var soundLoaded = false
    private var context: Context = app

    init {
//      Initialize properties
        headline.value = app.getString(R.string.roll_em)
        drawables.value = mutableMapOf(
            Pair(1, R.drawable.die_6),
            Pair(2, R.drawable.die_6),
            Pair(3, R.drawable.die_6),
            Pair(4, R.drawable.die_6),
            Pair(5, R.drawable.die_6)
        )

//      Initialize the soundPool object
        initSoundPool()
    }

    /**
     * Called from activity_main.xml with a binding
     */
    fun rollDice() {
        val rolledDice = DiceHelper.rollDice()
        headline.value = DiceHelper.evaluateDice(context, rolledDice)
        updateDrawables(rolledDice)

        if (soundLoaded) {
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        }
    }

    /**
     * Create a new map of die images
     */
    private fun updateDrawables(dice: IntArray) {
        val newDrawables = mutableMapOf<Int, Int>()
        for (i in 0 until dice.size) {
            newDrawables[i + 1] = getDrawable(dice[i])
        }
        drawables.value = newDrawables
    }

    /**
     * Return the die image resource for a number from 1 to 6
     */
    private fun getDrawable(die: Int): Int {
        return when (die) {
            1 -> R.drawable.die_1
            2 -> R.drawable.die_2
            3 -> R.drawable.die_3
            4 -> R.drawable.die_4
            5 -> R.drawable.die_5
            6 -> R.drawable.die_6
            else -> R.drawable.die_6
        }
    }

    /**
     * Intialize the soundpool object
     */
    private fun initSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .build()
        } else {
            @Suppress("DEPRECATION")
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool.setOnLoadCompleteListener { _, _, status ->
            soundLoaded = (status == 0)
        }
        soundId = soundPool.load(context, R.raw.roll_dice, 1)
    }
}