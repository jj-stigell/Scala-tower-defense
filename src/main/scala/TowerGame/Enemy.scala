package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color

/**
 * Enemy is a ball that moves on the predetermined path and tries to reach the players tower.
 *
 * @param initDir         Initial direction the enemy moves to
 * @param initLoc         Initial location from where the enemy starts
 * @param enemyPath       Full path the enemy moves
 * @param enemyDirections All the directions enemy will move on the map
 */
class Enemy(initDir: Vector2D, var initLoc: Vector2D, enemyPath: Buffer[(Double, Double)], enemyDirections: Buffer[(Double, Double)]) {

  private var location: Vector2D = initLoc
  private var speed: Vector2D = initDir
  private var alive: Boolean = true
  private var playerHit: Boolean = false
  private var health: Int = Settings.enemyHealth
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var path = enemyPath.drop(1)                                                          // drop first, because first is set as the first turning point
  private var directions = enemyDirections.drop(1)                                              // drop first, already as the initial direction
  private var turningPoint: Vector2D = Vector2D((enemyPath.head._1), (enemyPath.head._2))       // start with the first turning point
  private val enemySize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  /**
   *
   * @return Boolean on the state of the enemy, dead or alive
   */
  def isAlive: Boolean = this.alive

  /**
   * Player can attack the enemy when close by, kills the enemy if health goes zero.
   */
  def gethit(): Unit = {
    if (this.health - this.damagePerHit > 0) {
      this.health -= this.damagePerHit
    } else {
      this.alive = false
    }
  }

  /**
   * Move the enemy if alive to the direction of current movement.
   * If enemy reaches player tower, reduce player health and update stats.
   */
  def move() = {

    if (isAlive) {
      if (this.closeToTurningPoint) {

        if (path.isEmpty && !playerHit) {
          Player.gethit()
          this.playerHit = true
          this.alive = false
          Updater.updateStats()
          Updater.updateConditions()
        } else {
        speed = Vector2D(directions.head._1, directions.head._2)
        directions = directions.drop(1)
        turningPoint = Vector2D((path.head._1), (path.head._2))
        path = path.drop(1)
        }

      } else {
        location = location + speed
      }
    }
  }

  /**
   * Check if the enemy is close to the turning point. Compare enemy location and turningpoint location.
   * Takes into account the enemy speed and difference it creates to the compared values.
   *
   * @return Boolean true if the enemy is close to turning point, otherwise false
   */
  def closeToTurningPoint: Boolean = (this.location.x - turningPoint.x).abs < Settings.enemySpeed && (this.location.y - turningPoint.y).abs < Settings.enemySpeed

  /**
   * Draw the enemy on the map.
   *
   * @param g Graphics2D
   */
  def draw(g: Graphics2D) = {
    if (isAlive) {
      val circle = new Ellipse2D.Double(10, 10, enemySize, enemySize)
      g.setColor(new Color(255, 0, 0))
      val oldTransform = g.getTransform
      g.translate(location.x, location.y)
      g.fill(circle)
      g.setTransform(oldTransform)
    }
  }

}
