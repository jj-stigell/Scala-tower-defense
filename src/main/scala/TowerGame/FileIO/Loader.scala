package TowerGame.FileIO

import TowerGame.Enemies.Enemy
import TowerGame.FileIO.Reader._
import TowerGame.Helpers.{Updater, Vector2D}
import TowerGame.Towers.Tower
import TowerGame.{Area, Game, Player, Settings, WaveController}

import java.io.{BufferedReader, FileNotFoundException, FileReader, IOException}
import javax.swing.{JFileChooser, JFrame}
import javax.swing.filechooser.FileNameExtensionFilter
import scala.collection.mutable.Buffer

object Loader {

  var currentMap: Int = 1
  var maxMaps: Int = Settings.allMaps.length

  // For loading a new map
  var loadedMap: Array[Array[Int]] = Array(Array(0,0))
  var loadedEnemies: Buffer[Enemy] = Buffer[Enemy]()
  var loadedCurrentWave: Int = 0
  var loadedMaxWaves: Int = 0
  var loadedMaxHealth: Int = 0
  var loadedCurrentHealth: Int = 0
  var loadedMoney: Int = 0
  var loadedTowers: Buffer[Tower] = Buffer[Tower]()
  var loadedTowerLocations: Array[Vector2D] = Array[Vector2D]()

  // Load map, new map from file, save game form file or next map from the default maps list in Settings.scala file.
  def loadMap(fromFile: Boolean = false) = {

    if (fromFile) {

      // Set new map, max health and starting money
      Settings.setMap(this.loadedMap)
      Settings.maxHealth = loadedMaxHealth
      Settings.startingMoney = loadedMoney
      Settings.maxWaves = this.loadedMaxWaves
      Settings.numberOfEnemies = this.loadedEnemies.length

      // Reset waves and area, resetArea(true) for cleaning the towers
      Updater.resetWaves()
      Updater.resetArea(true)

      // After resetting the player set current health to one loaded ffrom file
      Player.health = loadedCurrentHealth

      // Set the correct waves
      WaveController.currentWave = this.loadedCurrentWave
      WaveController.maxWaves = this.loadedMaxWaves

      // Update stats, conditions and buttons
      Updater.updateStats()
      Updater.updateConditions()
      Updater.updateButtons()

      // Add towers if any was loaded
      if (loadedTowers.nonEmpty) {
        var i = 0
        for (tower <- loadedTowers) { tower.changeLocation(loadedTowerLocations(i)); i += 1 }
        Area.towers = this.loadedTowers
      }

      // Start drawing the new map
      Game.refreshMap()
      // Update the enemy paths and directions to match the new map
      Area.updateAreaPathAndDirs()
      // Set current map to max maps so game ends if player finishes the loaded game.
      this.currentMap = this.maxMaps
    } else {
      if (currentMap != maxMaps) {
        Settings.setMap(Settings.allMaps(currentMap))
        Updater.resetWaves()
        Game.refreshMap()
        Area.updateAreaPathAndDirs()
        currentMap += 1
      }
    }
  }

  // Load game from sav-file.
  def loadGame() = {

    var mapRead = false
    var enemiesRead = false
    var wavesRead = false
    var healthRead = false
    var moneyRead = false
    var towersRead = false
    var locationsRead = false

    val fileChooser = new JFileChooser
    fileChooser.setDialogTitle("Choose a sav-file with the map and settings")

    val filter = new FileNameExtensionFilter("sav file", "sav")
    fileChooser.addChoosableFileFilter(filter)
    fileChooser.setFileFilter(filter)

    val frame = new JFrame("Load")
    val response = fileChooser.showSaveDialog(frame)

    if (response == JFileChooser.APPROVE_OPTION) {

      try {
        val selectedFile = fileChooser.getSelectedFile
        val fileIn = new FileReader(selectedFile.getAbsolutePath)
        val linesIn = new BufferedReader(fileIn)

        try {
          var currentLine = linesIn.readLine()

          while (currentLine != null) {
            currentLine = currentLine.trim.toLowerCase

            currentLine = currentLine match {
              case "#" => linesIn.readLine()
              case "#map" =>
                mapRead = true
                readMap(linesIn)
              case "#enemy" =>
                enemiesRead = true
                readEnemies(linesIn)
              case "#waves" =>
                wavesRead = true
                readWaves(linesIn)
              case "#health" =>
                healthRead = true
                readHealth(linesIn)
              case "#money" =>
                moneyRead = true
                readMoney(linesIn)
              case "#tower" =>
                towersRead = true
                readTowers(linesIn)
              case "#towerlocation" =>
                locationsRead = true
                readLocations(linesIn)
              case _ => linesIn.readLine()
            }
          }
          // Load the new map with enemies
          this.loadMap(true)

        } finally {
          // Close open streams
          // This will be executed if the file has been opened
          // regardless of whether or not there were any exceptions.
          fileIn.close()
          linesIn.close()
        }
      } catch {
        case notFound: FileNotFoundException =>
        // Response here to a failed file opening.
        case e: IOException =>
        // Response here to unsuccessful reading
      }
    }
  }

}
