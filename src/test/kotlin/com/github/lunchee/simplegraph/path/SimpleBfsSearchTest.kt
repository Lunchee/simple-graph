package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Connection
import com.github.lunchee.simplegraph.DirectedGraph
import com.github.lunchee.simplegraph.UndirectedGraph
import com.github.lunchee.simplegraph.Vertex
import com.github.lunchee.simplegraph.weight.WeightedEdge
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

class SimpleBfsSearchTest {
    companion object {
        val ANY_EDGE = WeightedEdge(42)
    }

    @Test
    fun `should find the path in undirectedGraph-line from first to last vertex`() {
        // given
        val vertexes = emptyList<Vertex<Int>>().toMutableList()
        val graph = UndirectedGraph<Int, WeightedEdge>().apply {
            vertexes.addAll(listOf(addVertex(1), addVertex(2), addVertex(3)))
            addEdge(vertexes[0], vertexes[1], ANY_EDGE)
            addEdge(vertexes[1], vertexes[2], ANY_EDGE)
        }

        //when
        val path = SimpleBfsSearch<WeightedEdge>().findPath(graph, vertexes[0], vertexes[2])

        //then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            listOf(
                Connection(vertexes[0], vertexes[1], ANY_EDGE),
                Connection(vertexes[1], vertexes[2], ANY_EDGE)
            )
        )
    }

    @Test
    fun `should find the cycle path in undirectedGraph-line from first to first vertex`() {
        // given
        val vertexes = emptyList<Vertex<Int>>().toMutableList()
        val graph = UndirectedGraph<Int, WeightedEdge>().apply {
            vertexes.addAll(listOf(addVertex(1), addVertex(2), addVertex(3)))
            addEdge(vertexes[0], vertexes[1], ANY_EDGE)
            addEdge(vertexes[1], vertexes[2], ANY_EDGE)
        }

        //when
        val path = SimpleBfsSearch<WeightedEdge>().findPath(graph, vertexes[0], vertexes[0])

        //then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            listOf(
                Connection(vertexes[0], vertexes[1], ANY_EDGE),
                Connection(vertexes[1], vertexes[0], ANY_EDGE)
            )
        )
    }

    @Test
    fun `should NOT find any path in directedGraph-line from first to first vertex`() {
        // given
        val vertexes = emptyList<Vertex<Int>>().toMutableList()
        val graph = DirectedGraph<Int, WeightedEdge>().apply {
            vertexes.addAll(listOf(addVertex(1), addVertex(2), addVertex(3)))
            addEdge(vertexes[0], vertexes[1], ANY_EDGE)
            addEdge(vertexes[1], vertexes[2], ANY_EDGE)
        }

        //when
        val path = SimpleBfsSearch<WeightedEdge>().findPath(graph, vertexes[0], vertexes[0])

        //then
        path.shouldBeNull()
    }

    @Test
    fun `should find the path in directedGraph-cycle from first to first vertex`() {
        // given
        val vertexes = emptyList<Vertex<Int>>().toMutableList()
        val graph = DirectedGraph<Int, WeightedEdge>().apply {
            vertexes.addAll(listOf(addVertex(1), addVertex(2), addVertex(3)))
            addEdge(vertexes[0], vertexes[1], ANY_EDGE)
            addEdge(vertexes[1], vertexes[2], ANY_EDGE)
            addEdge(vertexes[2], vertexes[0], ANY_EDGE)
        }

        //when
        val path = SimpleBfsSearch<WeightedEdge>().findPath(graph, vertexes[0], vertexes[0])

        //then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            listOf(
                Connection(vertexes[0], vertexes[1], ANY_EDGE),
                Connection(vertexes[1], vertexes[2], ANY_EDGE),
                Connection(vertexes[2], vertexes[0], ANY_EDGE)
            )
        )
    }
}