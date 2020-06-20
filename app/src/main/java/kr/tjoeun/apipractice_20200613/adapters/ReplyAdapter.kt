package kr.tjoeun.apipractice_20200613.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kr.tjoeun.apipractice_20200613.R
import kr.tjoeun.apipractice_20200613.datas.TopicReply
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(
    val mContext : Context,
    val resId: Int ,
    val mList: List<TopicReply>) : ArrayAdapter<TopicReply> (mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }
        val row =tempRow!!

//        XML에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contextTxt)
        val replyBtn = row.findViewById<Button>(R.id.replyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

//        목로에서 뿌려줄 데이터 꺼내오기

        val data = mList[position]

        
//        데이터/뷰 연결 => 알고리즘
        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content
        
        replyBtn.text = "답글 : ${data.replyCount}"
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        내 좋아요 /싫어요 여부 표시
        if (data.isMyLike) {
//            내가 좋아요를 찍은 댓글일 경우
//            좋아요 빨간색 /싫어요 회색
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            //            좋아요 글씨 색 :빨간색 => res => colors => red를 사용
            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }
        else if (data.isMyDislike) {
//            내가 싫어요를 찍은 댓글일 경우
//            좋아요 회색 / 싫어요 파란색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))

        }
        else {
//            아무것도 찍지 않은 경우
//            둘다 회색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

        //좋아요 / 싫어요 이벤트 처리
        likeBtn.setOnClickListener {
//좋아요 API호출 => 좋아요 누르기 /취소 처리
            ServerUtil.postRequestReplyLike(mContext, data.id, true , object :ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {


//            화면에 변경된 좋아요/싫어요 갯수 반영(응용)
                val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

//                    목록에서 꺼낸 data변수의 객체를 통채로 바꾸는건 불가능.
//                    var 로 바꿔서 통째로 바꿔도 => 목록에는 반영되지 않음.

//                    목록에서 꺼낸 data변수의 좋아요 갯수 /싫어요 갯수를 직접 변경
                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

//                    목록의 내용을 일부 변경 => 반영하려면
//                    어댑터.notifyDataSEtChanged() 실행 필요함.
//                    이미 어뎁터 내부에 있는 상황 = > 곧바로 notifyDataSetChanged() 실행 가능

//                    runOnUiThread 로 처리 필요 => 어뎁터내부에선 사용 불가.
//                    대체제 :Handler(Looper.getMainLooper()).post (UI쓰레드 접근하는 다른 방법)
                    Handler(Looper.getMainLooper()).post{
                        notifyDataSetChanged()
                    }

                }
            })

        }

        dislikeBtn.setOnClickListener {
            ServerUtil.postRequestReplyLike(mContext, data.id, false, object :ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

                    Handler(Looper.getMainLooper()).post{
                        notifyDataSetChanged()
                    }
                }

            })

        }

        return row
    }
}