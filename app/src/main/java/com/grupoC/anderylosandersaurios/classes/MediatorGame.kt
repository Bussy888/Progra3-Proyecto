package com.grupoC.anderylosandersaurios.classes

import com.grupoC.anderylosandersaurios.activity.MainActivity

data class MediatorGame(
    val blueCabinet: Cabinet,
    val redCabinet: Cabinet,
    val yellowCabinet: Cabinet,
    val greenCabinet: Cabinet,
    val buttonsContracts: List<ButtonContract>,
    val activity:MainActivity
) {
    private var colors: List<String> = listOf("red", "yellow", "blue", "green")
    private var colorTexts: List<String> = listOf("r", "y", "b", "g")
    private var texts: List<String> = listOf("r", "y", "b", "g", "")

    private lateinit var contract: Contract

    fun checking(colorCheck: String): Int {
        when (colorCheck) {
            "blue" -> {
                blueCabinet.score++
                return blueCabinet.score
            }
            "red" -> {
                redCabinet.score++
                return redCabinet.score
            }
            "yellow" -> {
                yellowCabinet.score++
                return yellowCabinet.score
            }
            else -> {
                greenCabinet.score++
                return greenCabinet.score
            }
        }
    }

    fun shuffleParameters() {
        colors = colors.shuffled()
        colorTexts = colors.shuffled()
        texts = texts.shuffled()
    }

    fun generateContract() {
        shuffleParameters()
        contract = Contract(colors[0], colorTexts[0], texts[0])
        contract.defineColor()
        activity.idSContracts("folder_${contract.image}")
    }

    fun identifyButtonColor() {

    }
}