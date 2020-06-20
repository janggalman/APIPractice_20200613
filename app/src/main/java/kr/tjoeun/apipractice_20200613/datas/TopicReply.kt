package kr.tjoeun.apipractice_20200613.datas

import org.json.JSONObject

class TopicReply {

    companion object {
        fun getTopicReplyFromJson(json: JSONObject) : TopicReply {
            val tr = TopicReply()

            tr.id = json.getInt("id")
            tr.content = json.getString("content")
            tr.topicId = json.getInt("topic_id")
            tr.sideId = json.getInt("side_id")
            tr.userId = json.getInt("user_id")

            //의견 JSON의 항목중 user JSONObject를 => User클래스에 전달
            // User클래스가 Json을 받어서 => User로 변환해서 리턴
//            tr.user 에 대입

            val userJson = json.getJSONObject("user")
            tr.user = User.getUserFromJson(userJson)

//            답글/좋아요/싫어요 갯수 파싱
            tr.replyCount = json.getInt("reply_count")
            tr.likeCount = json.getInt("like_count")
            tr.dislikeCount = json.getInt("dislike_count")

            return tr
        }
    }

    var id = 0
    var content = ""
    var topicId = 0
    var sideId = 0
    var userId = 0
    lateinit var user : User

//    답글/좋아요/싫어요 갯수 저장 변수
    var replyCount = 0
    var likeCount = 0
    var dislikeCount = 0
    
}