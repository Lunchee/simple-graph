package com.github.lunchee.simplegraph

import com.github.lunchee.simplegraph.path.Path
import com.github.lunchee.simplegraph.path.PathSearchAlgorithm
import com.github.lunchee.simplegraph.path.SimpleBfsSearch

/**
 * Base class for Directed and Undirected Graphs.
 *
 * @see Graph
 */
abstract class AbstractGraph<VertexT, EdgeT> : Graph<VertexT, EdgeT> {

    private val outgoingConnections = HashMap<Vertex<VertexT>, MutableList<Connection<VertexT, EdgeT>>>()
    private var nextVertexId = 0

    override fun addVertex(payload: VertexT): Vertex<VertexT> {
        val vertex = Vertex(nextVertexId++, payload)
        outgoingConnections[vertex] = mutableListOf()

        return vertex
    }

    override fun addEdge(from: Vertex<VertexT>, to: Vertex<VertexT>, edge: EdgeT) {
        checkVertexPresence(from)
        checkVertexPresence(to)
        connect(from, to, edge)
    }

    private fun checkVertexPresence(vertex: Vertex<VertexT>) {
        if (outgoingConnections[vertex] == null) {
            throw VertexNotFoundException(vertex)
        }
    }

    protected abstract fun connect(from: Vertex<VertexT>, to: Vertex<VertexT>, withEdge: EdgeT)

    protected fun addConnection(connection: Connection<VertexT, EdgeT>) {
        outgoingConnections[connection.from]?.add(connection)
            ?: throw VertexNotFoundException(connection.from)
    }

    override fun getConnections(vertex: Vertex<VertexT>): List<Connection<VertexT, EdgeT>> {
        return outgoingConnections[vertex]
            ?: throw VertexNotFoundException(vertex)
    }

    override fun getPath(from: Vertex<VertexT>, to: Vertex<VertexT>): Path<VertexT, EdgeT>? {
        return getPath(SimpleBfsSearch(), from, to)
    }

    override fun getPath(
        algorithm: PathSearchAlgorithm<EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>? {
        return algorithm.findPath(this, from, to)
    }

    override fun traverse(function: (Vertex<VertexT>) -> Unit) {
        outgoingConnections.keys.forEach { function(it) }
    }
}



