package TowerGame.Towers

import TowerGame.Config.Settings
import TowerGame.Helpers.Vector2D

import scala.swing.Color

/** Small type tower, fast at shooting but not so effective */
class SmallTower(location: Vector2D) extends Tower {
  val towerColor: Color = new Color(23, 67, 125)
  val towerType: String = "small"
  val size = 3  // Smaller the size, bigger the tower
  var towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / this.size)
  val coolDownTime: Int = 50
  val range: Double = 130
  val coolDownPerCycle: Int = 1
  val damageGivenPerHit: Int = 1
  val price: Int = Settings.smallTowerPrice
  var towerLocation: Vector2D = location
}
