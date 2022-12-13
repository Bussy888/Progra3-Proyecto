package com.grupoC.anderylosandersaurios.classes

import com.grupoC.anderylosandersaurios.activity.MainActivity

data class MediatorGame(
    val blueCabinet: Cabinet,
    val redCabinet: Cabinet,
    val yellowCabinet: Cabinet,
    val greenCabinet: Cabinet,
    val buttonsContracts: List<ButtonContract>,
    val activity: MainActivity
) {
    private var colors: List<String> = listOf("red", "yellow", "blue", "green")
    private var colorTexts: List<String> = listOf("r", "y", "b", "g")
    private var texts: List<String> = listOf("r", "y", "b", "g", "")

    private lateinit var contract: Contract

    fun checking(colorCheck: String): Int {
        when (colorCheck) {
            "blue" -> {
                if (colorCheck == contract.rightColor) {
                    blueCabinet.score++
                } else {
                    blueCabinet.score--
                }
                return blueCabinet.score
            }
            "red" -> {
                if (colorCheck == contract.rightColor) {
                    redCabinet.score++
                } else {
                    redCabinet.score--
                }
                return redCabinet.score
            }
            "yellow" -> {
                if (colorCheck == contract.rightColor) {
                    yellowCabinet.score++
                } else {
                    yellowCabinet.score--
                }
                return yellowCabinet.score
            }
            else -> {
                if (colorCheck == contract.rightColor) {
                    greenCabinet.score++
                } else {
                    greenCabinet.score--
                }
                return greenCabinet.score
            }
        }
    }

    fun shuffleParameters() {
        colors = colors.shuffled()
        colorTexts = colors.shuffled()
        texts = texts.shuffled()
    }

    // TODO: Usar color image
    fun generateContract() {
        shuffleParameters()
        contract = Contract(colors[0], colorTexts[0], texts[0])
        contract.defineColor()
        activity.idSContracts("folder_${contract.colorPaper}")
    }

    fun getFinalScore(): String =
        "${blueCabinet.score + redCabinet.score + yellowCabinet.score + greenCabinet.score}"

    fun identifyButtonColor() {

    }
}