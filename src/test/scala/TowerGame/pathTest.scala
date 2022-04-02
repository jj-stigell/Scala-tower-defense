package TowerGame

import TowerGame.Helpers.PathFinder
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
    val pathAndDirections1 = (PathFinder.enemyPath((0, 1), (1, 0))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((3,1), (3,3), (5,3), (5,1), (7,1), (7,3), (9,3), (9,6), (6,6), (6,8)), pathAndDirections1._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((1,0), (0,1), (1,0), (0,-1), (1,0), (0,1), (1,0), (0,1), (-1,0), (0,1)), pathAndDirections1._2)

    // Start direction to up (0, -1), start location (6, 8)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(3, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0)
    ))
    val pathAndDirections2 = (PathFinder.enemyPath((6, 8), (0, -1))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((6,6), (9,6), (9,3), (7,3), (7,1), (5,1), (5,3), (3,3), (3,1), (0,1)), pathAndDirections2._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((0,-1), (1,0), (0,-1), (-1,0), (0,-1), (-1,0), (0,1), (-1,0), (0,-1), (-1,0)), pathAndDirections2._2)

    // Start direction to down (0, -1), start location (3, 0)
    Settings.setMap( Array(
      Array(0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 3),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    ))
    val pathAndDirections3 = (PathFinder.enemyPath((3, 0), (0, 1))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((3,4), (1,4), (1,6), (5,6), (5,1), (7,1), (7,3), (9,3), (9,6), (12,6)), pathAndDirections3._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((0,1), (-1,0), (0,1), (1,0), (0,-1), (1,0), (0,1), (1,0), (0,1), (1,0)), pathAndDirections3._2)

    // Start direction to left (-1, 0), start location (12, 6)
    Settings.setMap( Array(
      Array(0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 2),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    ))
    val pathAndDirections4 = (PathFinder.enemyPath((12, 6), (-1, 0))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((9,6), (9,3), (7,3), (7,1), (5,1), (5,6), (1,6), (1,4), (3,4), (3,0)), pathAndDirections4._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((-1,0), (0,-1), (-1,0), (0,-1), (-1,0), (0,1), (-1,0), (0,-1), (1,0), (0,-1)), pathAndDirections4._2)

    // Start direction to left (-1, 0), start location (29, 1)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 2),
      Array(0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0)
    ))
    val pathAndDirections5 = (PathFinder.enemyPath((29, 1), (-1, 0))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((28,1), (28,3), (26,3), (26,1), (24,1), (24,7), (27,7), (27,11), (20,11), (20,5), (14,5), (14,14), (18,14), (18,18), (9,18), (9,12), (7,12), (7,17), (5,17), (5,6), (7,6), (7,2), (1,2), (1,6), (3,6), (3,12), (1,12), (1,20), (24,20), (24,19), (26,19), (26,21)), pathAndDirections5._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((-1,0), (0,1), (-1,0), (0,-1), (-1,0), (0,1), (1,0), (0,1), (-1,0), (0,-1), (-1,0), (0,1), (1,0), (0,1), (-1,0), (0,-1), (-1,0), (0,1), (-1,0), (0,-1), (1,0), (0,-1), (-1,0), (0,1), (1,0), (0,1), (-1,0), (0,1), (1,0), (0,-1), (1,0), (0,1)), pathAndDirections5._2)

    // Start direction to up (0, -1), start location (26, 21)
    Settings.setMap( Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 3, 0),
      Array(0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
      Array(0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
      Array(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0)
    ))
    val pathAndDirections6 = (PathFinder.enemyPath((26, 21), (0, -1))).unzip
    assertEquals("Wrong path:", mutable.Buffer[(Int, Int)]((26,18), (24,18), (24,20), (1,20), (1,12), (3,12), (3,6), (1,6), (1,2), (7,2), (7,6), (5,6), (5,17), (7,17), (7,12), (9,12), (9,18), (18,18), (18,14), (14,14), (14,5), (20,5), (20,11), (27,11), (27,7), (24,7), (24,1), (26,1), (26,3), (28,3), (28,1)), pathAndDirections6._1)
    assertEquals("Wrong directions:", mutable.Buffer[(Int, Int)]((0,-1), (-1,0), (0,1), (-1,0), (0,-1), (1,0), (0,-1), (-1,0), (0,-1), (1,0), (0,1), (-1,0), (0,1), (1,0), (0,-1), (1,0), (0,1), (1,0), (0,-1), (-1,0), (0,-1), (1,0), (0,1), (1,0), (0,-1), (-1,0), (0,-1), (1,0), (0,1), (1,0), (0,-1)), pathAndDirections6._2)
  }

}
