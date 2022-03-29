package TowerGame.Towers

import TowerGame.{Settings, Vector2D}
import scala.swing.Color

class BigTower(val location: Vector2D) extends Tower(location) {
  val towerColor: Color = new Color(140, 218, 23)
  val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 2)
  val coolDownTime: Int = 70
  val range: Double = 200
  val coolDownPerCycle: Int = 1
  val damageGivenPerHit: Int = 5
}
