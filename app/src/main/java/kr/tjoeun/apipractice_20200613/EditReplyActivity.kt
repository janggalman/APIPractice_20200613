package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_reply.*

class EditReplyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        topicTitleTxt.text = intent.getStringExtra("topicTitle")
        selectedSideTitleTxt.text = intent.getStringExtra("selectedSideTitle")

    }

}
