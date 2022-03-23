package TowerGame

import java.awt.Graphics2D
import scala.collection.mutable.Buffer

object Area {
  
  // Store enemies here
  val enemies = Buffer[Enemy]()

  // Three initial example asteroids.
  enemies += Enemy(100, Vector2D(1,0),   Vector2D(100,100))
  //enemies += Enemy(130, Vector2D(-0.45,0.3), Vector2D(200,150))
  //enemies += Enemy(90,  Vector2D(0.1,0.7),   Vector2D(400,500))

  // When space steps one time unit forward, all asteroids move a step forward
  def step() = {
    enemies.foreach(_.move())
  }


  // Drawing all enemies
  def draw(g: Graphics2D) = enemies foreach (_.draw(g))
}
