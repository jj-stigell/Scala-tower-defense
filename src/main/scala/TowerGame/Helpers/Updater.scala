package TowerGame.Helpers

import TowerGame.Area.{correctedPath, directions}
import TowerGame.Enemies.{BigEnemy, Enemy, SmallEnemy}
import TowerGame.FileIO.Loader
import TowerGame.Towers.Tower
import TowerGame.{Player, _}

import scala.collection.mutable.Buffer

/** Helping funtions for updating/resetting buttons, game condition, maps and routes */
object Updater {

  /** Update the stats on the screen: health, money, wave. */
  def updateStats() = {
    Game.healthPoints.text = s"Current Health: ${Player.Player.getHealth}/${Settings.maxHealth}"
    Game.moneyInTheBank.text = s"Money: ${Player.Player.moneyIntheBank}€"
    Game.waveNumber.text = s"Current wave: ${WaveController.currentWave}/${Settings.maxWaves}"
  }

  /** Update the game changing conditions. */
  def updateConditions() = {
    Game.gameOver = !Player.Player.isAlive
    Game.roundOver = Player.Player.isAlive && Area.enemies.forall(!_.isAlive)
    Game.mapWon = WaveController.currentWave == Settings.maxWaves && Game.roundOver
    Game.gameWon = WaveController.currentWave == Settings.maxWaves && Loader.currentMap == Loader.maxMaps && Game.roundOver
  }

  /** Updates the buttons in the GUI depending on the current game situation. */
  def updateButtons() = {
    if (Game.gameOver) {
      Game.restartMap.visible = true
      Game.nextMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.buySmallTower.visible = false
      Game.buyBigTower.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else if (Game.gameWon) {
      Game.restartMap.visible = false
      Game.nextMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.buySmallTower.visible = false
      Game.buyBigTower.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else if (Game.mapWon) {
      Game.nextMap.visible = true
      Game.restartMap.visible = false
      Game.saveGameButton.visible = false
      Game.loadGameButton.visible = false
      Game.buySmallTower.visible = false
      Game.buyBigTower.visible = false
      Game.startButton.visible = false
      Game.healthPoints.visible = false
      Game.moneyInTheBank.visible = false
      Game.waveNumber.visible = false
    } else {
      Game.restartMap.visible = false
      Game.saveGameButton.visible = true
      Game.loadGameButton.visible = true
      Game.buySmallTower.visible = true
      Game.buyBigTower.visible = true
      Game.startButton.visible = true
      Game.healthPoints.visible = true
      Game.moneyInTheBank.visible = true
      Game.waveNumber.visible = true
      if (Player.Player.moneyIntheBank >= Settings.smallTowerPrice) Game.buySmallTower.enabled = true else Game.buySmallTower.enabled = false
      if (Player.Player.moneyIntheBank >= Settings.bigTowerPrice) Game.buyBigTower.enabled = true else Game.buyBigTower.enabled = false
      if (Game.roundOver) {
        Game.startButton.enabled = true
        Game.saveGameButton.enabled = true
        Game.loadGameButton.enabled = true
      } else {
        Game.startButton.enabled = false
        Game.saveGameButton.enabled = false
        Game.loadGameButton.enabled = false
      }
    }
  }

  /** Reset the game area for the next wave. */
  def resetArea(loadingFromFile: Boolean = false, enemies: Buffer[Enemy] = Buffer[Enemy]()) = {

    // Previous count of enemies is multiplied with the new wave number
    Area.enemies = {
      var enemyBuf = Buffer[Enemy]()
      if (loadingFromFile) enemyBuf = enemies
      for (i <- 1 to WaveController.currentWave * Area.enemies.count(_.enemyType == "small")) enemyBuf += new SmallEnemy(correctedPath, directions)
      for (i <- 1 to WaveController.currentWave * Area.enemies.count(_.enemyType == "big")) enemyBuf += new BigEnemy(correctedPath, directions)
      enemyBuf.foreach(_.reCalcDirections())
      enemyBuf
    }

    Area.tick = 0
    if (Game.gameOver || Game.mapWon || loadingFromFile) Area.towers = Buffer[Tower]() // Reset the towers only if game over, resetting or ready for the nex map
  }

  /** Resets all waves, stats and area for a new game. */
  def resetWaves() = {
    this.resetArea() // Reset area before setting gameOver to false
    Player.Player.resetPlayer()
    Game.gameOver = false
    Game.mapWon = false
    Game.roundOver = true
    WaveController.currentWave = 0
    this.updateButtons()
    this.updateStats()
    this.updateConditions()
  }

}
