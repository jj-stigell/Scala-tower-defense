package TowerGame.FileIO

import TowerGame.Area.{correctedPath, directions}
import TowerGame.Config.Settings
import TowerGame.Enemies._
import TowerGame.Helpers.Vector2D
import TowerGame.Towers._

import java.io.BufferedReader
import javax.swing.JOptionPane
import scala.collection.mutable.Buffer

/** Reader has functions for reading from a saved file all the game attributes. */
object Reader {

	/**
	 * Read line by line the game map.
	 * @param reader 	BufferedReader
	 * @return 				Last read line
	 */
  def readMap(reader: BufferedReader): String = {

		var map: Array[Array[Int]] = Array(Array())
    var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#") && !Loader.mapError) {
			if (line.nonEmpty) {

				try {
					map = map :+ line.split(",").map(_.toInt)
				} catch {
					case numberFormatException: NumberFormatException =>
						JOptionPane.showMessageDialog(null, "Read map in the sav-file is corrupted or incorrectly inputted.\nMap is set to default and towers are not placed. Enjoy the game")
						Loader.mapError = true
						Loader.loadedMap = Settings.defaultMaps(0)
				}
			}
			line = reader.readLine()
		}

		if (!Loader.mapError) {
			map = map.drop(1) // Drop the first element that is empty
			var invalid = false
			var entry = 0
			var exit = 0
			// Check that all rows are equal in length
			val mapUniform = map.forall(row => map(0).length == row.length)

			for (row <- map) {
				for (value <- row) {
					value match {
						case 0 => // Tower area
						case 1 => // Path
						case 2 => entry += 1
						case 3 => exit += 1
						case _ => invalid = true
					}
				}
			}

			if (entry != 1 || exit != 1 || invalid || !mapUniform) {
				JOptionPane.showMessageDialog(null, "Read map in the sav-file is corrupted or incorrectly inputted.\nMap is set to default and towers are not placed. Enjoy the game")
				Loader.mapError = true
				Loader.loadedMap = Settings.defaultMaps(0)
			} else {
				Loader.loadedMap = map
			}
		}

