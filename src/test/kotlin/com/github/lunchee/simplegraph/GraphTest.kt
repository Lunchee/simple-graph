package com.github.lunchee.simplegraph

import com.github.lunchee.simplegraph.path.Path
import com.github.lunchee.simplegraph.weight.WeightedEdge
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GraphTest {

    @Test
    fun `a newly created graph should have no vertices`() {
        // given
        val graph = UndirectedGraph<String, String>()

        // when
        val vertices: List<Vertex<String>> = getVertices(graph)

        // then
        vertices.shouldBeEmpty()
    }

    private fun getVertices(graph: Graph<String, String>): List<Vertex<String>> {
        val vertices = mutableListOf<Vertex<String>>()
        graph.traverse { vertex -> vertices.add(vertex) }

        return vertices
    }

    @Test
    fun `should add a new vertex to a graph`() {
        // given
        val graph = UndirectedGraph<String, String>()

        // when
        val addedVertex: Vertex<String> = graph.addVertex("First Vertex")

        // then
        getVertices(graph).shouldContainExactly(addedVertex)
    }

    @Test
    fun `should add an edge between two vertices in an undirected graph`() {
        // given
        val graph = UndirectedGraph<String, String>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val secondVertex: Vertex<String> = graph.addVertex("Second Vertex")

        // when
        graph.addEdge(firstVertex, secondVertex, "An Edge")

        // then
        graph.getConnections(firstVertex).shouldContainExactly(
            Connection(from = firstVertex, to = secondVertex, edge = "An Edge")
        )
        graph.getConnections(secondVertex).shouldContainExactly(
            Connection(from = secondVertex, to = firstVertex, edge = "An Edge")
        )
    }

    @Test
    fun `should add an edge between two vertices in a directed graph`() {
        // given
        val graph = DirectedGraph<String, String>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val secondVertex: Vertex<String> = graph.addVertex("Second Vertex")

        // when
        graph.addEdge(firstVertex, secondVertex, "An Edge")

        // then
        graph.getConnections(firstVertex).shouldContainExactly(
            Connection(from = firstVertex, to = secondVertex, edge = "An Edge")
        )
        graph.getConnections(secondVertex).shouldBeEmpty()
    }

    @Test
    fun `should not add an edge to an unknown source vertex`() {
        // given
        val graph = DirectedGraph<String, String>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val unknownVertex: Vertex<String> = Vertex(id = 2, "Unknown Vertex")

        // then
        assertThrows<VertexNotFoundException> {
            graph.addEdge(unknownVertex, firstVertex, "An Edge")
        }
    }

    @Test
    fun `should not add an edge to an unknown destination vertex`() {
        // given
        val graph = DirectedGraph<String, String>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val unknownVertex: Vertex<String> = Vertex(id = 2, "Unknown Vertex")

        // then
        assertThrows<VertexNotFoundException> {
            graph.addEdge(firstVertex, unknownVertex, "An Edge")
        }
    }

    @Test
    fun `traverse should visit every node of a graph`() {
        // given
        val graph = UndirectedGraph<String, String>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val secondVertex: Vertex<String> = graph.addVertex("Second Vertex")
        val thirdVertex: Vertex<String> = graph.addVertex("Third Vertex")

        graph.addEdge(firstVertex, secondVertex, "An Edge")

        // when
        val visitedVertices = mutableListOf<Vertex<String>>()
        graph.traverse { vertex -> visitedVertices.add(vertex) }

        // then
        visitedVertices.shouldContainExactlyInAnyOrder(firstVertex, secondVertex, thirdVertex)
    }

    @Test
    fun `getPath returns a possibly non-optimal path between two nodes using a default algorithm`() {
        // given
        val graph = UndirectedGraph<String, WeightedEdge>()
        val firstVertex: Vertex<String> = graph.addVertex("First Vertex")
        val secondVertex: Vertex<String> = graph.addVertex("Second Vertex")
        val thirdVertex: Vertex<String> = graph.addVertex("Third Vertex")

        graph.addEdge(firstVertex, secondVertex, WeightedEdge(1))
        graph.addEdge(secondVertex, thirdVertex, WeightedEdge(2))
        graph.addEdge(firstVertex, thirdVertex, WeightedEdge(10))

        // when
        val path: Path<String, WeightedEdge>? = graph.getPath(from = firstVertex, to = thirdVertex)

        // then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(Connection(from = firstVertex, to = thirdVertex, edge = WeightedEdge(10)))
    }
}