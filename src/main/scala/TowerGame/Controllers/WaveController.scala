package TowerGame.Controllers

import TowerGame.Config.Settings
import TowerGame.Helpers.Updater
import TowerGame.Game

/** Object for controlling the individual waves on the game area. */
object WaveController {

  var maxWaves = Settings.maxWaves
  var currentWave = 0

  /** Launch new wave if there is still waves left. */
  def launchNewWave() = {
    if (currentWave <= maxWaves) {
      Game.roundOver = false
      currentWave += 1
      Updater.resetArea()
      Updater.updateStats()
      Updater.updateButtons()
    }
  }

}
