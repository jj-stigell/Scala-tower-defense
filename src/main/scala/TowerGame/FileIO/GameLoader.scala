package TowerGame.FileIO

import TowerGame.Enemies.Enemy
import TowerGame.FileIO.Reader.{readEnemies, readMap, readWaves}
import TowerGame.Helpers.Updater
import TowerGame.{Area, Game, Settings, WaveController}
import java.io.{BufferedReader, FileNotFoundException, FileReader, IOException}
import javax.swing.{JFileChooser, JFrame}
import javax.swing.filechooser.FileNameExtensionFilter
import scala.collection.mutable.Buffer

object GameLoader {

  var currentMap: Int = 1
  var maxMaps: Int = Settings.allMaps.length

  // For loading a new map
  var loadedMap: Array[Array[Int]] = Array(Array(0,0))
  var loadedEnemies: Buffer[Enemy] = Buffer[Enemy]()
  var loadedCurrentWave: Int = 0
  var loadedMaxWaves: Int = 0

  /**
   * Load map, new map from file, save game form file or next map from the default maps list in Settings.scala file.
   */
  def loadMap(fromFile: Boolean = false) = {

    if (fromFile) {
      Settings.setMap(this.loadedMap)
      Updater.resetWaves()
      WaveController.currentWave = this.loadedCurrentWave
      WaveController.maxWaves = this.loadedMaxWaves
      Settings.maxWaves = this.loadedMaxWaves
      Settings.numberOfEnemies = this.loadedEnemies.length
      Game.refreshMap()
      Area.updatePathAndDirs()
      this.currentMap = this.maxMaps
    } else {
      if (currentMap != maxMaps) {
        Settings.setMap(Settings.allMaps(currentMap))
        Updater.resetWaves()
        Game.refreshMap()
        Area.updatePathAndDirs()
        currentMap += 1
      }
    }
  }

  def loadGame() = {

    var mapRead = false
    var enemiesRead = false
    var wavesRead = false

    val fileChooser = new JFileChooser
    fileChooser.setDialogTitle("Choose a sav-file with the map and settings")

    val filter = new FileNameExtensionFilter("sav file", "sav")
    fileChooser.addChoosableFileFilter(filter)
    fileChooser.setFileFilter(filter)

    val frame = new JFrame("Loader")
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
