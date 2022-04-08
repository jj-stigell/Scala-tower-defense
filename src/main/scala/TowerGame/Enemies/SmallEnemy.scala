package TowerGame.Enemies

import TowerGame.Config.Settings
import TowerGame.Helpers.Vector2D

import scala.collection.mutable.Buffer
import scala.swing.Color

/** Small enemy is fast but weak */
class SmallEnemy(enemyPath: Buffer[Vector2D], directionSet: Buffer[(Int, Int)]) extends Enemy(enemyPath, directionSet) {
  val speed = 7.0
  val enemyType = "small"
  val rewardFromDestroying = 10                                  // How much money player gets from destroying the enemy of this type
  val damageGivenPerHit = 1                                      // How much enemy damages the player if enemy reaches end of the map
  val enemyColor = new Color(34, 67, 0)
  var health = 1
  val enemySize = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)
  enemyDirections = directionSet.map(x => Vector2D(x._1 * this.speed, x._2 * this.speed))
}
