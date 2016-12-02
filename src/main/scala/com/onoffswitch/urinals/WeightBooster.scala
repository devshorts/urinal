//
// Copyright (c) 2011-2016 by Curalate, Inc.
//

package com.onoffswitch.urinals

trait WeightBooster {
  def weight(stall: Stall): Int
}

class LeastOccupiedClusters() extends WeightBooster {
  override def weight(stall: Stall): Int = {
    -1 * (stall.leftOccupied + stall.rightOccupied)
  }
}

class DistanceFromWall(totalStalls: Int, totalOpen: Int) extends WeightBooster {
  private val half = totalStalls / 2

  override def weight(stall: Stall): Int = {
    /**
     * Create an inverted priority. If the ID's are
     *
     * 0 1 2 3 4
     *
     * We want a priority of
     *
     * 2 1 0 1 2
     *
     * So we can boost being closer to a door with the dead middle being the worst spot
     */
    val boost = if (stall.id < half) {
      half - (stall.id % half)
    } else {
      stall.id - half
    }

    boost
  }
}

class Corner() extends WeightBooster {
  override def weight(stall: Stall): Int = {
    if (stall.left.isEmpty || stall.right.isEmpty) 1 else 0
  }
}

class Centered() extends WeightBooster {
  override def weight(stall: Stall): Int = {
    if (stall.leftFree == stall.rightFree) 1 else 0
  }
}