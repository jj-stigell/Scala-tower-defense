package TowerGame.Helpers

/** Describes a 2D mathematical vector. */
case class Vector2D(x: Double, y: Double) {

  val length = math.hypot(x, y)
  val angle = math.atan2(y, x)

  /** create a new vector with a revised angle and scaled length. */
  def derive(angleOff: Double, lengthTimes: Double) = {
    val newLen = length * lengthTimes
    val newAngle = angle + angleOff
    Vector2D(math.cos(newAngle) * newLen, math.sin(newAngle) * newLen)
  }

  /** Add two vectors and return their sum vector. */
  def +(other: Vector2D) = {
    Vector2D(x + other.x, y + other.y)
  }

  /**
   * Coordinates "turn around" so that enemies appear from the other side of the area.
   * Bound gets as parameters the window size and shape size.
   */
  def bound(xBound: Int, shapeWidth: Int, yBound: Int, shapeHeight: Int) = {
    val newX =
      if (x >= xBound + shapeWidth)
        x - xBound - 2 * shapeWidth
      else if (x < -shapeWidth)
        x + xBound + 2 * shapeWidth
      else x

    val newY =
      if (y >= yBound + shapeHeight)
        y - yBound - 2 * shapeHeight
      else if (y < -shapeHeight)
        y + yBound + 2 * shapeHeight
      else y

    if (newX != x || newY != y)
      Vector2D(newX, newY)
    else
      this
  }
}
