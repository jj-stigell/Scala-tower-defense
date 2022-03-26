package TowerGame.Towers

import TowerGame.{Settings, Vector2D}
import scala.swing.Color

class SmallTower(val location: Vector2D) extends Tower(location) {

  val towerColor: Color = new Color(245, 178, 125)
  val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)
  val coolDownTime: Int = 10
  val range: Double = 1.0
  val coolDownPerCycle: Int = 1
  val damagePerHit: Int = 1

}
