package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Connection
import com.github.lunchee.simplegraph.DirectedGraph
import com.github.lunchee.simplegraph.UndirectedGraph
import com.github.lunchee.simplegraph.weight.WeightedEdge
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

class DijkstraShortestPathTest {

    @Test
    fun `should find a path from only one edge`() {
        // given
        val graph = UndirectedGraph<String, WeightedEdge>()

        val firstVertex = graph.addVertex("First")
        val secondVertex = graph.addVertex("Second")
        graph.addEdge(firstVertex, secondVertex, WeightedEdge(42))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, firstVertex, secondVertex)

        // then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(Connection(firstVertex, secondVertex, WeightedEdge(42)))
    }

    @Test
    fun `should return null if the given vertices are not connected`() {
        // given
        val graph = UndirectedGraph<String, WeightedEdge>()

        val firstVertex = graph.addVertex("First")
        val secondVertex = graph.addVertex("Second")
        val thirdVertex = graph.addVertex("Third")
        graph.addEdge(firstVertex, secondVertex, WeightedEdge(42))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, firstVertex, thirdVertex)

        // then
        path.shouldBeNull()
    }

    @Test
    fun `path to self should have empty connections`() {
        // given
        val graph = UndirectedGraph<String, WeightedEdge>()

        val firstVertex = graph.addVertex("First")
        val secondVertex = graph.addVertex("Second")
        graph.addEdge(firstVertex, secondVertex, WeightedEdge(42))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, firstVertex, firstVertex)

        // then
        path.shouldNotBeNull()
        path.connections.shouldBeEmpty()
    }

    @Test
    fun `should find a shortest path for an example directed graph from Baeldung`() {
        // given
        val graph = DirectedGraph<String, WeightedEdge>()

        val a = graph.addVertex("A")
        val b = graph.addVertex("B")
        val c = graph.addVertex("C")
        val d = graph.addVertex("D")
        val e = graph.addVertex("E")
        val f = graph.addVertex("F")

        graph.addEdge(a, b, WeightedEdge(10))
        graph.addEdge(a, c, WeightedEdge(15))
        graph.addEdge(b, d, WeightedEdge(12))
        graph.addEdge(b, f, WeightedEdge(15))
        graph.addEdge(c, e, WeightedEdge(10))
        graph.addEdge(d, e, WeightedEdge(2))
        graph.addEdge(d, f, WeightedEdge(1))
        graph.addEdge(f, e, WeightedEdge(5))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, a, e)

        // then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            Connection(a, b, WeightedEdge(10)),
            Connection(b, d, WeightedEdge(12)),
            Connection(d, e, WeightedEdge(2))
        )
    }

    @Test
    fun `should find a shortest path for an example directed graph from GeeksForGeeks`() {
        // given
        val graph = UndirectedGraph<Int, WeightedEdge>()

        val vertex0 = graph.addVertex(0)
        val vertex1 = graph.addVertex(1)
        val vertex2 = graph.addVertex(2)
        val vertex3 = graph.addVertex(3)
        val vertex4 = graph.addVertex(4)
        val vertex5 = graph.addVertex(5)
        val vertex6 = graph.addVertex(6)
        val vertex7 = graph.addVertex(7)
        val vertex8 = graph.addVertex(8)

        graph.addEdge(vertex0, vertex1, WeightedEdge(4))
        graph.addEdge(vertex0, vertex7, WeightedEdge(8))
        graph.addEdge(vertex1, vertex2, WeightedEdge(8))
        graph.addEdge(vertex1, vertex7, WeightedEdge(11))
        graph.addEdge(vertex2, vertex3, WeightedEdge(7))
        graph.addEdge(vertex2, vertex5, WeightedEdge(4))
        graph.addEdge(vertex2, vertex8, WeightedEdge(2))
        graph.addEdge(vertex3, vertex4, WeightedEdge(9))
        graph.addEdge(vertex3, vertex5, WeightedEdge(14))
        graph.addEdge(vertex4, vertex5, WeightedEdge(10))
        graph.addEdge(vertex5, vertex6, WeightedEdge(2))
        graph.addEdge(vertex6, vertex7, WeightedEdge(1))
        graph.addEdge(vertex6, vertex8, WeightedEdge(6))
        graph.addEdge(vertex7, vertex8, WeightedEdge(7))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, vertex0, vertex8)

        // then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            Connection(vertex0, vertex1, WeightedEdge(4)),
            Connection(vertex1, vertex2, WeightedEdge(8)),
            Connection(vertex2, vertex8, WeightedEdge(2))
        )

        // and when
        val anotherPath = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, vertex0, vertex4)

        // then
        anotherPath.shouldNotBeNull()
        anotherPath.connections.shouldContainExactly(
            Connection(vertex0, vertex7, WeightedEdge(8)),
            Connection(vertex7, vertex6, WeightedEdge(1)),
            Connection(vertex6, vertex5, WeightedEdge(2)),
            Connection(vertex5, vertex4, WeightedEdge(10))
        )
    }

    @Test
    fun `should find a shortest path for an example directed graph from Wikipedia`() {
        // given
        val graph = UndirectedGraph<Int, WeightedEdge>()

        val vertex1 = graph.addVertex(1)
        val vertex2 = graph.addVertex(2)
        val vertex3 = graph.addVertex(3)
        val vertex4 = graph.addVertex(4)
        val vertex5 = graph.addVertex(5)
        val vertex6 = graph.addVertex(6)

        graph.addEdge(vertex1, vertex2, WeightedEdge(7))
        graph.addEdge(vertex1, vertex3, WeightedEdge(9))
        graph.addEdge(vertex1, vertex6, WeightedEdge(14))
        graph.addEdge(vertex2, vertex3, WeightedEdge(10))
        graph.addEdge(vertex2, vertex4, WeightedEdge(15))
        graph.addEdge(vertex3, vertex4, WeightedEdge(11))
        graph.addEdge(vertex3, vertex6, WeightedEdge(2))
        graph.addEdge(vertex4, vertex5, WeightedEdge(6))
        graph.addEdge(vertex5, vertex6, WeightedEdge(9))

        // when
        val path = DijkstraShortestPath<WeightedEdge, Int>().findPath(graph, vertex1, vertex5)

        // then
        path.shouldNotBeNull()
        path.connections.shouldContainExactly(
            Connection(vertex1, vertex3, WeightedEdge(9)),
            Connection(vertex3, vertex6, WeightedEdge(2)),
            Connection(vertex6, vertex5, WeightedEdge(9))
        )
    }

}