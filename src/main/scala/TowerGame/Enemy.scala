package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.swing.Color

/**
 * Enemy is a ball (visible as its radius), speed vector, position vector
 */
case class Enemy(mass: Double, speed: Vector2D, var place: Vector2D) {

  private var alive: Boolean = true
  private var health: Int = Settings.enemyHealth
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var onTheScreen = true

  private val enemySize = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  def isAlive: Boolean = this.alive



  def gethit(): Unit = {
    if (this.health - this.damagePerHit > 0) {
      this.health -= this.damagePerHit
    } else {
      this.alive = false
    }
  }


  def move() = {



    place = place + speed
    place = place.bound(Game.width, (4 * mass).toInt, Game.height, (4 * mass).toInt)



  }



  def draw(g: Graphics2D) = {


    /*
    double	height
    The overall height of the Ellipse2D.
    double	width
    The overall width of this Ellipse2D.
    double	x
    The X coordinate of the upper-left corner of the framing rectangle of this Ellipse2D.
    double	y
    The Y coordinate of the upper-left corner of the framing rectangle of this Ellipse2D.
     */

    if (this.alive && this.onTheScreen) {


      val circle = new Ellipse2D.Double(10, 10, enemySize, enemySize)

      g.setColor(new Color(255, 0, 0))


      val oldTransform = g.getTransform()

      g.translate(place.x, place.y)

      g.fill(circle)


      g.setTransform(oldTransform)
    }
  }

}
