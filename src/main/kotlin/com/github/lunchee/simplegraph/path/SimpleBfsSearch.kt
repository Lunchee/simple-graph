package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Graph
import com.github.lunchee.simplegraph.Vertex

/**
 * A simple Breadth-First Search algorithm that finds a Path between two Vertices of an Unweighted Graph.
 *
 * See [Wikipedia](https://en.wikipedia.org/wiki/Breadth-first_search)
 */
class SimpleBfsSearch<EdgeT> : PathSearchAlgorithm<EdgeT> {
    override fun <VertexT> findPath(
        graph: Graph<VertexT, EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>? {
        val startingPoint = VertexPath(vertex = from, path = Path.empty<VertexT, EdgeT>())
        val pathQueue = ArrayDeque(listOf(startingPoint))
        val visitedVertices = HashSet<Vertex<VertexT>>()

        while (!pathQueue.isEmpty()) {
            val currentPoint = pathQueue.first()

            if (visitedVertices.contains(currentPoint.vertex)) {
                continue
            }

            graph.getConnections(currentPoint.vertex).asSequence()
                .filter { connection -> connection.to == to || !visitedVertices.contains(connection.to) }
                .map { connection -> VertexPath(vertex = connection.to, path = currentPoint.path + connection) }
                .forEach { nextPoint ->
                    if (nextPoint.vertex == to) return nextPoint.path
                    else pathQueue.addLast(nextPoint)
                }

            visitedVertices.add(currentPoint.vertex)
        }

        return null
    }

    private data class VertexPath<VertexT, EdgeT>(val vertex: Vertex<VertexT>, val path: Path<VertexT, EdgeT>)
}