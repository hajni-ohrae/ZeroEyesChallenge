package biz.ohrae.challenge_screen.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailViewModel
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity() {
    protected lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
    }

    open fun initClickListeners() { }
    open fun observeViewModels() { }
    open fun onBack() {}

    fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}