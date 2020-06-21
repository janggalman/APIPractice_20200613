package kr.tjoeun.apipractice_20200613.adapters

import android.content.Context
import android.content.Intent
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
import kr.tjoeun.apipractice_20200613.ViewReplyDetailActivity
import kr.tjoeun.apipractice_20200613.datas.TopicReply
import kr.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ReReplyAdapter(
    val mContext : Context,
    val resId: Int ,
    val mList: List<TopicReply>) : ArrayAdapter<TopicReply> (mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_re_reply_list_item, null)
        }
        val row =tempRow!!

//        XML에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contextTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

//        목로에서 뿌려줄 데이터 꺼내오기

        val data = mList[position]

        
//        데이터/뷰 연결 => 알고리즘


        return row
    }

}