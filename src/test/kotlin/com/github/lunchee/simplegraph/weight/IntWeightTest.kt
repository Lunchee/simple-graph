package com.github.lunchee.simplegraph.weight

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class IntWeightTest {

    @Test
    fun `int weight may be added with another weighted value`() {
        IntWeight(1).plus(IntWeight(2)) shouldBe IntWeight(3)
    }
}