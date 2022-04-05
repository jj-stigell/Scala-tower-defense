package TowerGame.FileIO

import TowerGame.{Area, Settings, WaveController}
import java.io.{File, PrintWriter}
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JFileChooser, JFrame}

/** Save game state (map, enemies, waves and towers) to a sav-file */
object Saver {

    def saveGame() = {
        val maxWaves = WaveController.maxWaves
        val enemies = Area.numberOfEnemies
        val currentWave = WaveController.currentWave
        val map = Settings.map

        val fileChooser = new JFileChooser
        fileChooser.setDialogTitle("Choose a location to save the game")

        val filter = new FileNameExtensionFilter("sav file", "sav")
        fileChooser.addChoosableFileFilter(filter)
        fileChooser.setFileFilter(filter)

        val frame = new JFrame("Save")
        val response = fileChooser.showSaveDialog(frame)

        if (response == JFileChooser.APPROVE_OPTION) {
            val path = fileChooser.getSelectedFile.getAbsolutePath
            val saveDir = new File(path + ".sav")
            val saveFile = new PrintWriter(saveDir)

            saveFile.println("#MAP")
            for (row <- map) saveFile.println(row.mkString(","))
            saveFile.println("#ENEMY")
            saveFile.println(s"0/$enemies")
            //saveFile.println(s"1/$enemies")   // Missing still different types of enemies
            saveFile.println("#WAVES")
            saveFile.println(s"$currentWave/$maxWaves")
            saveFile.close()
        }
    }

}
