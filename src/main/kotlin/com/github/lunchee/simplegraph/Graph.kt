package com.github.lunchee.simplegraph

import com.github.lunchee.simplegraph.path.Path
import com.github.lunchee.simplegraph.path.PathSearchAlgorithm
import com.github.lunchee.simplegraph.weight.Weighted

/**
 * Graph is a set of [Vertices][Vertex] connected by Edges.
 * An Edge runs between two Vertices, connecting them together.
 * The are two types of connections:
 *
 * 1. Undirected -
 *    given an Edge between two Vertices, it is possible to travel in both directions:
 *    from Vertex 1 to Vertex 2 and vice-versa.
 *
 * 2. Directed -
 *    each Edge has a direction from one Vertex to another.
 *    If an Edge connects Vertex 1 with Vertex 2, it is possible to follow the connection from Vertex 1 to Vertex 2,
 *    but not otherwise.
 *
 * In the former case, a Graph is called an [Undirected Graph][UndirectedGraph], while
 * in the latter - a [Directed Graph][DirectedGraph].
 *
 * Vertices of a Graph hold user-defined data of type [VertexT],
 * while Edges may be of a custom user type [EdgeT].
 * In particular, if edges implement the [Weighted] interface, a Graph is considered to be **weighted**.
 *
 * @param VertexT type of data held by every Vertex of the Graph
 * @param EdgeT type of Edges of the Graph
 */
interface Graph<VertexT, EdgeT> {

    /**
     * Adds a new Vertex to the Graph.
     *
     * @param payload user-defined data held by a Vertex
     * @return an added Vertex
     */
    fun addVertex(payload: VertexT): Vertex<VertexT>

    /**
     * Connects two Vertices with an Edge.
     * The Vertices should be present in the Graph, otherwise an exception will be thrown.
     *
     * @param from a first Vertex to connect
     * @param to a second Vertex to connect
     * @param edge an Edge to connect the two Vertices
     * @throws VertexNotFoundException if any of the given Vertices is not in the Graph
     */
    fun addEdge(from: Vertex<VertexT>, to: Vertex<VertexT>, edge: EdgeT)

    /**
     * Returns a list of outgoing Edges from a given Vertex.
     *
     * @param vertex a Vertex existing in the Graph
     * @return connections from the Vertex to other Vertices of the Graph
     * @throws VertexNotFoundException if a given Vertex is not found in the Graph
     */
    fun getConnections(vertex: Vertex<VertexT>): List<Connection<VertexT, EdgeT>>

    /**
     * Returns a [Path] between two given Vertices.
     * A path may not be optimal, e.g. with the least amount of weight.
     *
     * @param from a starting Vertex
     * @param to a destination Vertex
     * @return a Path between two Vertices or `null` if two Vertices are not connected
     */
    fun getPath(from: Vertex<VertexT>, to: Vertex<VertexT>): Path<VertexT, EdgeT>?

    /**
     * Returns a [Path] between two given Vertices using a given traversal algorithm.
     *
     * @param algorithm an algorithm that finds a path between two Vertices
     * @param from a starting Vertex
     * @param to a destination Vertex
     * @return a Path between two Vertices or `null` if two Vertices are not connected
     */
    fun getPath(
        algorithm: PathSearchAlgorithm<EdgeT>,
        from: Vertex<VertexT>,
        to: Vertex<VertexT>
    ): Path<VertexT, EdgeT>?

    /**
     * Visits every Vertex of a Graph, applying a given function.
     *
     * @param function a function to apply on every visited Vertex
     */
    fun traverse(function: (Vertex<VertexT>) -> Unit)
}