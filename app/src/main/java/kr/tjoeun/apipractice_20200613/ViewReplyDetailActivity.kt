package kr.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import kr.tjoeun.apipractice_20200613.adapters.ReReplyAdapter
import kr.tjoeun.apipractice_20200613.datas.TopicReply
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    var mReplyId = -1
    lateinit var mReply : TopicReply

//    답글 목록을 담고있게 될 배열
    val reReplyList = ArrayList<TopicReply>()
    lateinit var mReReplyAdapter : ReReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mReplyId = intent.getIntExtra("replyId", -1)

//        어댑터를 먼저 생성 => 답글 목록을 뿌려준다고 명시
        mReReplyAdapter = ReReplyAdapter(mContext, R.layout.topic_re_reply_list_item, reReplyList)
//      어댑터와 리스트뷰 연결
        reReplyListView.adapter = mReReplyAdapter

        if (mReplyId == -1) {
//            주제 id가 -1로 남아있다 : topic_id 첨부가 제대로 되지 않었다.
            Toast.makeText(mContext, "잘못된 접근입니다." , Toast.LENGTH_SHORT).show()
//            코드 추가 진행을 막자. (강제종료)
            return
        }

        Log.d("넘겨받은 주제 id" ,mReplyId.toString())

//        서버에서 의견상세 가져오기
        getReplyDetailFromServer()

    }

    fun getReplyDetailFromServer() {
        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object:ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                var data =json.getJSONObject("data")
                var reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

//                화면서 뿌려질 댓글 목록 담어주자.
                var reReplies = reply.getJSONArray("replies")

                for (i in 0..reReplies.length()-1) {
//                    JSONArray 내부의 객체를  => TopicReply 로 변환 => reReplyList에 추가
                    reReplyList.add(TopicReply.getTopicReplyFromJson(reReplies.getJSONObject(i)))
                }

                runOnUiThread {
                    sideTitleTxt.text = mReply.selectedSide.title
                    writerNickNameTxt.text = mReply.user.nickName
                    contextTxt.text = mReply.content

//                    서버에서 받어온 대댓글을 리스트뷰에 반영 => 리스트뷰의 내용 변경 감지 새로고침
                    mReReplyAdapter.notifyDataSetChanged()

                }


            }

        })

    }
}
