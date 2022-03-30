package TowerGame

object SaveLoad {

  var currentMap: Int = 1
  val maxMaps: Int = Settings.allMaps.length

  /**
   * Load map, new map from file, save game form file or next map from the default maps list in Settings.scala file.
   */
  def loadMap() = {
    if (currentMap != maxMaps) {
      Settings.setMap(Settings.allMaps(currentMap))
      Updater.resetWaves()
      Game.refreshMap()
      Area.updatePathAndDirs()
      currentMap += 1
    }
  }

  def saveGame() = ???

  def loadGame() = ???

}
