//
// Copyright (c) 2011-2016 by Curalate, Inc.
//

package com.onoffswitch.urinals

trait Strategy {
  def solve(stalls: List[Stall]): Option[Stall]
}

object WeightsStrategy extends Strategy {
  override def solve(stalls: List[Stall]): Option[Stall] = {
    val boosters: List[WeightBooster] = List(
      new Corner,
      new Centered,
      new LeastOccupiedClusters,
      new DistanceFromWall(stalls.size, stalls.count(_.isFree))
    )

    val stallsWithWeights =
      stalls.filter(_.isFree).map(stall => {
        val weight = stall.leftFree + stall.rightFree + boosters.map(_.weight(stall)).sum

        (stall, weight)
      })

    if (stallsWithWeights.isEmpty) {
      None
    } else {
      Some(stallsWithWeights.maxBy(_._2)._1)
    }
  }
}

object Solver {
  def apply(stalls: List[Stall], strategy: Strategy) = strategy.solve(stalls)

  def display(stalls: List[Stall], strategy: Strategy): (Option[Stall], String) = {
    val selectedStall = strategy.solve(stalls)

    (selectedStall, stalls.foldLeft("")((acc, stall) => {
      if (stall.id == selectedStall.map(_.id).getOrElse(-1)) {
        acc + " ! "
      } else if(stall.isFree) {
        acc + " o "
      } else {
        acc + " x "
      }
    }))
  }
}
