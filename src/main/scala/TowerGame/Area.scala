package TowerGame

import TowerGame.Towers.{Tower, SmallTower}

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color
import scala.swing.event.MouseMoved

object Area {
  
  // Store enemies and towers into buffers
  var enemies: Buffer[Enemy] = Buffer[Enemy]()
  var towers: Buffer[Tower] = Buffer[Tower]()
  var numberOfEnemies: Int = Settings.numberOfEnemies
  var tick: Int = 0
  var towerLocation: Vector2D = Vector2D(0, 0)

  private val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  // Initialize the starting location of enemies and the direction and calculate path and directions on the map
  val initLoc: (Int, Int) = PathFinder.enemyInitialLocation()
  val initDir: (Int, Int) = PathFinder.findInitialDirection(initLoc)
  val path: Buffer[(Int, Int)] = (PathFinder.enemyPath(initLoc, initDir)).map(_._1)
  val directions: Buffer[(Int, Int)] = (PathFinder.enemyPath(initLoc, initDir)).map(_._2)

  // Correct values based on the gui dimensions and enemy speed
  val correctedInitlDir: Vector2D = Vector2D(initDir._1 * Settings.enemySpeed, initDir._2 * Settings.enemySpeed)
  val correctedInitlLoc: Vector2D = Vector2D(Settings.blockLengthX * (initLoc._1 + (-1 * initDir._1)), Settings.blockLengthY* (initLoc._2 + (-1 * initDir._2)))
  val correctedDirections: Buffer[Vector2D] = directions.map( x => Vector2D(x._1 * Settings.enemySpeed, x._2 * Settings.enemySpeed))
  val correctedPath: Buffer[Vector2D] = Buffer(correctedInitlLoc) ++ path.map( x => Vector2D(x._1 * Settings.blockLengthX.toDouble, x._2 * Settings.blockLengthY.toDouble))

  // Surrounding areas the enemy moves in
  val enemyPathSquares = PathFinder.findBannedAreas(correctedPath)

  // When space steps one time unit forward, all enemies move a step forward
  def step() = {
    if (numberOfEnemies > 0 && tick % Settings.correctedInterval == 0) {
      enemies += new Enemy(correctedPath, correctedDirections)
      numberOfEnemies -= 1
    }
    tick += 1
    enemies.foreach(_.move())
    //towers foreach (_.move())
  }

  // Drawing all enemies to the map
  def draw(g: Graphics2D) = {
    enemies.foreach(_.draw(g))
    towers.foreach(_.draw(g))
    if (Game.towerBuying) this.drawNewTower(g)
  }

  def resetEnemyBuffer() = enemies = Buffer[Enemy]()

  def checkBlocking() = {
    if (towers.exists(tower => (tower.getLocation.x - towerLocation.x).abs < towerSize && (tower.getLocation.y - towerLocation.y).abs < towerSize) ||
        enemyPathSquares.exists( spots => (spots._1._1 <= towerLocation.x && towerLocation.x <= spots._2._1 && spots._1._2 <= towerLocation.y && towerLocation.y <= spots._2._2))) {
      Game.blocked = true
    } else {
      Game.blocked = false
    }
  }

  def newTowerLocation(locationMouse: MouseMoved) = towerLocation = Vector2D(locationMouse.point.x - Settings.xCorrection, locationMouse.point.y - Settings.yCorrection)

  def drawNewTower(g: Graphics2D) = {

    checkBlocking()

    if (Game.blocked) {
      g.setColor(new Color(255, 0, 0))
    } else {
      g.setColor(new Color(178, 123, 255))
    }

    val circle = new Ellipse2D.Double(10, 10, towerSize, towerSize)
    val oldTransform = g.getTransform
    g.translate(towerLocation.x, towerLocation.y)
    g.fill(circle)
    g.setTransform(oldTransform)

  }

  def placeTower(x: Int, y: Int) = {
    towers += new SmallTower(Vector2D(x - Settings.xCorrection, y - Settings.yCorrection))
    Game.towerBuying = false
  }

}
