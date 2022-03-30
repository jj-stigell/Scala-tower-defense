package TowerGame

import TowerGame.Enemies.Enemy
import TowerGame.Towers.Tower
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
    Game.mapWon = WaveController.currentWave == Settings.maxWaves && Game.roundOver
    Game.gameWon = WaveController.currentWave == Settings.maxWaves && SaveLoad.currentMap == SaveLoad.maxMaps && Game.roundOver
  }

  /**
   * Updates the buttons in the GUI depending on the current game situation.
   */
  def updateButtons() = {
    if (Game.gameOver) {
      Game.restartMap.visible = true
      Game.nextMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.menuButton.visible = false
      Game.buyTowerButton.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else if (Game.gameWon) {
      Game.restartMap.visible = false
      Game.nextMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.menuButton.visible = false
      Game.buyTowerButton.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else if (Game.mapWon) {
      Game.nextMap.visible = true
      Game.restartMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.menuButton.visible = false
      Game.buyTowerButton.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else {
      Game.restartMap.visible = false
      Game.saveGameButton.visible = true
      Game.loadGameButton.visible = true
      Game.menuButton.visible = true
      Game.buyTowerButton.visible = true
      Game.startButton.visible = true
      Game.healthPoints.visible = true
      Game.moneyInTheBank.visible = true
      Game.waveNumber.visible = true
      if (Player.moneyIntheBank >= Settings.towerPrice) Game.buyTowerButton.enabled = true else Game.buyTowerButton.enabled = false
      if (Game.roundOver) Game.startButton.enabled = true else Game.startButton.enabled = false
    }
  }

  /**
   * Reset the game area for the next wave.
   */
  def resetArea() = {
    Area.numberOfEnemies = WaveController.currentWave * Settings.numberOfEnemies
    Area.tick = 0
    Area.enemies = Buffer[Enemy]()
    if (Game.gameOver || Game.mapWon) Area.towers = Buffer[Tower]()
  }

  /**
   * Resets all waves, stats and area for a new game.
   */
  def resetWaves() = {
    Updater.resetArea()           // Reset area before setting gameOver to false
    Player.resetPlayer()
    Game.gameOver = false
    Game.mapWon = false
    Game.roundOver = true
    WaveController.currentWave = 0
    Updater.updateButtons()
    Updater.updateStats()
    Updater.updateConditions()
  }


  def updateMap() = {
    ???
  }

}
