package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
//        서버에서 내 정보를 받아와서 화면에 출력
    ServerUtil.getRequestMyInfo(mContext, object: ServerUtil.JsonResponseHandler {
        override fun onResponse(json: JSONObject) {

        }

    })

    }
}
