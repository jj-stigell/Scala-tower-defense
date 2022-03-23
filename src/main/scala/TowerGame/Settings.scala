package TowerGame

object Settings {

  val width = 1200
  val height = 800
  val fullHeight = height + 50    // take into account the control panel at the bottom
  val towerPrice = 10
  val maxHealth = 100
  val hpLossPerEnemy = 1
  val coolDownTime = 100
  val coolDownPerCycle = 1
  val enemyHealth = 10
  val enemySpeed = 1              // scalar for the enemy speed

  val title = "TowerDefense"
  val resizable = false

  // Each row must be same lenght
  /*
  val map = Array(
    Array(1, 1, 0, 0),
    Array(0, 1, 0, 0),
    Array(0, 1, 1, 1),
    Array(0, 0, 0, 0)
  )
  */

  // 1 road, roads cannot be side to side
  // 2 entry point, must be on the side, only one
  // 3 out point, must be on the side, only one

  val map = Array(
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    Array(3, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0)
  )

  // how many blocks the map has, horizontal and vertical
  val totalHorizontalBlocks = map(0).length
  val totalVerticalBlocks = map.length

  val blockLengthX = width / totalHorizontalBlocks
  val blockLengthY = height / totalVerticalBlocks

}
