package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Connection
import com.github.lunchee.simplegraph.Graph
import com.github.lunchee.simplegraph.Vertex
import com.github.lunchee.simplegraph.path.distance.Distance
import com.github.lunchee.simplegraph.path.distance.UnknownDistance
import com.github.lunchee.simplegraph.path.distance.ZeroDistance
import com.github.lunchee.simplegraph.weight.Weighted
import java.util.*
import kotlin.collections.HashMap

/**
 * An algorithm that works on a Weighted Graph and
 * searches for an optimal Path between two Vertices.
 *
 * See: [Wikipedia](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
 */
class DijkstraShortestPath<EdgeT, WeightValueT> : PathSearchAlgorithm<EdgeT>
        where EdgeT : Weighted<WeightValueT>,
              WeightValueT : Comparable<WeightValueT> {
    override fun <VertexT> findPath(
        graph: Graph<VertexT, EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>? {
        val distanceQueue = PriorityQueue<VertexDistance<VertexT, WeightValueT>>()
        val distances = HashMap<Vertex<VertexT>, Distance<WeightValueT>>()
        val parentConnections = HashMap<Vertex<VertexT>, Connection<VertexT, EdgeT>>()
        val visitedVertices = HashSet<Vertex<VertexT>>()

        distanceQueue.add(VertexDistance(from, ZeroDistance()))
        distances[from] = ZeroDistance()

        while (!distanceQueue.isEmpty()) {
            val currentPoint = distanceQueue.remove()

            if (currentPoint.vertex == to) {
                return shortestPath(currentPoint.vertex, parentConnections)
            }
            if (visitedVertices.contains(currentPoint.vertex)) {
                continue
            }

            graph.getConnections(currentPoint.vertex).asSequence()
                .filter { connection -> !visitedVertices.contains(connection.to) }
                .forEach { connection ->
                    val distanceToNext = currentPoint.distance + connection.edge.weight()
                    if (distanceToNext < distances[connection.to] ?: UnknownDistance()) {
                        distanceQueue.add(VertexDistance(connection.to, distanceToNext))
                        distances[connection.to] = distanceToNext
                        parentConnections[connection.to] = connection
                    }
                }

            visitedVertices.add(currentPoint.vertex)
        }

        return null
    }

    private fun <VertexT> shortestPath(
        vertex: Vertex<VertexT>,
        parentConnections: Map<Vertex<VertexT>, Connection<VertexT, EdgeT>>
    ): Path<VertexT, EdgeT> {
        val connections = mutableListOf<Connection<VertexT, EdgeT>>()

        var connectionToParent = parentConnections[vertex]
        while (connectionToParent != null) {
            connections.add(connectionToParent)
            connectionToParent = parentConnections[connectionToParent.from]
        }

        connections.reverse()

        return Path(connections)
    }

    private data class VertexDistance<VertexT, WeightValueT>(
        val vertex: Vertex<VertexT>,
        val distance: Distance<WeightValueT>
    ) : Comparable<VertexDistance<VertexT, WeightValueT>>
            where WeightValueT : Comparable<WeightValueT> {
        override fun compareTo(other: VertexDistance<VertexT, WeightValueT>): Int {
            return this.distance.compareTo(other.distance)
        }
    }
}