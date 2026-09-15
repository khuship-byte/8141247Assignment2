package com.example.assignment2.util

import com.example.assignment2.R

object AnimalImageMapper {

    fun getImageResource(species: String): Int {
        return when (species) {
            "African Elephant" -> R.drawable.african_elephant
            "Giant Panda" -> R.drawable.giant_panda
            "Blue Whale" -> R.drawable.blue_whale
            "Komodo Dragon" -> R.drawable.komodo_dragon
            "Emperor Penguin" -> R.drawable.emperor_penguin
            "Red Panda" -> R.drawable.red_panda
            "Platypus" -> R.drawable.platypus
            else -> R.drawable.african_elephant
        }
    }
}