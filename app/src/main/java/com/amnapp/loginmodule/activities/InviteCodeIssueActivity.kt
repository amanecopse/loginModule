package com.amnapp.loginmodule.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import com.amnapp.loginmodule.databinding.ActivityInviteCodeIssueBinding
import kotlinx.android.synthetic.main.activity_invite_code_issue.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class InviteCodeIssueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInviteCodeIssueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInviteCodeIssueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {
        //todo 발급절차가 끝난 상태인지 파악하고 UI를 발급가능 상태로 변화시키는 과정을 구현할 것
        // 만약 아직 발급중이면 발급중인 UI상태로 유지

        binding.cancelLl.setOnClickListener {
            finish()
        }
        binding.issuingLl.setOnClickListener{
            //todo 발급절차를 구현할 것

            binding.issuingTv.text = "발급중"
            binding.issuingCv.setCardBackgroundColor(Color.GRAY)
            binding.inviteCodeEt.inputType = InputType.TYPE_NULL
        }
    }
}