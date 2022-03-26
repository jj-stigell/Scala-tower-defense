package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color

/**
 * Enemy is a ball that moves on the predetermined path and tries to reach the players tower.
 *
 * @param enemyPath       Full path the enemy moves
 * @param enemyDirections All the directions enemy will move on the map
 */
class Enemy(enemyPath: Buffer[Vector2D], enemyDirections: Buffer[Vector2D]) {

  private var location: Vector2D = enemyPath.head                                               //initLoc
  private var speed: Vector2D = enemyDirections.head                                            // Initial direction (head of directions) set as the speed
  private var alive: Boolean = true
  private var playerHit: Boolean = false
  private var health: Int = Settings.enemyHealth
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var turningPoint: Vector2D = enemyPath(1)                                             // Start with the first turning point
  private var path = enemyPath.drop(2)                                                          // Drop first 2, because first is set as the first location and second one as the first turning point
  private var directions = enemyDirections.drop(1)                                              // Drop first, already as the initial direction
  private val enemySize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  /**
   *
   * @return Boolean on the state of the enemy, dead or alive
   */
  def isAlive: Boolean = this.alive

  /**
   * For tower to check the location of the enemy.
   *
   * @return Location Vector2D
   */
  def getLocation: Vector2D = this.location

  /**
   * Player can attack the enemy when close by, kills the enemy if health goes zero.
   */
  def gethit(): Unit = {
    if (this.health - this.damagePerHit > 0) {
      this.health -= this.damagePerHit
    } else {
      this.alive = false
      Player.addMoney(Settings.rewardFromKill)
      Updater.updateStats()
      Updater.updateButtons()
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
        speed = directions.head
        directions = directions.drop(1)
        turningPoint = path.head
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
