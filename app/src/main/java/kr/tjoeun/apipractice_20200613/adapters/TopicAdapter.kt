package kr.tjoeun.apipractice_20200613.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.tjoeun.apipractice_20200613.R
import kr.tjoeun.apipractice_20200613.datas.Topic

class TopicAdapter(
    val mContext: Context,
    val resId:Int,
    val mList:List<Topic>) : ArrayAdapter<Topic>(mContext, resId, mList) {

    val inf =LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.topic_list , null)
        }

        val row = tempRow!!

//리스트 아이템에 데이터를 반영할 뷰 가져오기.
        val topicImg = row.findViewById<ImageView>(R.id.topicImg)
        val topicTitleTxt = row.findViewById<TextView>(R.id.topicTextTxt)

//        뿌려줄 근거 데이터 가져오기
        val data = mList[position]

//        실 데이터 반영
        topicTitleTxt.text =data.title

//        Glide 라이브버리 사용
        Glide.with(mContext).load(data.imageUrl).into(topicImg)

        return row
    }
}