		line
  }

	/**
	 * Read the amount and type of enemies.
	 * @param reader 	BufferedReader
	 * @return 				Last read line
	 */
	def readEnemies(reader: BufferedReader): String = {

		var enemies: Buffer[Enemy] = Buffer[Enemy]()
		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {

				try {
					var numbers = line.split("/").map(_.toInt)
					if (numbers(0) == 0) for (i <- 1 to numbers(1)) enemies += new SmallEnemy(correctedPath, directions)
					else if (numbers(0) == 1) for (i <- 1 to numbers(1)) enemies += new BigEnemy(correctedPath, directions)
				} catch {
					case numberFormatException: NumberFormatException =>
						JOptionPane.showMessageDialog(null, "Error reading enemies from sav-file. Check the file.\nEnemies are set to random, enjoy the game")
						enemies += new SmallEnemy(correctedPath, directions)
						enemies += new BigEnemy(correctedPath, directions)
						enemies += new SmallEnemy(correctedPath, directions)
						enemies += new BigEnemy(correctedPath, directions)
				}
			}
			line = reader.readLine()
		}

		Loader.loadedEnemies = enemies
		line
	}

	/**
	 * Read the current wave and total number of waves.
	 * @param reader 	BufferedReader
	 * @return 				Last read line
	 */
  def readWaves(reader: BufferedReader): String = {

		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				try {
					val numbers = line.split("/")
					val currentWave = numbers(0).toInt
					val maxWaves = numbers(1).toInt

					if (currentWave <= maxWaves) {
						Loader.loadedCurrentWave = currentWave
						Loader.loadedMaxWaves = maxWaves
					} else {
						JOptionPane.showMessageDialog(null, "Current wave was higher than maximum amount of waves, current wave set to maximum.")
						Loader.loadedCurrentWave = maxWaves
						Loader.loadedMaxWaves = maxWaves
					}
				} catch {
					case numberFormatException: NumberFormatException =>
						JOptionPane.showMessageDialog(null, "Read amount of waves in the sav-file are corrupted or incorrectly inputted.\nWaves are set to 0/1, enjoy the game")
						Loader.loadedCurrentWave = 0
						Loader.loadedMaxWaves = 1
				}
			line = reader.readLine()
			}
		}

		line
	}

	/**
	 * Read the current health points and max healthpoints.
	 * @param reader	BufferedReader
	 * @return				Last read line
	 */
	def readHealth(reader: BufferedReader): String = {

		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {

				try {
					val numbers = line.split("/")
					val currentHealth = numbers(0).toInt
					val maxHealth = numbers(1).toInt

					if (currentHealth <= maxHealth) {
						Loader.loadedCurrentHealth = currentHealth
						Loader.loadedMaxHealth = maxHealth
					} else {
						JOptionPane.showMessageDialog(null, "Current health points were higher than maximum health points, current health points set to maximum.")
						Loader.loadedCurrentHealth = maxHealth
						Loader.loadedMaxHealth = maxHealth
					}
				} catch {
					case numberFormatException: NumberFormatException =>
						val health = 100
						JOptionPane.showMessageDialog(null, s"Read amount of health points in the sav-file are corrupted or incorrectly inputted.\nHealth points are set to ${health}/${health}, enjoy the game")
						Loader.loadedCurrentHealth = health
						Loader.loadedMaxHealth = health
				}
			}
			line = reader.readLine()
		}

		line
	}

		/**
	 * Read the amount of money player has in the bank.
	 * Read amount is converted to positive integer.
	 * @param reader	BufferedReader
	 * @return				Last read line
	 */
	def readMoney(reader: BufferedReader): String = {

		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				try {
					Loader.loadedMoney = line.toInt.abs
				} catch {
					case formatException: NumberFormatException =>
						Loader.loadedMoney = 50
						JOptionPane.showMessageDialog(null, s"Read amount of money in the sav-file is corrupted or incorrectly inputted.\nAmount of money set to ${Loader.loadedMoney}")
				}
			}
			line = reader.readLine()
		}

		line
	}

	/**
	 * Read the amount and type of towers.
	 * @param reader	BufferedReader
	 * @return				Last read line
	 */
	def readTowers(reader: BufferedReader): String = {

		var towers: Buffer[Tower] = Buffer[Tower]()
		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				try {
					var numbers = line.split("/").map(_.toInt)
					if (numbers(0) == 0) for (i <- 1 to numbers(1)) towers += new SmallTower(Vector2D(0,0))
					else if (numbers(0) == 1) for (i <- 1 to numbers(1)) towers += new BigTower(Vector2D(0,0))
				} catch {
					case formatException: NumberFormatException =>
						JOptionPane.showMessageDialog(null, s"Read tower types in the sav-file are corrupted or incorrectly inputted.\nNo towers will be added from saved file.")
				}
			}
			line = reader.readLine()
		}

		if (!Loader.mapError) Loader.loadedTowers = towers
		line
	}

		/**
	 * Read the location of each tower.
	 * @param reader	BufferedReader
	 * @return				Last read line
	 */
	def readLocations(reader: BufferedReader): String = {

		var locations: Array[Vector2D] = Array[Vector2D]()
		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				try {
					var locationsString = line.split(";").map(x => x.split(","))
					val locationsVectors = locationsString.map(x => Vector2D(x(0).toDouble, x(1).toDouble))
					locations = locations ++ locationsVectors
				} catch {
					case formatException: NumberFormatException =>
						JOptionPane.showMessageDialog(null, s"Read tower locations in the sav-file are corrupted or incorrectly inputted.\nNo towers will be added from saved file.")
				}
			}
			line = reader.readLine()
		}

		Loader.loadedTowerLocations = locations
		line
	}

}
