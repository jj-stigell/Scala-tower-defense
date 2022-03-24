package TowerGame

import org.junit.Assert._
import org.junit.Test

import scala.collection.mutable

class UnitTests {

  @Test def testFindInitialDirection(): Unit = {

    // Entry from left, direction to right
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0),
      Array(2, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 3),
      Array(0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (1, 0), PathFinder.findInitialDirection((0, 1)))

    // Entry from right, direction to left
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0),
      Array(3, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 2),
      Array(0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (-1, 0), PathFinder.findInitialDirection((4, 3)))

    // Entry from top, direction to down
    Settings.setMap( Array(
      Array(0, 2, 0, 0, 0),
      Array(0, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 0),
      Array(0, 0, 0, 3, 0)
    ))
    assertEquals("Wrong value:", (0, 1), PathFinder.findInitialDirection((1, 0)))

    // Entry from down, direction to up
    Settings.setMap( Array(
      Array(0, 3, 0, 0, 0),
      Array(0, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 0),
      Array(0, 0, 0, 2, 0)
    ))
    assertEquals("Wrong value:", (0, -1), PathFinder.findInitialDirection((3, 4)))
  }

  @Test def testEnemyInitialLocation(): Unit = {

    // Entry from left, direction to right
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0),
      Array(2, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 3),
      Array(0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (0, 1), PathFinder.enemyInitialLocation())

    // Entry from right, direction to left
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0),
      Array(3, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 2),
      Array(0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (4, 3), PathFinder.enemyInitialLocation())

    // Entry from top, direction to down
    Settings.setMap( Array(
      Array(0, 2, 0, 0, 0),
      Array(0, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 0),
      Array(0, 0, 0, 3, 0)
    ))
    assertEquals("Wrong value:", (1, 0), PathFinder.enemyInitialLocation())

    // Entry from down, direction to up
    Settings.setMap( Array(
      Array(0, 3, 0, 0, 0),
      Array(0, 1, 1, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 1, 1, 0),
      Array(0, 0, 0, 2, 0)
    ))
    assertEquals("Wrong value:", (3, 4), PathFinder.enemyInitialLocation())
  }

  @Test def testFindNewDirection(): Unit = {

    // Direction to right (1, 0), current location (5, 3), new direction to up (0, -1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (0, -1), PathFinder.findNewDirection((1, 0), (5, 3)))

    // Direction to right (1, 0), current location (7, 1), new direction to down (0, 1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (0, 1), PathFinder.findNewDirection((1, 0), (7, 1)))

    // Direction to left (-1, 0), current location (7, 3), new direction to up (0, -1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (0, -1), PathFinder.findNewDirection((-1, 0), (3, 3)))

    // Direction to left (-1, 0), current location (7, 3), new direction to down (0, 1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (0, 1), PathFinder.findNewDirection((-1, 0), (7, 3)))

    // Direction to up (0, -1), current location (6, 6), new direction to right (1, 0)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (1, 0), PathFinder.findNewDirection((0, -1), (6, 6)))

    // Direction to up (0, -1), current location (9, 3), new direction to left (-1, 0)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (-1, 0), PathFinder.findNewDirection((0, -1), (9, 3)))

    // Direction to down (0, 1), current location (3, 3), new direction to right (1, 0)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (1, 0), PathFinder.findNewDirection((0, 1), (3, 3)))

    // Direction to down (0, 1), current location (9, 6), new direction to left (-1, 0)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    assertEquals("Wrong value:", (-1, 0), PathFinder.findNewDirection((0, 1), (9, 6)))
  }

  @Test def testEnemyPath(): Unit = {

    // Start direction to right (1, 0), start location (0, 1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(2, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0)
    ))
    var pathAndDirections = (PathFinder.enemyPath((0, 1), (1, 0))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((3,1), (3,3), (5,3), (5,1), (7,1), (7,3), (9,3), (9,6), (6,6), (6,8)), pathAndDirections._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((1,0), (0,1), (1,0), (0,-1), (1,0), (0,1), (1,0), (0,1), (-1,0), (0,1)), pathAndDirections._2)
  }

}
