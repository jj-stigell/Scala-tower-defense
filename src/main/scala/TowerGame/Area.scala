package TowerGame

import TowerGame.Enemies.{BigEnemy, Enemy, SmallEnemy}
import TowerGame.Helpers.{PathFinder, Updater, Vector2D}
import TowerGame.Towers.{SmallTower, Tower}

import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import scala.collection.mutable.Buffer
import scala.swing.Color
import scala.swing.event.MouseMoved

object Area {

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

  // Form a buffer with enemies, initialize with small enemies matching the multiplier number and few big enemies
  var enemies: Buffer[Enemy] = {
    var enemyBuf = Buffer[Enemy]()
    for (i <- 1 to Settings.enemyMultiplier) enemyBuf += new SmallEnemy(correctedPath, directions)
    enemyBuf += new BigEnemy(correctedPath, directions)
    enemyBuf += new BigEnemy(correctedPath, directions)
    enemyBuf
  }

  // Set the first enemy active
  enemies.head.launchEnemy()

  var towers: Buffer[Tower] = Buffer[Tower]()
  //var numberOfEnemies: Int = Settings.enemyMultiplier
  var tick: Int = 0
  var towerLocation: Vector2D = Vector2D(0, 0)
  var newTower: Tower = new SmallTower(Vector2D(0, 0))

  /** When space steps one time unit forward, all enemies move a step forward */
  def step() = {

    enemies.foreach(enemy => if (enemy.isAlive) println("x on: " + enemy.getLocation.x + " y on: " + enemy.getLocation.y))

    if (tick % Settings.correctedInterval == 0) {
      val nonActive = this.enemies.filter(_.launched == false)
      if (nonActive.nonEmpty) nonActive.head.launchEnemy()
    }
    this.tick += 1
    this.enemies
      .filter(_.launched == true)
      .foreach(_.move())
    this.towers.foreach(_.scanProximity(this.enemies))
  }

  /** Drawing all enemies to the map */
  def draw(g: Graphics2D) = {
    this.enemies
      .filter(_.launched == true)
      .foreach(_.draw(g))
    this.towers.foreach(_.draw(g))
    if (Game.towerBuying) this.drawNewTower(g)
  }

  /** Check for new tower that it is not blocking with the enemy path or towers previously placed on the map */
  def checkBlocking() = {
    if (this.towers.exists(tower => (tower.getLocation.x - this.towerLocation.x).abs < newTower.towerSize && (tower.getLocation.y - this.towerLocation.y).abs < this.newTower.towerSize) ||
        this.towerBannedPath.exists(spots => (spots._1._1 <= this.towerLocation.x && this.towerLocation.x <= spots._2._1 && spots._1._2 <= this.towerLocation.y && this.towerLocation.y <= spots._2._2))) {
      Game.blocked = true
    } else {
      Game.blocked = false
    }
  }

  /** Update the location of the tower that is being placed on the map */
  def newTowerLocation(locationMouse: MouseMoved) = this.towerLocation = Vector2D(locationMouse.point.x - Settings.xCorrection, locationMouse.point.y - Settings.yCorrection)

  /** Draw the new tower that is being placed */
  def drawNewTower(g: Graphics2D) = {
    this.checkBlocking()
    if (Game.blocked) g.setColor(new Color(255, 0, 0))
    else g.setColor(new Color(0, 255, 0))

    val circle = new Ellipse2D.Double(10, 10, newTower.towerSize, newTower.towerSize)
    val oldTransform = g.getTransform
    g.translate(this.towerLocation.x, this.towerLocation.y)
    g.fill(circle)
    g.setTransform(oldTransform)
  }

  /** Place the new tower to the map */
  def placeTower(x: Int, y: Int) = {
    this.newTower.changeLocation(Vector2D(x - Settings.xCorrection, y - Settings.yCorrection))
    this.towers += this.newTower
    Game.towerBuying = false
    Player.removeMoney(this.newTower.price)
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
