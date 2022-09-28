package biz.ohrae.challenge_screen.ui.dialog

import android.graphics.Bitmap


interface CustomDialogListener {
    fun clickPositive()
    fun clickNegative()
}

interface FilterDialogListener {
    fun clickPositive()
    fun clickNegative()
    fun clickInitialization()
}
