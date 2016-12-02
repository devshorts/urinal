package com.onoffswitch.urinals

import org.scalatest._

class Tests extends FlatSpec with Matchers {
  "Stall selector" should "choose an open left corner" in {
    val stalls =
      Stall(isFree = true). // 0
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        flattenRight

    stalls.head.rightFree shouldEqual 2

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    result.get.id shouldEqual 0

    println(display)
  }

  it should "choose an open right corner " in {
    val stalls =
      Stall(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true). // 5
        flattenRight

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    result.get.id shouldEqual 5

    println(display)
  }

  it should "choose a middle" in {
    val stalls =
      Stall(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true). // 2
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        flattenRight

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    println(display)

    result.get.id shouldEqual 2
  }

  it should "choose closest to the door even if there are more available" in {
    val stalls =
      Stall(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = true). // 6
        flattenRight

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    println(display)

    result.get.id shouldEqual 6
  }

  it should "choose the only one open :(" in {
    val stalls =
      Stall(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = false).
        flattenRight

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    result.get.id shouldEqual 1

    println(display)
  }

  it should "select the one with the most privacy" in {
    val stalls =
      Stall(isFree = false).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true).
        toTheRight(isFree = false).
        toTheRight(isFree = true). // 6 (its closer to the door)
        toTheRight(isFree = false).
        flattenRight

    val (result, display) = Solver.display(stalls, WeightsStrategy)

    println(display)

    result.get.id shouldEqual 6
  }
}
