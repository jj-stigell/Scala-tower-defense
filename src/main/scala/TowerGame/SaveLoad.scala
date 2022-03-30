package TowerGame

object SaveLoad {

  var currentMap: Int = 1
  val maxMaps: Int = Settings.allMaps.length

  def loadMap() = {

    if (currentMap != maxMaps) {
      Settings.setMap(Settings.allMaps(currentMap))
      Updater.resetWaves()
      Game.refreshMap()
      currentMap += 1
    }
  }


  def saveGame() = ???

  def loadGame() = ???

}
