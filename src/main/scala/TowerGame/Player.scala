package TowerGame

object Player {

  private var health: Int = Settings.maxHealth
  private var alive: Boolean = true
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var money: Int = Settings.startingMoney

  def getHealth: Int = this.health

  def isAlive: Boolean = this.alive

  def moneyIntheBank: Int = this.money

  def addMoney(amount: Int): Unit = this.money = this.money + amount

  def gethit(): Unit = {
    if (this.health > 0) {
      this.health -= this.damagePerHit
    } else {
      this.alive = false
      Game.gameOver = true
    }
  }

}
