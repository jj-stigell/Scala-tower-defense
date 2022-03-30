package TowerGame

object SaveLoad {

  var currentMap: Int = 1
  val maxMaps: Int = Settings.allMaps.length

  def nextMap() = {

    if (currentMap != maxMaps) {
      Settings.setMap(Settings.allMaps(currentMap))
      currentMap += 1
      WaveController.resetWaves()
    }
  }


  def saveGame() = ???

  def loadGame() = ???

}
