package TowerGame

import java.awt.Graphics2D
import scala.collection.mutable.Buffer

object Area {
  
  // Store enemies into a buffer
  val enemies = Buffer[Enemy]()

  // Initialize the starting location of enemies and the direction
  val enemyInitialLocation = DirLoc.enemyInitialLocation()
  val enemyInitialDirection = DirLoc.enemyInitialDirection(enemyInitialLocation)

  // speed, place
  enemies += Enemy(
    Vector2D(enemyInitialDirection._1, enemyInitialDirection._2),
    Vector2D(enemyInitialLocation._1 * Settings.blockLengthX, enemyInitialLocation._2 * Settings.blockLengthX))

  // When space steps one time unit forward, all asteroids move a step forward
  def step() = {
    enemies.foreach(_.move())
  }

  // Drawing all enemies to the map
  def draw(g: Graphics2D) = enemies foreach (_.draw(g))
}
