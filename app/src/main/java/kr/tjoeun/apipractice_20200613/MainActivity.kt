package kr.tjoeun.apipractice_20200613

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kr.tjoeun.apipractice_20200613.adapters.TopicAdapter
import kr.tjoeun.apipractice_20200613.datas.Topic
import kr.tjoeun.apipractice_20200613.utils.ContextUtil
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val topicList = ArrayList<Topic>()
    // onCreate 실행된 이후에 내용을 채워야함.
//    변수는 멤버변수로 만드어두면 차후에 편리함.
//    만드는건 지금 만들지만, 채우는건 나중에 하겠다 => Lateinit
    lateinit var topicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        logoutBtn.setOnClickListener {
//            정말 로그아웃 할건지? 확인받자.
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//                실제로 로그아웃 하는 방법 => 저장된 토큰을 삭제 (빈칸으로 변경)
                ContextUtil.setUserToken(mContext, "")

//                로그인 화면으로 이동
                val myIntent = Intent(mContext , LoginActivity::class.java)
                startActivity(myIntent)

                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {
//        서버에서 내 정보를 받아와서 화면에 출력
//    ServerUtil.getRequestMyInfo(mContext, object: ServerUtil.JsonResponseHandler {
//        override fun onResponse(json: JSONObject) {
//
//            val data = json.getJSONObject("data")
//            val user = data.getJSONObject("user")
//            val nickName = user.getString("nick_name")
//
//            runOnUiThread {
//                loginUserNickNameTxt.text = "${nickName}님 환영합니다."
//            }
//        }
//
//    })

//         서버에서 토론 주제 목록을 받아와서 리스트뷰의  ArrayList에 채워주기.
        getTopicListFromServer()

        topicAdapter = TopicAdapter(mContext , R.layout.topic_list, topicList)
        topicListView.adapter = topicAdapter
    }

    fun getTopicListFromServer() {
        ServerUtil.getRequestV2MainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val code = json.getInt("code")

                if (code == 200){
                    val data = json.getJSONObject("data")
//                    JSONArray 추출 => [ ] 를 가져와야함으로.
                    val topics = json.getJSONArray("topics")

//                    JSON Object들을 차례대로 추출 반복문
                    for (i in 0..topics.length()-1) {
//                      topics 배열안에서 { } 를 순서대로 {i} JSONObject로 추출
                        val topicJson = topics.getJSONObject(i)

//                        추출한 JsonObject => Topic 객체로 변환 (클래스에 만든 기능 활용)
                        val topic = Topic.getTopicFromJson(topicJson)

                        topicList.add(topic)

                    }

//                    내용물이 추가되었으니 어뎁터에게 새로고침
                    topicAdapter.notifyDataSetChanged()

                }

            }

        })

    }
}
