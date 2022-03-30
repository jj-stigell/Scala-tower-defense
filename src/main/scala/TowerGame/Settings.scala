package TowerGame

/**
 * Settings for the game, do not edit any other part of the game code except these
 * Mind the correct datatypes
 */
object Settings {

  // Game
  val width: Int = 1200
  val height: Int = 800
  val fullHeight: Int = height + 40                         // Take into account the control panel at the bottom
  val title: String = "TowerDefense"
  val resizable: Boolean = false
  val maxWaves: Int = 2

  // Player
  val towerPrice: Int = 10
  val startingMoney: Int = 10
  val maxHealth: Int = 150

  // Tower placing
  val xCorrection = 40
  val yCorrection = 40

  // Enemy
  val interval: Int = 6                                     // Enemy update interval, in milliseconds, please keep at 6
  val enemyInterval: Int = 200                              // Time between enemy addition, in milliseconds
  val numberOfEnemies: Int = 5                             // How many enemies in total appears on the map
  val correctedInterval: Int = this.enemyInterval / this.interval

  // Each row must be same length
  // 1 road, roads cannot be side to side
  // 2 entry point, must be on the side, only one
  // 3 out point, must be on the side, only one
  // Default map
  val allMaps: Array[Array[Array[Int]]] = Array(
    Array(
    Array(0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    Array(0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0),
    Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0),
    Array(0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0),
    Array(0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    ),Array(
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    Array(3, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
    Array(0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0),
    Array(0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0)
    ), Array(
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0),
    Array(0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0),
    Array(3, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0),
    Array(0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )
  )

  // Set allMaps head as the first map
  var map: Array[Array[Int]] = allMaps.head

  // How many blocks the map has, horizontal and vertical and their lengths
  var totalHorizontalBlocks: Int = this.map(0).length
  var totalVerticalBlocks: Int = this.map.length
  var blockLengthX: Int = this.width / this.totalHorizontalBlocks
  var blockLengthY: Int = this.height / this.totalVerticalBlocks

  /**
   * Set new map for the game and update blocks and lengths
   *
   * @param newMap New map Array[Array[Int]]
   */
  def setMap(newMap: Array[Array[Int]] ) = {
    this.map = newMap
    this.totalHorizontalBlocks = this.map(0).length
    this.totalVerticalBlocks = this.map.length
    this.blockLengthX = this.width / this.totalHorizontalBlocks
    this.blockLengthY = this.height / this.totalVerticalBlocks
  }

}
