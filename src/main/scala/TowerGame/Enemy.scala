package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color

/**
 * Enemy is a ball (visible as its radius), speed vector, position vector
 */
case class Enemy(initDir: Vector2D, var initLoc: Vector2D, enemyPath: Buffer[(Double, Double)], enemyDirections: Buffer[(Double, Double)]) {


  private var location = initLoc
  private var speed = initDir
  private var currentSpeed = speed
  //val mass = 100
  private var alive: Boolean = true
  private var health: Int = Settings.enemyHealth
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var onTheScreen = true
  private var path = enemyPath.drop(1)            // drop first, because first is set as the turning point
  private var directions = enemyDirections.drop(1)
  private var turningPoint = Vector2D((enemyPath.head._1), (enemyPath.head._2))       // start with the first turning point

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

    if (this.alive) {
      if (this.closeToTurningPoint) {

        if (path.isEmpty) {
          Player.gethit()
          this.alive = false
          Game.updateStats()
        } else {
        speed = Vector2D(directions.head._1, directions.head._2)
        directions = directions.drop(1)    // drop the first from directions
        turningPoint = Vector2D((path.head._1), (path.head._2))
        path = path.drop(1)            // drop one from path
        }

      } else {
        location = location + speed
      }
    }
  }




  def closeToTurningPoint: Boolean = (this.location.x - turningPoint.x).abs < Settings.enemySpeed && (this.location.y - turningPoint.y).abs < Settings.enemySpeed




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
      g.translate(location.x, location.y)
      g.fill(circle)
      g.setTransform(oldTransform)
    }
  }

}
