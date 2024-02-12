package com.example.gardening

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ParallaxTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val width = view.width.toFloat()

        when {
            position < -1 -> view.alpha = 0f
            position <= 1 -> {
                view.alpha = 1 - abs(position)
                view.translationX = -width * position
            }
            else -> view.alpha = 0f
        }
    }
}
