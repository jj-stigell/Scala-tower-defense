package TowerGame.FileIO

import TowerGame.Controllers.WaveController
import TowerGame.Enemies.Enemy
import TowerGame.Player.Player
import TowerGame.Towers.Tower
import TowerGame.Area
import TowerGame.Config.Settings

import java.io.{File, PrintWriter}
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JFileChooser, JFrame}
import scala.collection.mutable.Buffer

/** Save game state (map, enemies, waves and towers) to a sav-file */
object Saver {

    def saveGame() = {
        val maxWaves: Int = WaveController.maxWaves
        val enemies: Buffer[Enemy]  = Area.enemies
        val currentWave: Int  = WaveController.currentWave
        val map: Array[Array[Int]] = Settings.map
        val health: Int  = Player.getHealth
        val maxHealth: Int  = Settings.maxHealth
        val money: Int  = Player.moneyIntheBank
        val towers: Buffer[Tower] = Area.towers

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
            val smallEnemies = enemies.filter(_.enemyType == "small")
            val bigEnemies = enemies.filter(_.enemyType == "big")

            saveFile.println("#MAP")
            for (row <- map) saveFile.println(row.mkString(","))
            saveFile.println("#ENEMY")
            saveFile.println(s"0/${smallEnemies.length}")
            saveFile.println(s"1/${bigEnemies.length}")

            if (towers.nonEmpty) {
                val smallTowers = towers.filter(_.towerType == "small")
                val bigTowers = towers.filter(_.towerType == "big")
                saveFile.println("#TOWER")
                saveFile.println(s"0/${smallTowers.length}")
                saveFile.println(s"1/${bigTowers.length}")
                saveFile.println("#TOWERLOCATION")
                smallTowers.foreach(tower => saveFile.print(s"${tower.getLocation.x},${tower.getLocation.y};"))
                saveFile.println()
                bigTowers.foreach(tower => saveFile.print(s"${tower.getLocation.x},${tower.getLocation.y};"))
                saveFile.println()
            }
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
