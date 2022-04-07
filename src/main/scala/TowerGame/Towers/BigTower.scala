package TowerGame.Towers

import TowerGame.Helpers.Vector2D
import TowerGame.Settings
import scala.swing.Color

/** Big type tower, slow at shooting but effective with good range */
class BigTower(location: Vector2D) extends Tower(location) {
  val towerColor: Color = new Color(140, 218, 23)
  val towerType: String = "big"
  val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 2)
  val coolDownTime: Int = 100
  val range: Double = 200
  val coolDownPerCycle: Int = 1
  val damageGivenPerHit: Int = 5
  val price: Int = Settings.bigTowerPrice
  var towerLocation: Vector2D = location
}
