package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        emailChkBtn.setOnClickListener {
//            입력한 이메일이 이미 회원으로 있는지 확인 => 서버에 요청

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext,"EMAIL", inputEmail , object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                }

            })

        }

    }

    override fun setValues() {

    }

}
