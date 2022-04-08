package TowerGame.Enemies

import TowerGame.Helpers.Vector2D
import TowerGame.Settings
import scala.collection.mutable.Buffer
import scala.swing.Color

/** Big enemy is slow but strong */
class BigEnemy(enemyPath: Buffer[Vector2D], directionSet: Buffer[(Int, Int)]) extends Enemy(enemyPath, directionSet) {
  speed = 2.0
  enemyType = "big"
  rewardFromDestroying = 100                                  // How much money player gets from destroying the enemy of this type
  damageGivenPerHit = 10                                      // How much enemy damages the player if enemy reaches end of the map
  enemyColor = new Color(255, 0, 0)
  health = 10
  enemySize = (((Settings.width / Settings.totalHorizontalBlocks) + (Settings.height / Settings.totalVerticalBlocks)) / 3)
  enemyDirections = directionSet.map(x => Vector2D(x._1 * this.speed, x._2 * this.speed))
}
