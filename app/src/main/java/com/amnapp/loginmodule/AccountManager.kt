package com.amnapp.loginmodule

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amnapp.loginmodule.activities.SignInActivity
import com.amnapp.loginmodule.activity.LoginActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.nio.charset.Charset
import java.security.MessageDigest

class AccountManager {
    var db = Firebase.firestore

    fun signInWithoutInvite(// 그룹개설자 가입과정
        context: Context,
        id: String,
        pw: String,
        groupCode: String,
        userName: String,
        userHeight: Int,
        userWeight: Int,

        userAge: Int? = null,
        militaryId: Int? = null,
        userBloodType: String? = null,

        goalOfWeight: Int? = null,
        goalOfTotalRank: String? = null,
        goalOfLegTuckRank: String? = null,
        goalOfShuttleRunRank: String? = null,
        goalOfFieldTrainingRank: String? = null
    ){
        val confirmCode: String? = hash(id+pw+groupCode) ?: null.also{
            Log.d("AccountManager", "본인확인코드 생성오류")
        }
        val ud = UserData.getInstance()
        var isValid: Boolean = true
        ud.id = id
        ud.pw = pw
        ud.userName = userName
        ud.userHeight = userHeight
        ud.userWeight = userWeight
        ud.confirmCode = confirmCode
        ud.isAdmin = true

        ud.userAge = userAge
        ud.militaryId = militaryId
        ud.userBloodType = userBloodType
        ud.goalOfWeight = goalOfWeight
        ud.goalOfTotalRank = goalOfTotalRank
        ud.goalOfLegTuckRank = goalOfLegTuckRank
        ud.goalOfShuttleRunRank = goalOfShuttleRunRank
        ud.goalOfFieldTrainingRank = goalOfFieldTrainingRank

        Log.d(TAG, "asd")
        hash(id+pw+userName)?.let {
            db.collection("users").document(it)
                .set(ud)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Toast.makeText(context, "가입성공", Toast.LENGTH_SHORT).show()
                    val activity = context as SignInActivity
                    activity.finish()
                }
                .addOnFailureListener {e ->
                    Log.w(TAG, "Error writing document", e)
                    Toast.makeText(context, "가입실패", Toast.LENGTH_SHORT).show()
                    val activity = context as SignInActivity
                    activity.finish()
                }
        }
    }

    fun login(context: Context, id: String, pw: String, groupCode: String){
        val activity: LoginActivity = context as LoginActivity
        db.collection("users").whereEqualTo("id",id)
            .get().addOnSuccessListener {
                if(it.isEmpty){
                    activity.showDialogMessage("로그인 실패", "존재하지 않는 아이디 입니다")
                }
                else{
                    val document = it.documents[0]
                    val ud= document.toObject<UserData>()
                    val confirmCode = hash(id+pw+groupCode)
                    if (ud != null) {
                        if(ud.confirmCode==confirmCode){
                            UserData.ud = ud // 서버에서 얻은 객체로 대체
                            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                            activity.binding.loginLl.visibility = View.GONE
                            activity.binding.signInCv.visibility = View.GONE
                            activity.binding.loginBoxCv.visibility = View.GONE
                            activity.binding.logoutLl.visibility = View.VISIBLE
                            activity.binding.issueCv.visibility = View.VISIBLE
                            activity.binding.loginoutCv.setCardBackgroundColor(Color.RED)
                        }
                        else{
                            activity.showDialogMessage("로그인 실패", "비밀번호 또는 그룹코드가 다릅니다")
                        }

                    }
                }
                activity.binding.loginLl.isClickable = true //연타방지 해제
            }
    }

//    fun issueInviteCode(context: Context, inviteCode: String){
//
//    }

    fun hash(text: String): String? {
        val sha = SHA256()
        return sha.encrypt(text)
    }

    fun checkNetworkState(context: Context): Boolean {//인터넷 상태를 확인하는 함수
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    companion object{
        var groupCode: String? = null //트리순회에 필요하므로 로그인 시 정적으로 입력할 것
        val TAG = "AccountManager"
    }
}

class SHA256(){
    fun encrypt(text: String): String? {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(text.toByteArray(Charset.defaultCharset()))

        return bytesToHex(md.digest())
    }

    private fun bytesToHex(bytes: ByteArray): String? {
        val builder = StringBuilder()
        for (b in bytes) {
            builder.append(String.format("%02x", b))
        }
        return builder.toString()
    }
}