package kr.tjoeun.apipractice_20200613.utils

import android.content.Context

class ContextUtil {

    companion object {
//        메모장의 파일이름에 대응되는 개념으로 만든 변수
        private val prefName = "APIPracticePreference"

//        저장된 항목이름 변수 설정
        private val USER_TOKEN = "USER_TOKEN"
        private val AUTO_LOGIN = "AUTO_LOGIN"

//        자동로그인 getter / setter
        fun setAutoLogin (context: Context, autoLogin:Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN , autoLogin).apply()
        }

        fun isAutoLogin(context: Context) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }

//        토큰 저장 기능
        fun setUserToken(context: Context, token: String) {
//        저장할때 사용할 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//    열어준 메모장의 USER_TOKEN 항목에 받아온 token에 든 값을 저장.
            pref.edit().putString(USER_TOKEN , token).apply()
        }

//        저장된 토큰 불러오기
        fun getUserToken(context: Context) : String {
//        저장할때 사용할 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//          열어준 메모장의 USER_TOKEN 항목에 저장된 token값을 꺼내서 리턴 (USER_TOKEN이 없을 경우 디폴트값 지정 "")
            return pref.getString(USER_TOKEN, "")!!
        }
    }
}