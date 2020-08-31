package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Graph
import com.github.lunchee.simplegraph.Vertex

/**
 * A graph traversal algorithm that searches for a path between any two given Vertices.
 *
 * If the path will be optimal depends on implementation of the algorithm.
 */
interface PathSearchAlgorithm<EdgeT> {

    /**
     * Finds a path between two given Vertices in a Graph.
     *
     * @param graph a Graph to traverse
     * @param from a starting Vertex
     * @param to a destination Vertex
     * @return a found Path between the two Vertices or null if path is not found
     */
    fun <VertexT> findPath(
        graph: Graph<VertexT, EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>?
}