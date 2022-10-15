package biz.ohrae.challenge_screen.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

open class BaseActivity: AppCompatActivity() {
    protected lateinit var prefs: SharedPreference

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
}