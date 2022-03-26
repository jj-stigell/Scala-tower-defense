package TowerGame.Towers

import TowerGame.Vector2D
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.swing.Color

abstract class Tower(location: Vector2D) {

  val coolDownTime: Int
  val towerSize: Int
  val range: Double
  val towerColor: Color
  val coolDownPerCycle: Int
  val damagePerHit: Int
  var enemyNearBy = false
  var coolDownCounter = 0

  def getLocation = this.location

  def scanProximity() = ???

  def shoot() = {
    if (this.coolDownCounter > 0) {
      this.coolDownCounter -= this.coolDownPerCycle
    } else {
      // shoot near by enemy, search enemies on proximity
      this.coolDownCounter = this.coolDownTime
    }
  }

  def draw(g: Graphics2D) = {
    val circle = new Ellipse2D.Double(10, 10, this.towerSize, this.towerSize)
    g.setColor(this.towerColor)
    val oldTransform = g.getTransform
    g.translate(this.location.x, this.location.y)
    g.fill(circle)
    g.setTransform(oldTransform)
  }

}
