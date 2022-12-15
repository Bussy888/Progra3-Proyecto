package com.grupoC.anderylosandersaurios.classes

import com.grupoC.anderylosandersaurios.activity.LoginActivity.Companion.VIBRATION
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
    private var colorTexts: List<String> = listOf("red", "yellow", "blue", "green")
    private var texts: List<String> = listOf("red", "yellow", "blue", "green", "")
    private var plusTime: Int = 0
    private var minusTime: Int = 0

    private lateinit var contract: Contract

    var timeVibration: Long = 200

    fun checking(colorCheck: String): Int {
        when (colorCheck) {
            "blue" -> {
                if (colorCheck == contract.rightColor) {
                    blueCabinet.score++
                    increasingPlus()
                } else {
                    blueCabinet.score--
                    increasingMinus()
                    if (VIBRATION) {
                        activity.vibration(timeVibration)
                    }
                }
                return blueCabinet.score
            }
            "red" -> {
                if (colorCheck == contract.rightColor) {
                    redCabinet.score++
                    increasingPlus()
                } else {
                    redCabinet.score--
                    increasingMinus()
                    if (VIBRATION) {
                        activity.vibration(timeVibration)
                    }
                }
                return redCabinet.score
            }
            "yellow" -> {
                if (colorCheck == contract.rightColor) {
                    yellowCabinet.score++
                    increasingPlus()
                } else {
                    yellowCabinet.score--
                    increasingMinus()
                    if (VIBRATION) {
                        activity.vibration(timeVibration)
                    }
                }
                return yellowCabinet.score
            }
            else -> {
                if (colorCheck == contract.rightColor) {
                    greenCabinet.score++
                    increasingPlus()
                } else {
                    greenCabinet.score--
                    increasingMinus()
                    if (VIBRATION) {
                        activity.vibration(timeVibration)
                    }
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

    fun generateContract() {
        shuffleParameters()
        contract = Contract(colors[0], colorTexts[0], texts[0])
        contract.defineColor()
        activity.idSContracts("folder_${contract.image}")
    }

    fun getFinalScore(): Int {
        val finalScore: Int =
            blueCabinet.score + redCabinet.score + yellowCabinet.score + greenCabinet.score
        return if (finalScore > 0) finalScore else 0
    }

    private fun increasingPlus() {
        plusTime++
        if (plusTime == 5) {
            activity.addExtraSeconds()
            plusTime = 0
        }
    }

    private fun increasingMinus() {
        minusTime++
        if (minusTime == 5) {
            activity.subtractExtraSeconds()
            minusTime = 0
        }
    }
}