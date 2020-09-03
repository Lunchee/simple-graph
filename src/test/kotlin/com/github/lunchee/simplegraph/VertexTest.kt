package com.github.lunchee.simplegraph

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VertexTest {

    @Test
    fun `a Vertex should be equal to self`() {
        val vertex = Vertex(id = 1, payload = "Vertex")

        assertTrue(vertex.equals(vertex))
    }

    @Test
    fun `a Vertex should not be equal to null`() {
        val vertex = Vertex(id = 1, payload = "Vertex")

        assertFalse(vertex.equals(null))
    }

    @Test
    fun `a Vertex should not be equal to an object of another class`() {
        Vertex(id = 1, payload = "Vertex") shouldNotBe 1
    }

    @Test
    fun `two Vertices with the same IDs should be equal`() {
        Vertex(id = 1, payload = "Vertex") shouldBe Vertex(id = 1, payload = "Another Vertex")
    }

    @Test
    fun `two Vertices with different IDs should not be equal`() {
        Vertex(id = 1, payload = "Vertex") shouldNotBe Vertex(id = 2, payload = "Another Vertex")
    }

    @Test
    fun `two Vertices with the same IDs should have the same hashCode`() {
        Vertex(id = 1, payload = "Vertex").hashCode() shouldBe Vertex(id = 1, payload = "Another Vertex").hashCode()
    }

    @Test
    fun `two Vertices with different IDs should have different hashCodes`() {
        Vertex(id = 1, payload = "Vertex").hashCode() shouldNotBe Vertex(id = 2, payload = "Another Vertex").hashCode()
    }
}