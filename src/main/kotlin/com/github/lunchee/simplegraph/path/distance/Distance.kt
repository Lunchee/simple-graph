package com.github.lunchee.simplegraph.path.distance

import com.github.lunchee.simplegraph.weight.Weight

/**
 * A distance between two Vertices,
 * measured as a sum of weights of all Edges between the two Vertices.
 */
sealed class Distance<WeightValueT> : Comparable<Distance<WeightValueT>>
        where WeightValueT : Comparable<WeightValueT> {

    abstract operator fun plus(weight: Weight<WeightValueT>): Distance<WeightValueT>
}

/**
 * The distance for a starting Vertex
 * (a distance between the starting Vertex and the starting Vertex).
 */
class ZeroDistance<WeightValueT> : Distance<WeightValueT>()
        where WeightValueT : Comparable<WeightValueT> {

    override fun compareTo(other: Distance<WeightValueT>): Int {
        return -1
    }

    override fun plus(weight: Weight<WeightValueT>): Distance<WeightValueT> {
        return PositiveDistance(weight)
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is ZeroDistance<*>
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * A distance that is not yet calculated.
 */
class UnknownDistance<WeightValueT> : Distance<WeightValueT>()
        where WeightValueT : Comparable<WeightValueT> {

    override fun compareTo(other: Distance<WeightValueT>): Int {
        return 1
    }

    override fun plus(weight: Weight<WeightValueT>): Distance<WeightValueT> {
        return this
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is UnknownDistance<*>
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * A known distance between two Vertices.
 */
data class PositiveDistance<WeightValueT>(val weight: Weight<WeightValueT>) : Distance<WeightValueT>()
        where WeightValueT : Comparable<WeightValueT> {

    override fun plus(weight: Weight<WeightValueT>): Distance<WeightValueT> {
        return PositiveDistance(this.weight + weight)
    }

    override fun compareTo(other: Distance<WeightValueT>): Int {
        return when (other) {
            is PositiveDistance<WeightValueT> -> this.weight.value.compareTo(other.weight.value)
            is ZeroDistance<WeightValueT> -> 1
            is UnknownDistance<WeightValueT> -> -1
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PositiveDistance<*>

        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        return weight.hashCode()
    }
}