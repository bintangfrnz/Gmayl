package com.bintangfajarianto.gmayl.core

import kotlin.random.Random

abstract class RouteDestination {
    final override fun equals(other: Any?): Boolean = false
    final override fun hashCode(): Int = Random.nextInt()
}
