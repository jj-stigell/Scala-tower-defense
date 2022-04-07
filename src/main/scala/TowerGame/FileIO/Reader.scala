package TowerGame.FileIO

import TowerGame.Area.{correctedPath, directions}
import TowerGame.Enemies._
import TowerGame.Helpers.Vector2D
import TowerGame.Towers._

import java.io.BufferedReader
import scala.collection.mutable.Buffer
import scala.util.Random

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

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				map = map :+ line.split(",").map(_.toInt)
			}
			line = reader.readLine()
		}

    Loader.loadedMap = map.drop(1)
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
				var numbers = line.split("/").map(_.toInt)
				if (numbers(0) == 0) for (i <- 1 to numbers(1)) enemies += new SmallEnemy(correctedPath, directions)
				else if (numbers(0) == 1) for (i <- 1 to numbers(1)) enemies += new BigEnemy(correctedPath, directions)
				Random.shuffle(enemies)
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
				val numbers = line.split("/").map(_.toInt)
				Loader.loadedCurrentWave = numbers(0)
				Loader.loadedMaxWaves = numbers(1)
			}
			line = reader.readLine()
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
				val numbers = line.split("/").map(_.toInt)
				Loader.loadedCurrentHealth = numbers(0)
				Loader.loadedMaxHealth = numbers(1)
			}
			line = reader.readLine()
		}

		line
	}

		/**
	 * Read the amount of money player has in the bank.
	 * @param reader	BufferedReader
	 * @return				Last read line
	 */
	def readMoney(reader: BufferedReader): String = {

		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				Loader.loadedMoney = line.toInt
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
				var numbers = line.split("/").map(_.toInt)
				if (numbers(0) == 0) for (i <- 1 to numbers(1)) towers += new SmallTower(Vector2D(0,0))
				else if (numbers(0) == 1) for (i <- 1 to numbers(1)) towers += new BigTower(Vector2D(0,0))
			}
			line = reader.readLine()
		}

		Loader.loadedTowers = towers
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
				var locationsString = line.split(";").map(x => x.split(","))
				val locationsVectors = locationsString.map(x => Vector2D(x(0).toDouble, x(1).toDouble))
				locations = locations ++ locationsVectors
			}
			line = reader.readLine()
		}

		Loader.loadedTowerLocations = locations
		line
	}

}
