package com.demo.quizzed.activities.util

import com.demo.quizzed.R

object IconPicker {
    private val icons = arrayOf(
        R.drawable.icon,
        R.drawable.icon_1,
        R.drawable.icon_2,
        R.drawable.icon_3,
        R.drawable.icon_4,
        R.drawable.icon_5,
        R.drawable.icon_6,
        R.drawable.icon_7,
        R.drawable.icon_8,
        R.drawable.icon_9,
        R.drawable.icon_10
    )
    var currentIconIndex = 0

    fun getIcon(): Int {
        currentIconIndex = (currentIconIndex + 1) % icons.size
        return icons[currentIconIndex]
    }
}