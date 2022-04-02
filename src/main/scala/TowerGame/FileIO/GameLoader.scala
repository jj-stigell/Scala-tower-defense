package TowerGame.FileIO

import TowerGame.Enemies.Enemy
import TowerGame.FileIO.Reader.{readEnemies, readMap, readWaves}
import TowerGame.{Area, Game, Settings, Updater}
import java.io.{BufferedReader, FileNotFoundException, FileReader, IOException}
import javax.swing.{JFileChooser, JFrame}
import javax.swing.filechooser.FileNameExtensionFilter
import scala.collection.mutable.Buffer

object GameLoader {

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

  def loadGame() = {

    var map: Array[Array[Int]] = Array(Array(0,0))
    var enemies: Buffer[Enemy] = Buffer[Enemy]()
    var currentWave: Int = 0
    var maxWaves: Int = 0

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
              case "map" =>
                mapRead = true; readMap(linesIn)
              case "enemy" =>
                enemiesRead = true; readEnemies(linesIn)
              case "waves" =>
                wavesRead = true; readWaves(linesIn)
              case _ => linesIn.readLine()
            }
          }

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
