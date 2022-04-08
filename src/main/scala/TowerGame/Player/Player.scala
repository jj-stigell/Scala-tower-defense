package TowerGame.Player

import TowerGame.Enemies.Enemy
import TowerGame.{Game, Settings}

/** Object for controlling player money and health. */
object Player {

  var health: Int = Settings.maxHealth
  var alive: Boolean = true
  var money: Int = Settings.startingMoney

  /** REturn the current health of the player. */
  def getHealth: Int = this.health

  /** Check if the player is still alive. */
  def isAlive: Boolean = this.alive

  /** Check the amount of money the player has. */
  def moneyIntheBank: Int = this.money

  /** Add money to the player. */
  def addMoney(amount: Int): Unit = this.money = this.money + amount

  /** Remove money after buying towers. */
  def removeMoney(amount: Int): Unit = this.money = this.money - amount

  /** Resets player's all stats */
  def resetPlayer() = {
    this.health = Settings.maxHealth
    this.alive = true
    this.money = Settings.startingMoney
  }

  /** Once enemy reaches the end point it will give damage to the player, varies depending on the enemy type */
  def getHitByEnemy(enemy: Enemy): Unit = {
    this.health -= enemy.damageGivenPerHit
    if (this.health <= 0) {
      this.health = 0
      this.alive = false
      Game.gameOver = true
    }
  }

}
