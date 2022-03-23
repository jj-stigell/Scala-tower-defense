package TowerGame

import java.awt.Graphics2D
import scala.collection.mutable.Buffer

object Area {
  
  // Store enemies here
  val enemies = Buffer[Enemy]()

  // Three initial example asteroids.
  val enemyInitialLocation = DirLoc.enemyInitialLocation()

  val enemyInitialDirection = DirLoc.enemyInitialDirection(enemyInitialLocation)


  println("enemy loc: " + enemyInitialLocation)
  println("enemy dir: " + enemyInitialDirection)

  // speed, place
  enemies += Enemy(Vector2D(enemyInitialDirection._1, enemyInitialDirection._2), Vector2D(enemyInitialLocation._1 * Settings.blockLengthX, enemyInitialLocation._2 * Settings.blockLengthX))
  //enemies += Enemy(Vector2D(-0.45,0.3), Vector2D(200,150))
  //enemies += Enemy(Vector2D(0.1,0.7),   Vector2D(400,500))

  // When space steps one time unit forward, all asteroids move a step forward
  def step() = {
    enemies.foreach(_.move())
  }


  // Drawing all enemies
  def draw(g: Graphics2D) = enemies foreach (_.draw(g))
}
