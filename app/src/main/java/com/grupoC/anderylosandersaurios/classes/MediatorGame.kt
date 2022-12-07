package com.grupoC.anderylosandersaurios.classes

data class MediatorGame(
    val blueCabinet: Cabinet,
    val redCabinet: Cabinet,
    val yellowCabinet: Cabinet,
    val greenCabinet: Cabinet,
) {
    fun checking(buttonNumber: Int): Int {
        when (buttonNumber) {
            1 -> {
                blueCabinet.score++
                return blueCabinet.score
            }
            2 -> {
                redCabinet.score++
                return redCabinet.score
            }
            3 -> {
                yellowCabinet.score++
                return yellowCabinet.score
            }
            else -> {
                blueCabinet.score++
                return blueCabinet.score
            }
        }
    }


}