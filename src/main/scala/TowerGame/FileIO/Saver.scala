package TowerGame.FileIO

import TowerGame.{Area, Player, Settings, WaveController}

import java.io.{File, PrintWriter}
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JFileChooser, JFrame}

/** Save game state (map, enemies, waves and towers) to a sav-file */
object Saver {

    def saveGame() = {
        val maxWaves: Int = WaveController.maxWaves
        val enemies: Int  = Area.numberOfEnemies
        val currentWave: Int  = WaveController.currentWave
        val map: Array[Array[Int]] = Settings.map
        val health: Int  = Player.getHealth
        val maxHealth: Int  = Settings.maxHealth
        val money: Int  = Player.moneyIntheBank

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
            saveFile.println("#HEALTH")
            saveFile.println(s"$health/$maxHealth")
            saveFile.println("#MONEY")
            saveFile.println(money)
            saveFile.println("#WAVES")
            saveFile.println(s"$currentWave/$maxWaves")
            saveFile.close()
        }
    }

}
