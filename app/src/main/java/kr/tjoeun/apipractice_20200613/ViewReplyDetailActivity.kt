package kr.tjoeun.apipractice_20200613

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

//        답글 삭제 이벤트 (리스트뷰 아이템 롱클릭)

        reReplyListView.setOnItemLongClickListener { parent, view, position, id ->

            val clickedReReply = reReplyList[position]

//            if (clickedReReply.userId == )

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("답글 삭제")
            alert.setMessage("정말 답글을 삭제하시겠습니까? 받은 좋아요 이력이 모두 삭제됩니다.")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

//                서버에 답글 삭제 요청
                ServerUtil.deleteRequestReply(mContext, clickedReReply.id, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {

//                        서버의 메세지를 토스트로 출력
                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
//                        code : 200 => 삭제 성공
                        val code = json.getInt("code")

                        if (code == 200) {
//                            실제 삭제 : 목록 변화 필요함. => 서버에서 다시 불러오기.
                            getReplyDetailFromServer()

                        }
                    }

                })


            })
            alert.setNegativeButton("취소", null)
            alert.show()

            return@setOnItemLongClickListener true
        }

        postReReplyBtn.setOnClickListener {
            val content = reReplyContentEdt.text.toString()

//            답글 등록 API 찾아보기 활용법 숙지

//            답글 등록 성공시 => 리스트뷰의 내용 새로고침

//            서버에서 다시 답글 목록을 받아와서 추가


//            한번 등록하면 내용을 수정할 수 없다고 안내
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("내용 변경 불가 안내")
            alert.setMessage("한번 등록한 의견은 내용을 수정할 수 없습니다. 의견을 등록한 뒤에는 재투표가 불가능합니다.")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//                서버에 요청해서 의견 등록 처리 => 추가 데이터를 받어와야함.
                ServerUtil.postRequestTopicReReply(mContext,mReplyId , content, object :ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {

    //                                서버에서 다시 의견에 대한 상세 현황 가져오기
                            getReplyDetailFromServer()

    //                                답글 등록시 성공 관련 UI 처리
                            runOnUiThread {
//                                입력했던 내용 삭제
                                reReplyContentEdt.setText("")

//                                키보드도 내려주자
                                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(reReplyContentEdt.windowToken, 0)
                            }

                    }

                })
            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

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

//                기존에 담겨있던 답글 목록을 날리고
                reReplyList.clear()

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

//                    내가 단 답글을 보기 편하도록 스크롤 처리
//                    답글 10개 -> ArrayList는 0~9번까지.
//                    10개답글 => 9번을 보러 가는게 마지막으로 이동하는 행위임.
                    reReplyListView.smoothScrollToPosition(reReplyList.size-1)

                }


            }

        })

    }
}
