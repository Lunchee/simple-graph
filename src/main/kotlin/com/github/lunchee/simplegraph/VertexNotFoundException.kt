package com.github.lunchee.simplegraph

class VertexNotFoundException(vertex: Vertex<*>) : RuntimeException(
    "Graph does not contain a vertex $vertex"
)