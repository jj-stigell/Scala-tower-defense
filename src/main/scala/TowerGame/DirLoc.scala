package TowerGame

// for calculating the direction of movement and location of enemy
object DirLoc {

  val map = Settings.map

  //calculate direction for individual enemy
  def enemyInitialLocation(): (Int, Int) = {

    // find entry point 2 and next 1 from it
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

  // Return the direction into which the enemy travels.
  def enemyInitialDirection(entryLocation: (Int, Int)): (Int, Int) = {


        // find entry point 2 and next 1 from it
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

}
