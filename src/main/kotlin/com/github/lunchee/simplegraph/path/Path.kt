package com.github.lunchee.simplegraph.path

import com.github.lunchee.simplegraph.Connection

/**
 * An ordered list of Edges that lead from one Vertex to another.
 */
data class Path<VertexT, EdgeT>(val connections: List<Connection<VertexT, EdgeT>>) {
    companion object {
        fun <VertexT, EdgeT> empty() = Path<VertexT, EdgeT>(listOf())
    }

    /**
     * Adds another Edge to the Path.
     * The added Edge should start from the same Vertex as where the Path ends.
     *
     * @param connection an added edge
     * @return an extended path
     */
    operator fun plus(connection: Connection<VertexT, EdgeT>): Path<VertexT, EdgeT> {
        if (connections.isNotEmpty() && connections.last().to != connection.from) {
            throw IllegalArgumentException("An added connection does not continue the path")
        }

        return Path(connections + connection)
    }
}