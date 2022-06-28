package biz.ohrae.challenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import biz.ohrae.challenge.databinding.ActivityMainBinding
import biz.ohrae.challenge.ui.fragments.ChallengeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newFragment = ChallengeFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentView.id, newFragment, "ChallengeFragment")
        transaction.addToBackStack(null)
        transaction.commit()
    }
}