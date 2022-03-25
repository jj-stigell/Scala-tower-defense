package TowerGame

import java.awt.Graphics2D
import scala.collection.mutable.Buffer

object Area {
  
  // Store enemies into a buffer
  val enemies = Buffer[Enemy]()

  // Initialize the starting location of enemies and the direction
  val initLoc = PathFinder.enemyInitialLocation()
  val initDir = PathFinder.findInitialDirection(initLoc)

  val pathAndDirections = (PathFinder.enemyPath(initLoc, initDir)).unzip

  val path = pathAndDirections._1
  val directions = pathAndDirections._2

  val correctedInitlDir = Vector2D(initDir._1 * Settings.enemySpeed, initDir._2 * Settings.enemySpeed)
  val correctedInitlLoc = Vector2D(Settings.blockLengthX * (initLoc._1 + (-1 * initDir._1)), Settings.blockLengthY* (initLoc._2 + (-1 * initDir._2)))


  // Vector multiplied with the speed scalar
  val correctedDirections: Buffer[(Double, Double)] = directions.map(_._1 * Settings.enemySpeed).zip(directions.map(_._2 * Settings.enemySpeed))
  val correctedPath: Buffer[(Double, Double)] = path.map(_._1 * Settings.blockLengthX.toDouble).zip(path.map(_._2 * Settings.blockLengthY.toDouble))


  println("corrected path: " + correctedPath)



  // speed, place
  enemies += Enemy(correctedInitlDir, correctedInitlLoc, correctedPath, correctedDirections)

  // When space steps one time unit forward, all enemies move a step forward
  def step() = enemies.foreach(_.move())

  // Drawing all enemies to the map
  def draw(g: Graphics2D) = enemies foreach (_.draw(g))
}
