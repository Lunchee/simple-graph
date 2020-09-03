package com.github.lunchee.simplegraph.path.distance

import com.github.lunchee.simplegraph.weight.IntWeight
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class DistanceTest {

    @Test
    fun `positive distance is always greater than zero distance`() {
        PositiveDistance(IntWeight(Int.MIN_VALUE)) shouldBeGreaterThan ZeroDistance()
    }

    @Test
    fun `positive distance is always less than unknown distance`() {
        PositiveDistance(IntWeight(Int.MAX_VALUE)) shouldBeLessThan UnknownDistance()
    }

    @Test
    fun `positive distance should be equal to another positive distance with the same value`() {
        PositiveDistance(IntWeight(42)) shouldBe PositiveDistance(IntWeight(42))
    }

    @Test
    fun `positive distance should not be equal to another positive distance with a different value`() {
        PositiveDistance(IntWeight(1)) shouldNotBe PositiveDistance(IntWeight(2))
    }

    @Test
    fun `positive distance with the same values should have the same hashCode`() {
        PositiveDistance(IntWeight(42)).hashCode() shouldBe PositiveDistance(IntWeight(42)).hashCode()
    }

    @Test
    fun `positive distance with different values should have different hashCode`() {
        PositiveDistance(IntWeight(1)).hashCode() shouldNotBe PositiveDistance(IntWeight(2)).hashCode()
    }

    @Test
    fun `positive distance should be less than another positive distance with a higher value`() {
        PositiveDistance(IntWeight(1)) shouldBeLessThan PositiveDistance(IntWeight(2))
    }

    @Test
    fun `positive distance should be greater than another positive distance with a lesser value`() {
        PositiveDistance(IntWeight(2)) shouldBeGreaterThan PositiveDistance(IntWeight(1))
    }

    @Test
    fun `positive distances with the same value should return 0 in compareTo`() {
        PositiveDistance(IntWeight(42)) shouldBeEqualComparingTo PositiveDistance(IntWeight(42))
    }

    @Test
    fun `positive distance may be added with a weighted value`() {
        PositiveDistance(IntWeight(1)).plus(IntWeight(41)) shouldBe PositiveDistance(IntWeight(42))
    }

    @Test
    fun `zero distance is always less than a positive distance`() {
        ZeroDistance<Int>() shouldBeLessThan PositiveDistance(IntWeight(Int.MIN_VALUE))
    }

    @Test
    fun `zero distance is always less than an unknown distance`() {
        ZeroDistance<Int>() shouldBeLessThan UnknownDistance()
    }

    @Test
    fun `zero distance should be equal to another zero distance`() {
        ZeroDistance<Int>() shouldBe ZeroDistance<String>()
    }

    @Test
    fun `different zero distances should have the same hashCode`() {
        ZeroDistance<Int>().hashCode() shouldBe ZeroDistance<String>().hashCode()
    }

    @Test
    fun `zero distance plus a weighted value results in a positive distance`() {
        ZeroDistance<Int>().plus(IntWeight(42)) shouldBe PositiveDistance(IntWeight(42))
    }

    @Test
    fun `unknown distance is always greater than a positive distance`() {
        UnknownDistance<Int>() shouldBeGreaterThan PositiveDistance(IntWeight(Int.MAX_VALUE))
    }

    @Test
    fun `unknown distance is always greater than a zero distance`() {
        UnknownDistance<Int>() shouldBeGreaterThan ZeroDistance()
    }

    @Test
    fun `unknown distance should be equal to another unknown distance`() {
        UnknownDistance<Int>() shouldBe UnknownDistance<String>()
    }

    @Test
    fun `different unknown distances should have the same hashCode`() {
        UnknownDistance<Int>().hashCode() shouldBe UnknownDistance<String>().hashCode()
    }

    @Test
    fun `unknown distance plus a weighted value results in the same unknown distance`() {
        UnknownDistance<Int>().plus(IntWeight(42)) shouldBe UnknownDistance()
    }
}