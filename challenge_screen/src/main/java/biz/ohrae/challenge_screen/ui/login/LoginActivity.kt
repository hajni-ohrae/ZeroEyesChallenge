package biz.ohrae.challenge_screen.ui.login

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.databinding.ActivityLoginBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    companion object {
        const val TOTAL_TIMER = 180 * 1000 // 총 시간 3분
        const val COUNT_DOWN_INTERVAL = 1000 // onTick 메소드 호출 간격 1초
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private var count = 180
    private var lastCheckCount = 180
    var uidCheck = false

//    protected var auth: Auth? = null
    var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        init(binding.textPhoneNumber)

        initClickListeners()
        observeViewModels()
    }

    private fun initClickListeners() {
        binding.btnBottom.setOnClickListener {
            if (binding.layoutAuthNumber.visibility == View.GONE) {
                if (isCellPhone(binding.textPhoneNumber.text.toString())) {
                    val uid = getDeviceUid().toString()
                    val phoneNumber = binding.textPhoneNumber.text.toString()
                    viewModel.requestAuth(phoneNumber, uid)
                    binding.btnBottom.isEnabled = false
                    binding.btnBottom.text = "다음"
                } else {
//                    binding.layoutPhoneNumber.setErrorTextAppearance(R.style.InputError)
                    binding.layoutPhoneNumber.error = "휴대폰 번호가 유효하지 않습니다."
                }
            } else {
                if (count > 1) {
                    submitAuth()
                } else {
//                    binding.layoutAuthNumberCheck.setErrorTextAppearance(R.style.InputError)
                    binding.layoutAuthNumberCheck.error = "인증번호 유효기간이 만료되었습니다."
                }
            }
        }

        binding.btnClearPhoneNumber.setOnClickListener {
            binding.textPhoneNumber.text = null
            binding.textPhoneNumber.requestFocus()
            binding.btnClearPhoneNumber.visibility = View.GONE

            binding.btnAuthCheckRequest.visibility = View.VISIBLE
            binding.tvAuthTimer.visibility = View.VISIBLE
            binding.textAuthNumber.isEnabled = true
            binding.textPhoneNumber.isEnabled = true
            binding.textPhoneNumber.requestFocus()
            Handler().postDelayed({ showKeyboard(binding.textPhoneNumber) }, 750)
            binding.btnBottom.isEnabled = false
            binding.btnBottom.text = "인증번호 요청"
        }

        binding.btnAuthCheckRequest.setOnClickListener {
            val deviceUid = getDeviceUid().toString()
            viewModel.requestAuth(binding.textPhoneNumber.text.toString(), deviceUid)
        }

        val watcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (binding.layoutAuthNumber.visibility == View.GONE) {
                    if (s.isEmpty()) {
                        binding.btnBottom.isEnabled = false
                        binding.btnBottom.text = "다음"
                        binding.btnClearPhoneNumber.visibility = View.GONE
                    } else {
                        binding.btnBottom.isEnabled = true
                        binding.btnBottom.text = "다음"
                        binding.btnBottom.text = "인증번호 요청"
                        binding.btnClearPhoneNumber.visibility = View.VISIBLE
                    }
                } else {
                    binding.layoutAuthNumber.visibility = View.GONE
                    binding.layoutAuthNumberCheck.error = null
                    stopTimer()
                    binding.textAuthNumber.text = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.textPhoneNumber.addTextChangedListener(watcher)

        val watcher2 = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                binding.btnBottom.isEnabled = s.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.textAuthNumber.addTextChangedListener(watcher2)
    }

    private fun observeViewModels() {
        viewModel.receivedAuth.observe(this) {
            if (it) {
                showAuthTextView(true)
            }
        }

        viewModel.checkedAuth.observe(this) {
            if (it) {
                Timber.e("login!!")
            }
        }

        viewModel.relatedServiceIds.observe(this) {
            if (it.isNotEmpty()) {
                Timber.e("get service Ids!!")
            }
        }
    }

    private fun showAuthTextView(init: Boolean) {
        if (init) {
            binding.layoutAuthNumber.visibility = View.VISIBLE
            binding.layoutPhoneNumber.error = null
        } else {
            binding.layoutAuthNumber.visibility = View.VISIBLE
            binding.textAuthNumber.text = null
            binding.layoutAuthNumberCheck.error = null
        }
        startTimer()
    }

    private fun submitAuth() {
        binding.btnBottom.isEnabled = false

        val phoneNumber = binding.textPhoneNumber.text.toString()
        val authNumber = binding.textAuthNumber.text.toString()

        viewModel.authCheck(phoneNumber, authNumber)
    }

    private fun init(firstFocusView: View?) {
        if (firstFocusView != null) {
            firstFocusView.requestFocus()
            Handler().postDelayed({ showKeyboard(firstFocusView) }, 750)
        }
        binding.textPhoneNumber.text = null
        binding.textAuthNumber.text = null
    }

    private fun showKeyboard(view: View?) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun isCellPhone(string: String?): Boolean {
        return Pattern.matches("^010\\d{4}\\d{4}$|^01(?:1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", string)
    }

    fun startTimer() {
        stopTimer()
        count = 180
        lastCheckCount = 180
        countDownTimer = object : CountDownTimer(TOTAL_TIMER.toLong(), COUNT_DOWN_INTERVAL.toLong()) {
            override fun onTick(l: Long) {
                count--
                if (count - count / 60 * 60 >= 10) {
                    binding.tvAuthTimer.text = (count / 60).toString() + " : " + (count - count / 60 * 60)
                } else {
                    binding.tvAuthTimer.text = (count / 60).toString() + " : 0" + (count - count / 60 * 60)
                }
            }

            override fun onFinish() {
                count = -1
                binding.tvAuthTimer.text = "0 : 00"
                binding.textAuthNumber.text = null
//                binding.layoutAuthNumberCheck.setErrorTextAppearance(R.style.InputError)
                binding.layoutAuthNumberCheck.error = "인증번호 유효기간이 만료되었습니다."
                stopTimer()
            }
        }.start()
        binding.textAuthNumber.requestFocus()
        showKeyboard(binding.textAuthNumber)
    }

    fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    private fun getDeviceUid(): String? {
        val prefs = SharedPreference(applicationContext, Gson())
        var deviceUid: String = prefs.getUid()
        if (deviceUid.isEmpty()) {
            deviceUid = UUID.randomUUID().toString()
            prefs.setUid(deviceUid)
        }
        Timber.e( "DeviceUid : $deviceUid")
        return deviceUid
    }
}