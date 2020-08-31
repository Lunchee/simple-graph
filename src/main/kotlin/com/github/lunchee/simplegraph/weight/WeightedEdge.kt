package com.github.lunchee.simplegraph.weight

/**
 * An example of a Weighted Edge that holds an integer value as weight.
 */
data class WeightedEdge(private val weight: Int) : Weighted<Int> {
    override fun weight() = IntWeight(weight)
}