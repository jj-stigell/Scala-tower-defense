package TowerGame

import java.awt.event.ActionListener
import java.awt.{Color, Graphics2D, RenderingHints}
import scala.swing._
import scala.swing.event.ButtonClicked
import javax.swing.{JFrame, JOptionPane}

object Game extends SimpleSwingApplication {

  val width = Settings.width
  val height = Settings.height
  val fullHeight = Settings.fullHeight
  val map = Settings.map
  var wave = 1
  var gameOver = false
  var gameWon = false
  var roundWon = false

  val blockWidth = width / Settings.totalHorizontalBlocks
  val blockHeight = height / Settings.totalVerticalBlocks

  val startButton = new Button("Start new wave!")
  val menuButton = new Button("Menu")
  val buyTowerButton = new Button("Buy Tower: " + Settings.towerPrice + "$")

  val healthPoints = new Label
  healthPoints.text = "Current Health: " + Player.getHealth + "/" + Settings.maxHealth

  val moneyInTheBank = new Label
  moneyInTheBank.text = "Money: " + Player.moneyIntheBank + "$"

  val waveNumber = new Label
  waveNumber.text = "Current wave: " + wave + "/" + Settings.maxWaves


  def updateStats() = {
    healthPoints.text = "Current Health: " + Player.getHealth + "/" + Settings.maxHealth
    moneyInTheBank.text = "Money: " + Player.moneyIntheBank + "$"
    waveNumber.text = "Current wave: " + wave + "/" + Settings.maxWaves
    gameOver = !Player.isAlive
    roundWon = Player.isAlive && Area.enemies.forall(!_.isAlive)
    gameWon = this.wave > Settings.maxWaves
  }


  /**
   * MainFrame is the application window class in scala-swing.
   */
  def top = new MainFrame {

    title = Settings.title
    resizable = Settings.resizable

    minimumSize = new Dimension(width, fullHeight)
    preferredSize = new Dimension(width, fullHeight)
    maximumSize = new Dimension(width, fullHeight)

    /**
     * The panel inside our window, where the enemies are moving.
     */

    val arena = new Panel {

      /**
       * Standard panel is just a dull box where we can add other components.
       *
       * Overriding the paintComponent method enables drawing own graphics.  It gets as a parameter
       * a Graphics2D object, through which it is possible to draw into the object,
       * change colors, coordinates, line thickness etc.
       */

      override def paintComponent(g: Graphics2D) = {

        // Paint on the old image a rectangle with bright blue (red=80, green=180, blue=235)
        g.setColor(new Color(51, 153, 51))
        g.fillRect(0, 0, width, fullHeight)

        g.setColor(new Color(198, 140, 83))

        var rowNumber = 0

        // draw path
        for (row <- map) {
          var columnNumber = 0
          for (element <- row) {
            if (element == 1 || element == 2) {
              val x0 = columnNumber * blockWidth
              val y0 = rowNumber * blockHeight
              g.fillRect(x0, y0, blockWidth, blockHeight)
            } else if (element == 3) {
              g.setColor(new Color(87, 83, 198))
              val x0 = columnNumber * blockWidth
              val y0 = rowNumber * blockHeight
              g.fillRect(x0, y0, blockWidth, blockHeight)
              g.setColor(new Color(198, 140, 83))
            }
            columnNumber += 1
          }
          rowNumber += 1
        }

        // Ask Graphics2D to provide us smoother graphics, i.e., antialising
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // change paint color to white and ask the space to redraw itself.
        g.setColor(Color.white)
        Area.draw(g)
      }

    }

    val verticalPanel = new BoxPanel(Orientation.Vertical)
    val controlPanel = new BoxPanel(Orientation.Horizontal)

    controlPanel.contents += startButton
    controlPanel.contents += menuButton
    controlPanel.contents += buyTowerButton
    controlPanel.contents += healthPoints
    controlPanel.contents += moneyInTheBank
    controlPanel.contents += waveNumber
    verticalPanel.contents += arena
    verticalPanel.contents += controlPanel
    contents = verticalPanel

    // ask the object to listed mouse events in our arena panel
    listenTo(arena.mouse.clicks)
    listenTo(arena.mouse.moves)
    listenTo(startButton)
    listenTo(menuButton)
    listenTo(buyTowerButton)

    // and now that the class responds to events, we shoot in the space simulation at the same point
    this.reactions += {
      //case scala.swing.event.MousePressed(src, point, _, _, _) => Area.shoot(point.x, point.y)
      //case wheelEvent: MouseMoved => println("x on: " + wheelEvent.point.x + " y on: " + wheelEvent.point.y)
      case clickEvent: ButtonClicked => println("klikattu: " + clickEvent.source.text)
    }

    // This event listener and swing timer allow periodic repetitive
    // activity in the event listening thread. The game is light enough
    // to be drawn in the thread without additional buffers or threads.

    val listener = new ActionListener() {
      def actionPerformed(e: java.awt.event.ActionEvent) = {

        if (gameOver) {
          JOptionPane.showMessageDialog(new JFrame("Game Over!!!"), "Game Over!!!")
        } else {
          Area.step()
          arena.repaint()
        }
      }
    }




    // Timer sends ActionEvent to ActionListener every 6ms,
    // when the space moves forward and the screen is redrawn.
    // This code therefore allows animation
    val timer = new javax.swing.Timer(Settings.interval, listener)
    timer.start()

  }


}
