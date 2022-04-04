package TowerGame.FileIO

import TowerGame.Area.{correctedPath, directions}
import TowerGame.Enemies.{Enemy, SmallEnemy}
import java.io.BufferedReader
import scala.collection.mutable.Buffer

object Reader {

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

	def readEnemies(reader: BufferedReader): String = {

		var enemies: Buffer[Enemy] = Buffer[Enemy]()
		var line = reader.readLine()

		while (line != null && !line.trim.startsWith("#")) {
			if (line.nonEmpty) {
				var numbers = line.split("/").map(_.toInt)
				if (numbers(0) == 0) for (i <- 1 to numbers(1)) enemies += new SmallEnemy(correctedPath, directions)
				else if (numbers(0) == 1) for (i <- 1 to numbers(1)) {/*pass until new enemy type created*/}
			}
			line = reader.readLine()
		}

		Loader.loadedEnemies = enemies
		line
	}

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

}
