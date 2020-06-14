package kr.tjoeun.apipractice_20200613

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        //        3초 뒤에 다음화면으로 이동
        val myHandler =  Handler()
        myHandler.postDelayed({
//            시간이 지난뒤 실행할 내용
            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)

            finish()
        }, 3000)

    }
}
