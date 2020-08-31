package com.github.lunchee.simplegraph

/**
 * Represents a connection between two Vertices with an Edge.
 */
data class Connection<VertexT, EdgeT>(
    val from: Vertex<VertexT>,
    val to: Vertex<VertexT>,
    val edge: EdgeT
)