package kr.tjoeun.apipractice_20200613.datas

import org.json.JSONObject

class TopicReply {

    companion object {
        fun getTopicReplyFromJson(json: JSONObject) : TopicReply {
            val tr = TopicReply()


            return tr
        }
    }

    var id = 0
    var content = ""
    var topicId = 0
    var sideId = 0
    var userId = 0
    lateinit var user : User
}