package com.onoffswitch.urinals

import scala.annotation.tailrec

case class Stall(isFree: Boolean, id: Int = 0, private[urinals] var left: Option[Stall] = None, right: Option[Stall] = None) {
  def toTheRight(isFree: Boolean): Stall = {
    val stall = Stall(isFree, id + 1)
    stall.left = Some(this.copy(right = Some(stall)))
    stall
  }

  override def toString: String = (id, isFree).toString()

  private def flatten(direction: Stall => Option[Stall]) = {
    @tailrec
    def flattenHelper(current: Option[Stall], seed: List[Stall] = Nil): List[Stall] = {
      if (current.isEmpty) {
        seed
      } else {
        val next = direction(current.get)

        flattenHelper(next, current.get :: seed)
      }
    }

    flattenHelper(Some(this))
  }

  def flattenRight: List[Stall] = flatten(_.left)

  def flattenLeft: List[Stall] = flatten(_.right)

  private def countFree(next: Stall => Option[Stall]) = {
    @tailrec
    def count(current: Stall, num: Int): Int = {
      next(current) match {
        case Some(stall) if stall.isFree =>
          count(stall, num + 1)
        case _ => num
      }
    }

    if (isFree) {
      count(this, 1)
    } else {
      0
    }
  }

  def leftFree: Int = countFree(_.left)

  def rightFree: Int = countFree(_.right)

  /**
   * Contiguous occupied blocks on the left ignoring free seats inbetween this and occupied
   * @return
   */
  def leftOccupied: Int = flattenRight.reverse.dropWhile(_.isFree).takeWhile(!_.isFree).size

  /**
   * Contiguous occupied blocks on the right ignoring free seats inbetween this and occupied
   * @return
   */

  def rightOccupied: Int = flattenLeft.dropWhile(_.isFree).takeWhile(!_.isFree).size
}
