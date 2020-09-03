package com.github.lunchee.simplegraph

import com.github.lunchee.simplegraph.path.Path
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PathTest {

    @Test
    fun `may be empty`() {
        val emptyPath = Path.empty<String, String>()

        emptyPath.connections.shouldBeEmpty()
    }

    @Test
    fun `should return a new path when adding new edge`() {
        // given
        val emptyPath = Path.empty<String, String>()

        // when
        val firstNodePath = emptyPath.plus(
            Connection(
                from = Vertex(id = 1, "First"),
                to = Vertex(id = 2, "Second"),
                edge = "1-2"
            )
        )

        //then
        emptyPath.connections.shouldBeEmpty()
        firstNodePath.connections.shouldContainExactly(
            Connection(
                from = Vertex(id = 1, "First"),
                to = Vertex(id = 2, "Second"),
                edge = "1-2"
            )
        )
    }

    @Test
    fun `should add two edges to an empty path`() {
        // given
        var path = Path.empty<String, String>()

        // when
        path = path.plus(Connection(from = Vertex(1, "V1"), to = Vertex(2, "V2"), edge = "1-2"))
        path = path.plus(Connection(from = Vertex(2, "V2"), to = Vertex(3, "V3"), edge = "2-3"))

        // then
        path.connections.shouldContainExactly(
            Connection(from = Vertex(1, "V1"), to = Vertex(2, "V2"), edge = "1-2"),
            Connection(from = Vertex(2, "V2"), to = Vertex(3, "V3"), edge = "2-3")
        )
    }

    @Test
    fun `an added connection should start from the last Vertex of the path`() {
        // given
        val path = Path(listOf(Connection(from = Vertex(1, "V1"), to = Vertex(2, "V2"), edge = "1-2")))

        // then
        assertThrows<IllegalArgumentException> {
            path + Connection(from = Vertex(3, "V3"), to = Vertex(4, "V4"), edge = "3-4")
        }
    }
}