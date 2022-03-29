package TowerGame

import TowerGame.Enemies.Enemy

object Player {

  var health: Int = Settings.maxHealth
  var alive: Boolean = true
  var money: Int = Settings.startingMoney

  def getHealth: Int = this.health

  def isAlive: Boolean = this.alive

  def moneyIntheBank: Int = this.money

  def addMoney(amount: Int): Unit = this.money = this.money + amount

  def removeMoney(amount: Int): Unit = this.money = this.money - amount

  def getHitByEnemy(enemy: Enemy): Unit = {
    this.health -= enemy.damageGivenPerHit
    if (this.health <= 0) {
      this.health = 0
      this.alive = false
      Game.gameOver = true
    }
  }

}
