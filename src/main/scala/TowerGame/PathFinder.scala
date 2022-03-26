package TowerGame

import scala.collection.mutable
import scala.collection.mutable.Buffer

object PathFinder {

  /**
   * Finds the enemy path through the map from starting point (2 on array) to the ending point (3 on array).
   *
   * @param Location  Starting location of the enemy
   * @param Direction Starting direction of the enemy
   * @return          Zip with turning points on the map and direction to there
   */
  def enemyPath(Location: (Int, Int), Direction: (Int, Int)) = {

    val map = Settings.map
    var pathCollection = mutable.Buffer[(Int, Int)]()
    var directionCollection = mutable.Buffer[(Int, Int)]()
    var currentLocation = Location
    var currentDirection = Direction
    var i = 0
    var y = 0

    while (i != 3) {
      val newLocation = (currentLocation._1 + currentDirection._1, currentLocation._2 + currentDirection._2)
      val pathValue = map(newLocation._2)(newLocation._1)
      if (pathValue == 3) {
        // Map ends at 3
        i = 3
        pathCollection += newLocation
        directionCollection += currentDirection
      } else if (pathValue == 0) {
        pathCollection += currentLocation
        directionCollection += currentDirection
        currentDirection = findNewDirection(currentDirection, currentLocation)
      } else {
        currentLocation = newLocation
      }
    }
    pathCollection.zip(directionCollection)
  }

  /**
   * Find new direction for the enemy path after hitting a corner.
   *
   * @param currentDirection  Direction the enemy is going at the moment
   * @param currentLocation   Current locatoin of the enemy
   * @return New direction on the map
   */
  def findNewDirection(currentDirection: (Int, Int), currentLocation: (Int, Int)): (Int, Int) = {

    val map = Settings.map
    val previousLocation = (currentLocation._1 - (1 * currentDirection._1), currentLocation._2 - (1 * currentDirection._2))

    val option1 = (currentLocation._1 + 1, currentLocation._2)
    val option2 = (currentLocation._1, currentLocation._2 + 1)
    val option3 = (currentLocation._1 - 1, currentLocation._2)
    val option4 = (currentLocation._1, currentLocation._2 - 1)
    var newDirection = (0, 0)

    if ((map(option1._2)(option1._1) == 1 || map(option1._2)(option1._1) == 3) && (previousLocation._1 - 1, previousLocation._2) != currentLocation) {
      newDirection = (1, 0)
    } else if ((map(option2._2)(option2._1) == 1 || map(option2._2)(option2._1) == 3) && (previousLocation._1, previousLocation._2 - 1) != currentLocation) {
      newDirection = (0, 1)
    } else if ((map(option3._2)(option3._1) == 1 || map(option3._2)(option3._1) == 3) && (previousLocation._1 + 1, previousLocation._2) != currentLocation) {
      newDirection = (-1, 0)
    } else if ((map(option4._2)(option4._1) == 1 || map(option4._2)(option4._1) == 3) && (previousLocation._1, previousLocation._2 + 1) != currentLocation) {
      newDirection = (0, -1)
    }
    newDirection
  }

  /**
   * Find the entry point of the enemy in the map array.
   *
   * @return x and y coordinate in the map array where the enemy will start the movement
   */
  def enemyInitialLocation(): (Int, Int) = {

    val map = Settings.map
    var x = 0
    var y = 0
    var rowNumber = 0

    for (row <- map) {
      var columnNumber = 0
      for (element <- row) {
        if (element == 2) {
          x = columnNumber
          y = rowNumber
        }
        columnNumber += 1
      }
      rowNumber += 1
    }
    (x, y)
  }

  /**
   * Find the start direction of the enemy in the map
   *
   * @param entryLocation Location where the enemy enter the game arena, marked 2 on the map array
   * @return              Direction of movement on the map array, either horizontal or vertical but not diagonal
   */
  def findInitialDirection(entryLocation: (Int, Int)): (Int, Int) = {

    val map = Settings.map
    var x = 0
    var y = 0
    var rowNumber = 0

    for (row <- map) {
      var columnNumber = 0
      for (element <- row) {
        if (element == 1) {
          val xDiff = columnNumber - entryLocation._1
          val yDiff = rowNumber - entryLocation._2
          if (xDiff == 0 || yDiff == 0) {
            x = xDiff
            y = yDiff
          }
        }
        columnNumber += 1
      }
      rowNumber += 1
    }
    if (x > 0 && y == 0) (1,0)
    else if (x < 0 && y == 0) (-1,0)
    else if (x == 0 && y > 0) (0,1)
    else if (x == 0 && y < 0) (0,-1)
    else (0,0)
  }

  /**
   * Needed for calculating the area surrounding the enemy path.
   *
   * @param correctedPath Path where the enemies move
   * @return              Padding for the enemy path to stop placing towers in the middle of the enemy path
   */
  def findBannedAreas(correctedPath: Buffer[Vector2D]) = {

    // Buffer[((x1, y1), (x2, y2))]
    var blockPath: Buffer[((Double, Double), (Double, Double))] = Buffer[((Double, Double), (Double, Double))]()
    var i: Int = 0
    val modifier: Double = 1.2

    while (i < correctedPath.length - 1) {

      val x1 = correctedPath(i).x
      val x2 = correctedPath(i + 1).x
      val y1 = correctedPath(i).y
      val y2 = correctedPath(i + 1).y

      if (x1 < x2) blockPath = blockPath :+ ((x1, y1 - ((1.0 * Settings.blockLengthY) / modifier)), (x2,  y2 + ((1.0 * Settings.blockLengthY) / modifier)))
      else if (x1 > x2) blockPath = blockPath :+ ((x2, y2 - ((1.0 * Settings.blockLengthY) / modifier)), (x1,  y1 + ((1.0 * Settings.blockLengthY) / modifier)))
      else if (y1 < y2) blockPath = blockPath :+ ((x1 - ((1.0 * Settings.blockLengthX) / modifier), y1),((x2 + ((1.0 * Settings.blockLengthX) / modifier),  y2)))
      else blockPath = blockPath :+ ((x2 - ((1.0 * Settings.blockLengthX) / modifier), y2),((x1 + ((1.0 * Settings.blockLengthX) / modifier),  y1)))

      i += 1
    }
    blockPath
  }

}
