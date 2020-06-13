package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

//    이메일 / 닉네임 중복검사 결과 저장 변수
    var isEmailDuplOk = false
    var isNickNAmeDuolOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

//            이메일 중복검사 통과? + 닉네임 중복검사 통과?
            if (!isEmailDuplOk) {
//                이메일 중복검사 통과 X?

                Toast.makeText(mContext, "이메일 중복 검사를 통과해야 합니다." , Toast.LENGTH_SHORT).show()

//                뒤의 로직 실행하지 않고 이 함수를 강제 종료.
                return@setOnClickListener

            }

            if (!isNickNAmeDuolOk) {
//                닉네임 중복검사 통과 X?

                Toast.makeText(mContext, "닉네임 중복 검사를 통과해야 합니다." , Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

//            여기가 실행이 된다? => 강제종료 두번을 모두 피했다.
//            이메일도 / 닉네임도 모두 통과한 상태다.

//            입력한 이메일 / 비번 / 닉네임을 들고 서버에 가입 신청.
            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()
            val nickName = nickEdt.text.toString()

//            서버에 /user => PUT으로 요청. => ServerUtil 통해 요청
            ServerUtil.putRequestSignUp(mContext, email , nickName, pw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if ( code == 200) {

                        runOnUiThread {
                            Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    } else {

                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            })

        }

        nickEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                nickChkResultTxt.text = "중복확인을 해주세요."
                isNickNAmeDuolOk = false
            }

        })


//        이메일 입력 에디트 onchange 이벤트 처리법
        emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                글자가 변경된 시점에 실행되는 함수
//                Log.d("변경된 내용", s.toString())

//                이메일 중복검사를 하라고 안내
                emailChkResultTxt.text = "중복확인을 해주세요."
                isEmailDuplOk = false

            }

        })

        emailChkBtn.setOnClickListener {
//            입력한 이메일이 이미 회원으로 있는지 확인 => 서버에 요청

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext,"EMAIL", inputEmail , object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            emailChkResultTxt.text = "사용해도 좋습니다."
//                            중복검사 결과를 true
                            isEmailDuplOk = true
                        }

                    } else {
                        runOnUiThread {
                            emailChkResultTxt.text = "이미 사용중입니다. 다른 이메일로 다시 체크해주세요."
                        }
                    }
                }
            })

        }

        nickChkBtn.setOnClickListener {

            val nick = nickEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "NICKNAME", nick, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread{
                            nickChkResultTxt.text = "사용해도 좋습니다."
                            isNickNAmeDuolOk = true
                        }
                    } else {
                        runOnUiThread {
                            nickChkResultTxt.text = "중복된 닉네임입니다. 다른 닉네임으로 다시 체크해주세요."
                        }
                    }
                }

            })
        }

    }

    override fun setValues() {

    }

}
