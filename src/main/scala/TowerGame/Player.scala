package TowerGame

object Player {

  private var health: Int = Settings.maxHealth
  private var alive: Boolean = true
  private val damagePerHit: Int = Settings.hpLossPerEnemy
  private var money = 0


  def getHealth = this.health

  def isAlive: Boolean = this.alive

  def moneyIntheBank: Int = this.money


  def gethit(): Unit = {
    if (this.health - this.damagePerHit > 0) {
      this.health -= this.damagePerHit
    } else {
      this.alive = false
    }
  }





}
