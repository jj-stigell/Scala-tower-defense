package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.swing.Color

class Tower(val place: Vector2D) {

  private var onCooldown = false
  private var coolDownCounter = 0
  private var location: Vector2D = place
  private val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  def shoot() = {

    if (onCooldown) {

      coolDownCounter -= Settings.coolDownPerCycle

      if (coolDownCounter == 0) {
        onCooldown = false
      }

    } else {
      // shoot near by enemy, search enemies on proximity
      onCooldown = true
      coolDownCounter = Settings.coolDownTime
    }

  }




  def draw(g: Graphics2D) = {
    val circle = new Ellipse2D.Double(10, 10, towerSize, towerSize)
    g.setColor(new Color(178, 123, 255))
    val oldTransform = g.getTransform
    g.translate(location.x, location.y)
    g.fill(circle)
    g.setTransform(oldTransform)
  }

}
