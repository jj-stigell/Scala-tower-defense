package TowerGame

import TowerGame.Helpers.Updater

/** Object for controlling the individual waves on the game area */
object WaveController {

  var maxWaves = Settings.maxWaves
  var currentWave = 0

  /** Launch new wave if there is still waves left */
  def launchNewWave() = {
    if (currentWave <= maxWaves) {
      currentWave += 1
      Updater.resetArea()
      Updater.updateStats()
      Game.roundOver = false
      Updater.updateButtons()
    }
  }

}
