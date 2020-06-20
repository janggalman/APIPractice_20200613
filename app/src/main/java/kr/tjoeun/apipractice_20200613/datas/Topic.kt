package kr.tjoeun.apipractice_20200613.datas

import org.json.JSONObject

class Topic {

    //    Json을 넣으면 Topic 객체로 변환해주는 기능
    companion object {
        fun getTopicFromJson(json:JSONObject) : Topic {
            val topic = Topic()

//            빈 내용으로 만든 topic의 내용을
//            json에 담겨있는 내용을 가지고 채워준다.

            topic.id = json.getInt("id")
            topic.title = json.getString("title")
            topic.imageUrl = json.getString("img_url")

//            주제 파싱중 => 선택진영들 JSONArray을 가져오기
            val sides = json.getJSONArray("sides")

//            JSONArray 내부를 돌면서 파싱
            for (i in 0..sides.length()-1) {

//                선택진영 하나의 정보가 담긴 JSONObject 추출
                val sideJson = sides.getJSONObject(i)

//                이 Json을 선택진영으로 변환해주는 기능 사용
                val side = TopicSide.getTopicSideFromJson(sideJson)

//                이 주제의 진영 목록으로 추가
                topic.sides.add(side)

            }

//            댓글목록도 같이 파싱
            val replies = json.getJSONArray("replies")

//            댓글 JSONArray를 돌면서 => 파싱한 내용을 => topic.replies에 추가
            for (i in 0..replies.length()-1) {
                val replyJson = replies.getJSONObject(i)
                val reply = TopicReply.getTopicReplyFromJson(replyJson)
                topic.replies.add(reply)
            }

//            내가 선택한 진영이 어디인지?
//            id값 저장
            topic.mySideId = json.getInt("my_side_id")

//            서버에서 my_side 항목을 제대로 내려줄때만 파싱
            if (!json.isNull("my_side")){

//            실제 진영의 정보 저장
//            my_side로 되어있는 { } 이용 => TopicSide로 변환해서 대입
                topic.mySelectedSide = TopicSide.getTopicSideFromJson(json.getJSONObject("my_side"))
            }






            return topic
        }

    }

    var id = 0
    var title = ""
    var imageUrl = ""
//    선택 가능 진영 목록을 담는 배열
    val sides = ArrayList<TopicSide>()
//    의견 목록을 담는 배열
    val replies = ArrayList<TopicReply>()

//    선택진영의 id값 : -1일 경우 미선택으로 처리 (투표 안함)
    var mySideId = -1
//    선택한 진영이 있을 경우 그 실제 데이터 저장 공간 => 투표를 안한경우 null이어야함.
//    선택 진영이 없어야 하니까. => TopicSide? 로 저장.
    var mySelectedSide : TopicSide? = null




}