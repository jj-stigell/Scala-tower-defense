package TowerGame

import TowerGame.Enemies.{Enemy, SmallEnemy}
import TowerGame.Helpers.{PathFinder, Updater, Vector2D}
import TowerGame.Towers.{SmallTower, Tower}
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
  var newTower: Tower = new SmallTower(Vector2D(0, 0))

  // Initialize the starting location of enemies and the direction and calculate path and directions on the map
  var initLoc: (Int, Int) = PathFinder.enemyInitialLocation()
  var initDir: (Int, Int) = PathFinder.findInitialDirection(initLoc)
  var path: Buffer[(Int, Int)] = (PathFinder.enemyPath(initLoc, initDir)).map(_._1)
  var directions: Buffer[(Int, Int)] = (PathFinder.enemyPath(initLoc, initDir)).map(_._2)

  // -1 moves the enemy outside the current map, so it looks like its coming from outside of the map when wave starts
  var correctedInitlLoc: Vector2D = Vector2D(Settings.blockLengthX * (this.initLoc._1 + (-1 * this.initDir._1)), Settings.blockLengthY* (this.initLoc._2 + (-1 * this.initDir._2)))
  var correctedPath: Buffer[Vector2D] = Buffer(this.correctedInitlLoc) ++ this.path.map( x => Vector2D(x._1 * Settings.blockLengthX.toDouble, x._2 * Settings.blockLengthY.toDouble))

  // Path the enemy moves that is banned from adding towers
  var towerBannedPath = PathFinder.findBannedAreas(correctedPath)

  /** When space steps one time unit forward, all enemies move a step forward */
  def step() = {
    if (numberOfEnemies > 0 && tick % Settings.correctedInterval == 0) {
      this.enemies += new SmallEnemy(correctedPath, directions)
      this.numberOfEnemies -= 1
    }
    this.tick += 1
    this.enemies.foreach(_.move())
    this.towers.foreach(_.scanProximity(this.enemies))
  }

  /** Drawing all enemies to the map */
  def draw(g: Graphics2D) = {
    enemies.foreach(_.draw(g))
    towers.foreach(_.draw(g))
    if (Game.towerBuying) this.drawNewTower(g)
  }

  /** Check for new tower that it is not blocking with the enemy path or towers previously placed on the map */
  def checkBlocking() = {
    if (towers.exists(tower => (tower.getLocation.x - towerLocation.x).abs < newTower.towerSize && (tower.getLocation.y - towerLocation.y).abs < newTower.towerSize) ||
        towerBannedPath.exists(spots => (spots._1._1 <= towerLocation.x && towerLocation.x <= spots._2._1 && spots._1._2 <= towerLocation.y && towerLocation.y <= spots._2._2))) {
      Game.blocked = true
    } else {
      Game.blocked = false
    }
  }

  /** Update the location of the tower that is being placed on the map */
  def newTowerLocation(locationMouse: MouseMoved) = towerLocation = Vector2D(locationMouse.point.x - Settings.xCorrection, locationMouse.point.y - Settings.yCorrection)

  /** Draw the new tower that is being placed */
  def drawNewTower(g: Graphics2D) = {
    checkBlocking()
    if (Game.blocked) g.setColor(new Color(255, 0, 0))
    else g.setColor(new Color(0, 255, 0))

    val circle = new Ellipse2D.Double(10, 10, newTower.towerSize, newTower.towerSize)
    val oldTransform = g.getTransform
    g.translate(towerLocation.x, towerLocation.y)
    g.fill(circle)
    g.setTransform(oldTransform)
  }

  /** Place the new tower to the map */
  def placeTower(x: Int, y: Int) = {
    newTower.changeLocation(Vector2D(x - Settings.xCorrection, y - Settings.yCorrection))
    towers += newTower
    Game.towerBuying = false
    Player.removeMoney(newTower.price)
    Updater.updateStats()
    Updater.updateButtons()
  }

  /** Update the enemy path and directions after loading new map */
  def updateAreaPathAndDirs() = {
    this.initLoc = PathFinder.enemyInitialLocation()
    this.initDir = PathFinder.findInitialDirection(this.initLoc)
    this.path = (PathFinder.enemyPath(this.initLoc, this.initDir)).map(_._1)
    this.directions = (PathFinder.enemyPath(this.initLoc, this.initDir)).map(_._2)
    this.correctedInitlLoc = Vector2D(Settings.blockLengthX * (this.initLoc._1 + (-1 * this.initDir._1)), Settings.blockLengthY* (this.initLoc._2 + (-1 * this.initDir._2)))
    this.correctedPath = Buffer(this.correctedInitlLoc) ++ this.path.map( x => Vector2D(x._1 * Settings.blockLengthX.toDouble, x._2 * Settings.blockLengthY.toDouble))
    this.towerBannedPath = PathFinder.findBannedAreas(correctedPath)
  }

}
