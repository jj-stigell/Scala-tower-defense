package TowerGame.Towers

import TowerGame.Helpers.Vector2D
import TowerGame.Settings
import scala.swing.Color

/** Small type tower, fast at shooting but not so effective */
class SmallTower(val location: Vector2D) extends Tower(location) {
  val towerColor: Color = new Color(23, 67, 125)
  val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)
  val coolDownTime: Int = 50
  val range: Double = 130
  val coolDownPerCycle: Int = 1
  val damageGivenPerHit: Int = 1
  val price: Int = 10
}
