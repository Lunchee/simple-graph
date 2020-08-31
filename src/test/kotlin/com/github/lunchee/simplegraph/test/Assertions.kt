package com.github.lunchee.simplegraph.test

import kotlin.test.junit5.JUnit5Asserter.fail

fun interface Matcher<in T> {
    fun matches(element: T): Boolean
}

fun <E> Collection<E>.shouldContainExactly(vararg elementMatchers: Matcher<E>) {
    if (this.size != elementMatchers.size) {
        fail("Expected to have ${elementMatchers.size} elements, but was ${this.size}: $this")
    }

    val actualIterator = this.iterator()
    val expectedIterator = elementMatchers.iterator()
    var index = 0

    while (actualIterator.hasNext() && expectedIterator.hasNext()) {
        val actualValue = actualIterator.next()
        if (!expectedIterator.next().matches(actualValue)) {
            fail("Actual element at index $index is not expected: element=$actualValue; collection=$this")
        }

        index++
    }
}

fun <E> Collection<E>.shouldContainExactly(vararg elements: E) {
    val matchers: Array<Matcher<E>> = elements.map { expected ->
        Matcher<E> { element -> element == expected }
    }.toTypedArray()

    this.shouldContainExactly(*matchers)
}
