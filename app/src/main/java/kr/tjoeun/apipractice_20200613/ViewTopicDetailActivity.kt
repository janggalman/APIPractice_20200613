package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    //화면에서 넘겨준 주제 id값을 저장할 변수
    var mTopicId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mTopicId = intent.getIntExtra("topic_id", -1)

        if (mTopicId == -1) {
//            주제 id가 -1로 남아있다 : topic_id 첨부가 제대로 되지 않었다.
            Toast.makeText(mContext, "잘못된 접근입니다." , Toast.LENGTH_SHORT).show()
//            코드 추가 진행을 막자. (강제종료)
            return
        }

        Log.d("넘겨받은 주제 id" ,mTopicId.toString())

//        넘겨 받은 id값으로 서버에서 주제의 상세 진행 상황 받아오기
        ServerUtil.getRequestTopicDetail(mContext,mTopicId, object:ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {
                val code = json.getInt("code")

                if (code == 200){


                } else {


                }
            }

        })

    }

}
