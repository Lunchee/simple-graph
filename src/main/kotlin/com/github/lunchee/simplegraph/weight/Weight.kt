package com.github.lunchee.simplegraph.weight

/**
 * Weight of an Edge.
 *
 * Every Edge of a Weighted Graph has a Weight that is described by this interface.
 */
interface Weight<T : Comparable<T>> {
    /**
     * Value of weight, e.g. 42 for Int
     */
    val value: T

    /**
     * Returns a sum of this and other weights.
     */
    operator fun plus(other: Weight<T>): Weight<T>
}