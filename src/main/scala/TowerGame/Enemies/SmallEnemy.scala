package TowerGame.Enemies

import TowerGame.Helpers.Vector2D
import TowerGame.Settings
import scala.collection.mutable.Buffer
import scala.swing.Color

/** Small enemy is fast but weak */
class SmallEnemy(enemyPath: Buffer[Vector2D], directionSet: Buffer[(Int, Int)]) extends Enemy(enemyPath, directionSet) {
  speed = 7.0
  enemyType = "small"
  rewardFromDestroying = 10                                  // How much money player gets from destroying the enemy of this type
  damageGivenPerHit = 1                                      // How much enemy damages the player if enemy reaches end of the map
  enemyColor = new Color(34, 67, 0)
  health = 1
  enemySize = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)
  enemyDirections = directionSet.map(x => Vector2D(x._1 * this.speed, x._2 * this.speed))
}
