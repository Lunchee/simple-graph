package com.github.lunchee.simplegraph.path.distance

import com.github.lunchee.simplegraph.weight.IntWeight
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DistanceTest {

    @Test
    fun `positive distance is always greater than zero distance`() {
        val positiveDistance = PositiveDistance(IntWeight(Int.MIN_VALUE))
        val zeroDistance = ZeroDistance<Int>()

        assertTrue(positiveDistance > zeroDistance)
    }

    @Test
    fun `positive distance is always less than unknown distance`() {
        val positiveDistance = PositiveDistance(IntWeight(Int.MAX_VALUE))
        val unknownDistance = UnknownDistance<Int>()

        assertTrue(positiveDistance < unknownDistance)
    }

    @Test
    fun `positive distance should be equal to another positive distance with the same value`() {
        assertEquals(PositiveDistance(IntWeight(42)), PositiveDistance(IntWeight(42)))
    }

    @Test
    fun `positive distance should not be equal to another positive distance with a different value`() {
        assertNotEquals(PositiveDistance(IntWeight(1)), PositiveDistance(IntWeight(2)))
    }

    @Test
    fun `positive distance with the same values should have the same hashCode`() {
        assertEquals(
            PositiveDistance(IntWeight(42)).hashCode(),
            PositiveDistance(IntWeight(42)).hashCode()
        )
    }

    @Test
    fun `positive distance with different values should have different hashCode`() {
        assertNotEquals(
            PositiveDistance(IntWeight(1)).hashCode(),
            PositiveDistance(IntWeight(2)).hashCode()
        )
    }

    @Test
    fun `positive distance should be less than another positive distance with a higher value`() {
        assertTrue(PositiveDistance(IntWeight(1)) < PositiveDistance(IntWeight(2)))
    }

    @Test
    fun `positive distance should be greater than another positive distance with a lesser value`() {
        assertTrue(PositiveDistance(IntWeight(2)) > PositiveDistance(IntWeight(1)))
    }

    @Test
    fun `positive distances with the same value should return 0 in compareTo`() {
        assertEquals(PositiveDistance(IntWeight(42)).compareTo(PositiveDistance(IntWeight(42))), 0)
    }

    @Test
    fun `positive distance may be added with a weighted value`() {
        val positiveDistance = PositiveDistance(IntWeight(1))

        assertEquals(positiveDistance.plus(IntWeight(41)), PositiveDistance(IntWeight(42)))
    }

    @Test
    fun `zero distance is always less than a positive distance`() {
        assertTrue(ZeroDistance<Int>() < PositiveDistance(IntWeight(Int.MIN_VALUE)))
    }

    @Test
    fun `zero distance is always less than an unknown distance`() {
        assertTrue(ZeroDistance<Int>() < UnknownDistance())
    }

    @Test
    fun `zero distance should be equal to another zero distance`() {
        assertEquals(ZeroDistance<Int>(), ZeroDistance<String>())
    }

    @Test
    fun `different zero distances should have the same hashCode`() {
        assertEquals(ZeroDistance<Int>().hashCode(), ZeroDistance<String>().hashCode())
    }

    @Test
    fun `zero distance plus a weighted value results in a positive distance`() {
        val zeroDistance = ZeroDistance<Int>()

        assertEquals(zeroDistance.plus(IntWeight(42)), PositiveDistance(IntWeight(42)))
    }

    @Test
    fun `unknown distance is always greater than a positive distance`() {
        assertTrue(UnknownDistance<Int>() > PositiveDistance(IntWeight(Int.MAX_VALUE)))
    }

    @Test
    fun `unknown distance is always greater than a zero distance`() {
        assertTrue(UnknownDistance<Int>() > ZeroDistance())
    }

    @Test
    fun `unknown distance should be equal to another unknown distance`() {
        assertEquals(UnknownDistance<Int>(), UnknownDistance<String>())
    }

    @Test
    fun `different unknown distances should have the same hashCode`() {
        assertEquals(UnknownDistance<Int>().hashCode(), UnknownDistance<String>().hashCode())
    }

    @Test
    fun `unknown distance plus a weighted value results in the same unknown distance`() {
        val unknownDistance = UnknownDistance<Int>()

        assertEquals(unknownDistance.plus(IntWeight(42)), UnknownDistance<Int>())
    }
}