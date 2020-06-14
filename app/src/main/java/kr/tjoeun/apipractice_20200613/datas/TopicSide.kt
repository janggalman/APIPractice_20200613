package kr.tjoeun.apipractice_20200613.datas

import org.json.JSONObject

// 주제의 하위개념 : 선택 가능 진영 정보
class TopicSide {

    //    JSON => TopicSide로 변환 가능
    companion object {

        fun getTopicSideFromJson(json : JSONObject) : TopicSide {
            val ts = TopicSide()

            ts.id = json.getInt("id")
            ts.topicId = json.getInt("topic_id")
            ts.title = json.getString("title")
            ts.voteCount = json.getInt("vote_count")

            return ts
        }
    }
    var id = 0
    var topicId = 0
    var title = ""
    var voteCount = 0


}