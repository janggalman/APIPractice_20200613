package kr.tjoeun.apipractice_20200613.datas

import org.json.JSONObject

class User {
    var id = 0
    var email =""
    var nickName = ""

    companion object {

//        사용자 정보 JSON을 넣으면 => User로 변환해주는 기능
        fun getUserFromJson(json:JSONObject) : User {
            val u = User()

//    들어오는 json으로 u의 내용 채우기
            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")

            return u
        }

    }
}