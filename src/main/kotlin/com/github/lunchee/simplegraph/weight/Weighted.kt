package com.github.lunchee.simplegraph.weight

/**
 * Edges of a Weighted Graph implement this interface to provide their Weights.
 */
interface Weighted<WeightValueT : Comparable<WeightValueT>> {

    /**
     * Returns weight of an Edge.
     */
    fun weight(): Weight<WeightValueT>
}