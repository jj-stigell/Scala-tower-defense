package TowerGame.Towers

import TowerGame.Config.Settings
import TowerGame.Helpers.Vector2D

import scala.swing.Color

/** Big type tower, slow at shooting but effective with good range */
class BigTower(location: Vector2D) extends Tower {
  val towerColor: Color = new Color(140, 218, 23)
  val towerType: String = "big"
  val size = 2  // Smaller the size, bigger the tower
  var towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / this.size)
  val coolDownTime: Int = 100
  val range: Double = 200
  val coolDownPerCycle: Int = 1
  val damageGivenPerHit: Int = 5
  val price: Int = Settings.bigTowerPrice
  var towerLocation: Vector2D = location
}
