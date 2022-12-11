package com.grupoC.anderylosandersaurios.classes

data class MediatorGame(
    val blueCabinet: Cabinet,
    val redCabinet: Cabinet,
    val yellowCabinet: Cabinet,
    val greenCabinet: Cabinet,
    val buttonsContracts: List<ButtonContract>
) {
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

    fun identifyButtonColor() {

    }
}