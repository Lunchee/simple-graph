package com.github.lunchee.simplegraph.weight

/**
 * An example of Weight that holds an Int value.
 */
inline class IntWeight(override val value: Int) : Weight<Int> {
    override fun plus(other: Weight<Int>): Weight<Int> {
        return IntWeight(this.value + other.value)
    }
}