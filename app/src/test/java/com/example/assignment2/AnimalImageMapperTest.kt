package com.example.assignment2

import com.example.assignment2.util.AnimalImageMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class AnimalImageMapperTest {

    @Test
    fun `African Elephant returns elephant image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "African Elephant"
            )

        assertEquals(
            R.drawable.african_elephant,
            result
        )
    }


    @Test
    fun `Giant Panda returns panda image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Giant Panda"
            )

        assertEquals(
            R.drawable.giant_panda,
            result
        )
    }


    @Test
    fun `Blue Whale returns whale image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Blue Whale"
            )

        assertEquals(
            R.drawable.blue_whale,
            result
        )
    }


    @Test
    fun `Komodo Dragon returns komodo image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Komodo Dragon"
            )

        assertEquals(
            R.drawable.komodo_dragon,
            result
        )
    }


    @Test
    fun `Emperor Penguin returns penguin image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Emperor Penguin"
            )

        assertEquals(
            R.drawable.emperor_penguin,
            result
        )
    }


    @Test
    fun `Red Panda returns red panda image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Red Panda"
            )

        assertEquals(
            R.drawable.red_panda,
            result
        )
    }


    @Test
    fun `Platypus returns platypus image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Platypus"
            )

        assertEquals(
            R.drawable.platypus,
            result
        )
    }


    @Test
    fun `unknown species returns default elephant image`() {

        val result =
            AnimalImageMapper.getImageResource(
                "Unknown Animal"
            )

        assertEquals(
            R.drawable.african_elephant,
            result
        )
    }
}