package com.github.lunchee.simplegraph

/**
 * An Undirected Graph.
 *
 * This class is not thread-safe.
 *
 * @see [Graph]
 */
class UndirectedGraph<VertexT, EdgeT> : AbstractGraph<VertexT, EdgeT>() {
    override fun connect(from: Vertex<VertexT>, to: Vertex<VertexT>, withEdge: EdgeT) {
        addConnection(Connection(from, to, withEdge))
        addConnection(Connection(from = to, to = from, withEdge))
    }
}