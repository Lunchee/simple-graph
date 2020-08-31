package com.github.lunchee.simplegraph.concurrent

import com.github.lunchee.simplegraph.Connection
import com.github.lunchee.simplegraph.Graph
import com.github.lunchee.simplegraph.path.Path
import com.github.lunchee.simplegraph.Vertex
import com.github.lunchee.simplegraph.path.PathSearchAlgorithm
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A wrapper around a Graph that adds thread-safety for operations on the Graph.
 * Thread-safety is achieved by using a [ReadWriteLock]: every operation that changes
 * a structure of a Graph takes a Write Lock for duration of the operation.
 * Every other operation takes a Read Lock.
 *
 * It is guaranteed that any read operation will see a consistent view of a Graph
 * from the moment when the last Write operation completed.
 *
 * At any point in time only one Write operation is allowed to run,
 * while other operations wait for the Write operation to complete.
 *
 * @param wrappedGraph an underlying thread-unsafe Graph
 */
class ReadWriteLockedGraph<VertexT, EdgeT>(
    private val wrappedGraph: Graph<VertexT, EdgeT>
) : Graph<VertexT, EdgeT> {

    private val lock = ReentrantReadWriteLock()

    override fun addVertex(payload: VertexT): Vertex<VertexT> {
        return lock.write { wrappedGraph.addVertex(payload) }
    }

    override fun addEdge(from: Vertex<VertexT>, to: Vertex<VertexT>, edge: EdgeT) {
        lock.write { wrappedGraph.addEdge(from, to, edge) }
    }

    override fun getConnections(vertex: Vertex<VertexT>): List<Connection<VertexT, EdgeT>> {
        return lock.read { wrappedGraph.getConnections(vertex) }
    }

    override fun getPath(from: Vertex<VertexT>, to: Vertex<VertexT>): Path<VertexT, EdgeT>? {
        return lock.read { wrappedGraph.getPath(from, to) }
    }

    override fun getPath(
        algorithm: PathSearchAlgorithm<EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>? {
        return lock.read { wrappedGraph.getPath(algorithm, from, to) }
    }

    override fun traverse(function: (Vertex<VertexT>) -> Unit) {
        lock.read { wrappedGraph.traverse(function) }
    }
}