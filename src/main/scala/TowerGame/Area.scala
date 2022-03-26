package TowerGame

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color
import scala.swing.event.MouseMoved

object Area {
  
  // Store enemies into a buffer
  var enemies = Buffer[Enemy]()
  var towers = Buffer[Tower]()
  var numberOfEnemies = Settings.numberOfEnemies
  var tick = 0
  var towerLocation: Vector2D = Vector2D(0, 0)

  private val towerSize: Int = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)

  // Initialize the starting location of enemies and the direction and calculate path and directions on the map
  val initLoc = PathFinder.enemyInitialLocation()
  val initDir = PathFinder.findInitialDirection(initLoc)
  val path = (PathFinder.enemyPath(initLoc, initDir)).map(_._1)
  val directions = (PathFinder.enemyPath(initLoc, initDir)).map(_._2)

  // Correct values based on the gui dimensions
  val correctedInitlDir = Vector2D(initDir._1 * Settings.enemySpeed, initDir._2 * Settings.enemySpeed)
  val correctedInitlLoc = Vector2D(Settings.blockLengthX * (initLoc._1 + (-1 * initDir._1)), Settings.blockLengthY* (initLoc._2 + (-1 * initDir._2)))
  val correctedDirections = directions.map( x => Vector2D(x._1 * Settings.enemySpeed, x._2 * Settings.enemySpeed))
  val correctedPath = path.map( x => Vector2D(x._1 * Settings.blockLengthX.toDouble, x._2 * Settings.blockLengthY.toDouble))


  // When space steps one time unit forward, all enemies move a step forward
  def step() = {
    if (numberOfEnemies > 0 && tick % Settings.correctedInterval == 0) {
      enemies += new Enemy(Buffer(correctedInitlLoc) ++ correctedPath, correctedDirections)
      numberOfEnemies -= 1
    }
    tick += 1
    enemies.foreach(_.move())
    //towers foreach (_.move())
  }


  // Drawing all enemies to the map
  def draw(g: Graphics2D) = {
    enemies foreach (_.draw(g))
    towers foreach (_.draw(g))

    if (Game.towerBuying) this.drawNewTower(g)

  }


  def resetEnemyBuffer() = enemies = Buffer[Enemy]()


  def checkBlocking() = {
    if (towers.exists(tower => (tower.place.x - towerLocation.x).abs < towerSize && (tower.place.y - towerLocation.y).abs < towerSize)) Game.blocked = true else Game.blocked = false

    println(correctedInitlLoc)
    println(correctedPath)
  }


  def newTowerLocation(locationMouse: MouseMoved) = towerLocation = Vector2D(locationMouse.point.x - Settings.xCorrection, locationMouse.point.y - Settings.yCorrection)


    /*
    if (towers.nonEmpty) println("tower at buffer: (" + towers.head.place.x + "," + towers.head.place.y + ")")
    println("mouse at: (" + towerLocation.x + "," + towerLocation.y + ")     Blocking = " + Game.blocked)
    println()
    */


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
    towers += new Tower(Vector2D(x - Settings.xCorrection, y - Settings.yCorrection))
    println("tower placed to x: " + x + " y: " + y)
    Game.towerBuying = false
  }





}
