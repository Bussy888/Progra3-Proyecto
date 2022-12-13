package com.grupoC.anderylosandersaurios.classes

data class Contract(
    val colorPaper: String,
    val colorTextContract: String,
    val textContract: String
) {
    lateinit var rightColor: String
    lateinit var image: String

    fun defineColor() {
        if (textContract.isNotEmpty()) {
            if (colorPaper == textContract) {
                rightColor = textContract
            } else if (textContract == colorTextContract) {
                rightColor = colorTextContract
            } else {
                rightColor = colorPaper
            }
        } else {
            rightColor = colorPaper
        }
        defineImage()
    }

    fun defineImage() {
        image =
            "${colorPaper}${if (textContract.isEmpty()) "" else "_$textContract"}${if (textContract.isEmpty()) "" else "_$colorTextContract"}"
    }

    fun calculateFinalScore(hard: Boolean): Int {
        return 0
    }
}

