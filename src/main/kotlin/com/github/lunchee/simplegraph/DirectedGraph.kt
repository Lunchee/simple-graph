package com.github.lunchee.simplegraph

/**
 * A Directed Graph.
 *
 * This class is not thread-safe.
 *
 * @see [Graph]
 */
class DirectedGraph<VertexT, EdgeT> : AbstractGraph<VertexT, EdgeT>() {
    override fun connect(from: Vertex<VertexT>, to: Vertex<VertexT>, withEdge: EdgeT) {
        addConnection(Connection(from, to, withEdge))
    }
}