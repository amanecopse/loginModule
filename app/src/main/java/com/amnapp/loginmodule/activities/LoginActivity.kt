package com.amnapp.loginmodule.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.amnapp.loginmodule.AccountManager
import com.amnapp.loginmodule.activities.InviteCodeIssueActivity
import com.amnapp.loginmodule.activities.SignInActivity
import com.amnapp.loginmodule.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        //초기상태 로그인, 회원가입
        binding.loginLl.visibility = View.VISIBLE
        binding.signInCv.visibility = View.VISIBLE
        binding.logoutLl.visibility = View.GONE
        binding.issueCv.visibility = View.GONE
        binding.loginoutCv.setCardBackgroundColor(Color.WHITE)


        binding.loginLl.setOnClickListener{
            val am = AccountManager()
            am.login(this, binding.idEt.text.toString(), binding.pwEt.text.toString(), binding.groupCodeEt.text.toString())
            binding.loginLl.isClickable = false //연타 방지
        }
        binding.logoutLl.setOnClickListener {
            binding.loginLl.visibility = View.VISIBLE
            binding.signInCv.visibility = View.VISIBLE
            binding.loginBoxCv.visibility = View.VISIBLE
            binding.logoutLl.visibility = View.GONE
            binding.issueCv.visibility = View.GONE
            binding.loginoutCv.setCardBackgroundColor(Color.WHITE)
        }
        binding.issueLl.setOnClickListener {
            var intent = Intent(this, InviteCodeIssueActivity::class.java)
            startActivity(intent)
        }
        binding.signInLl.setOnClickListener {
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "%%%%%LoginActivityLog%%%%%"
    }

    fun showDialogMessage(title:String, body:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }
}