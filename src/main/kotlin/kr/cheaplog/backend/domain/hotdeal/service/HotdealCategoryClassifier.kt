package kr.cheaplog.backend.domain.hotdeal.service

import org.springframework.stereotype.Component

@Component
class HotdealCategoryClassifier {

    private val categoryKeywords = mapOf(
        "laptop" to listOf("맥북", "노트북", "그램", "갤럭시북", "macbook", "laptop", "thinkpad"),
        "phone" to listOf("아이폰", "갤럭시", "픽셀", "iphone", "galaxy s", "galaxy z"),
        "tablet" to listOf("아이패드", "갤럭시탭", "ipad", "galaxy tab"),
        "audio" to listOf("에어팟", "버즈", "헤드폰", "이어폰", "airpods", "earbuds", "headphone"),
        "pc_parts" to listOf("rtx", "cpu", "ssd", "ram", "그래픽카드", "gpu", "메모리", "파워서플라이"),
        "appliance" to listOf("냉장고", "세탁기", "에어컨", "청소기", "건조기", "식기세척기"),
        "food" to listOf("과자", "음료", "커피", "라면", "식품", "간식")
    )

    fun classify(productName: String?): String {
        if (productName.isNullOrBlank()) return "etc"
        val lower = productName.lowercase()
        for ((category, keywords) in categoryKeywords) {
            if (keywords.any { lower.contains(it) }) {
                return category
            }
        }
        return "etc"
    }
}
