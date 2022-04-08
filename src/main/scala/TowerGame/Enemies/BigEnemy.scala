package TowerGame.Enemies

import TowerGame.Config.Settings
import TowerGame.Helpers.Vector2D

import scala.collection.mutable.Buffer
import scala.swing.Color

/** Big enemy is slow but strong */
class BigEnemy(enemyPath: Buffer[Vector2D], directionSet: Buffer[(Int, Int)]) extends Enemy(enemyPath, directionSet) {
  val speed = 2.0
  val enemyType = "big"
  val rewardFromDestroying = 100                                  // How much money player gets from destroying the enemy of this type
  val damageGivenPerHit = 10                                      // How much enemy damages the player if enemy reaches end of the map
  val enemyColor = new Color(255, 0, 0)
  var health = 25
  val enemySize = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 2)
  enemyDirections = directionSet.map(x => Vector2D(x._1 * this.speed, x._2 * this.speed))
}
