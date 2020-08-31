package com.github.lunchee.simplegraph.weight

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IntWeightTest {

    @Test
    fun `int weight may be added with another weighted value`() {
        val intWeight = IntWeight(1)

        assertEquals(intWeight.plus(IntWeight(2)), IntWeight(3))
    }
}