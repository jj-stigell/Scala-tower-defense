package TowerGame

import TowerGame.Helpers.Updater

object WaveController {

  val maxWaves = Settings.maxWaves
  var currentWave = 0

  /**
   * Launch new wave if there is still waves left
   */
  def launchNewWave() = {
    if (currentWave <= maxWaves) {
      currentWave += 1
      Updater.resetArea()
      Updater.updateStats()
      Game.startButton.enabled = false
      Game.roundOver = false
    }
  }

}
