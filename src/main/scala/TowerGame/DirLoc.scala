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

    (x * Settings.blockLengthX, y * Settings.blockLengthY)


  }



}
