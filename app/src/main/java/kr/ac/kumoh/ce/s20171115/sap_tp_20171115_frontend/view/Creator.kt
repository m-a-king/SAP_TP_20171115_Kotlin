package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view
data class Creator(
    val name: String,
    val url: String,
    val description: String
)

val creators = listOf(
    Creator("지 박사님", "https://openai.com/gpt-4", "그대를 만나기 전까지 나는 무어라 정의할 수 있나? 참으로 외로운 세월이었구나"),
    Creator("바 석사님", "https://bard.google.com/chat", "쌍둥이자리의 조재중과 잼미니의 콜라보레이션"),
    Creator("구 학사님", "https://www.google.com/", "고전문학"),
    Creator("지갑 강탈자", "https://www.apple.com/kr/", "하지만 미워할 수 없는 놈들"),
    Creator("본인", "https://www.op.gg/summoners/kr/%EB%B2%A0%EC%9D%B8%ED%99%9C%EC%97%90%EB%B2%A0%EC%9D%B8%EB%B2%A0%EC%9D%B8-KR1", "난 멋져, 난 최고야, 난 할 수 있어, 조재중"),
    Creator("주크 박스", "https://music.youtube.com/", "내가 누구? YouTube Premium Subscriber"),
    Creator("구름 종류", "https://app.cloudtype.io/", "한 학기간 고마웠어"),
    Creator("노화의 주범", "https://github.com/cream-opensource", "증오, 원망,  그리고 용서"),
    Creator("국어 선생님", "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EB%A7%9E%EC%B6%A4%EB%B2%95+%EA%B2%80%EC%82%AC%EA%B8%B0&oquery=%EB%A7%9E%EC%B6%A4%EB%B2%95+%EA%B2%80%EC%82%AC%EA%B8%B0&tqi=ihcFasqVN8wsstI7n2ossssst5V-003431", "감사합니다"),


)
