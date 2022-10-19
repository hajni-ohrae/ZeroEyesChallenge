package biz.ohrae.challenge_screen.ui

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

open class BaseActivity: AppCompatActivity() {
    protected lateinit var prefs: SharedPreference
    protected var lastClickedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = SharedPreference(applicationContext, Gson())
    }

    open fun initClickListeners() { }
    open fun observeViewModels() { }
    open fun onBack() {}

    fun showSnackBar(code: String?, message: String?) {
        var result = ""
        if (!code.isNullOrEmpty()) {
            result += code
        }
        if (!message.isNullOrEmpty()) {
            result += ": $message"
        }

        if (result.isNotEmpty()) {
            showSnackBar(result)
        }
    }

    fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    fun isClickable(): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickedTime < 500) {
            return false
        }
        lastClickedTime = SystemClock.elapsedRealtime()
        return true
    }
}