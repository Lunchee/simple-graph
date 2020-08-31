package com.github.lunchee.simplegraph

import org.junit.jupiter.api.Assertions.*
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
        assertNotEquals(Vertex(id = 1, payload = "Vertex"), 1)
    }

    @Test
    fun `two Vertices with the same IDs should be equal`() {
        assertEquals(
            Vertex(id = 1, payload = "Vertex"),
            Vertex(id = 1, payload = "Another Vertex")
        )
    }

    @Test
    fun `two Vertices with different IDs should not be equal`() {
        assertNotEquals(
            Vertex(id = 1, payload = "Vertex"),
            Vertex(id = 2, payload = "Another Vertex")
        )
    }

    @Test
    fun `two Vertices with the same IDs should have the same hashCode`() {
        assertEquals(
            Vertex(id = 1, payload = "Vertex").hashCode(),
            Vertex(id = 1, payload = "Another Vertex").hashCode()
        )
    }

    @Test
    fun `two Vertices with different IDs should have different hashCodes`() {
        assertNotEquals(
            Vertex(id = 1, payload = "Vertex").hashCode(),
            Vertex(id = 2, payload = "Another Vertex").hashCode()
        )
    }
}