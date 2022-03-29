package TowerGame

import TowerGame.Enemies.Enemy
import scala.collection.mutable.Buffer

object Updater {

  /**
   * Update the stats on the screen: health, money, wave.
   */
  def updateStats() = {
    Game.healthPoints.text = "Current Health: " + Player.getHealth + "/" + Settings.maxHealth
    Game.moneyInTheBank.text = "Money: " + Player.moneyIntheBank + "$"
    Game.waveNumber.text = "Current wave: " + WaveController.currentWave + "/" + Settings.maxWaves
  }

  /**
   * Update the game changing conditions.
   */
  def updateConditions() = {
    Game.gameOver = !Player.isAlive
    Game.roundOver = Player.isAlive && Area.enemies.forall(!_.isAlive)
    Game.gameWon = WaveController.currentWave == Settings.maxWaves && Game.roundOver
  }

  /**
   * Updates the buttons in the GUI.
   */
  def updateButtons() = {
    if (Player.moneyIntheBank >= Settings.towerPrice) Game.buyTowerButton.enabled = true else Game.buyTowerButton.enabled = false
    if (Game.roundOver) Game.startButton.enabled = true else Game.startButton.enabled = false
  }

  /**
   * Reset the game area for the next wave.
   */
  def resetArea() = {
    Area.numberOfEnemies = WaveController.currentWave * Settings.numberOfEnemies
    Area.tick = 0
    Area.enemies = Buffer[Enemy]()
  }

}
