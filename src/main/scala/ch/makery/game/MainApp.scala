package ch.makery.game
import ch.makery.game.model.Player
import ch.makery.game.util.Database
import ch.makery.game.view.{EnterPlayerNameController, GameplayController, HowToPlayController, ScoreboardController}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp {

  Database.setupDB()

  val playerList = new ObservableBuffer[Player]()

  playerList ++= Player.getAllPlayers

  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Boat Racing Game"
    scene = new Scene {
      root = roots
    }
    icons += new Image(getClass.getResourceAsStream("/images/icon.png"))
  }
  // method to display game menu
  def showMainMenu() = {
    val resource = getClass.getResource("view/MainMenu.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  // display menu when the game starts
  showMainMenu()

  //method to display the dialog for player's to enter their name
  def showEnterPlayerName(player1: Player, player2: Player): (Boolean, String) = {
    val resource = getClass.getResourceAsStream("view/EnterPlayerNameDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[EnterPlayerNameController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
      icons += new Image(getClass.getResourceAsStream("/images/icon.png"))
    }
    control.dialogStage = dialog
    control.player1 = player1
    control.player2 = player2
    dialog.showAndWait()
    (control.okClicked, control.difficulty)
  }

  //method to display the dialog where the gameplay will occur
  def showGameplay(player1: Player, player2: Player, difficulty: String): Unit = {
    val resource = getClass.getResourceAsStream("view/GameplayView.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots3 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[GameplayController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots3
      }
      icons += new Image(getClass.getResourceAsStream("/images/icon.png"))
    }
    control.dialogStage = dialog
    control.player1 = player1
    control.player2 = player2
    control.difficulty = difficulty
    control.startGame
    dialog.showAndWait()
  }

  //method to display the instructions on how to play the game
  def showHowToPlay(): Unit = {
    val resource = getClass.getResourceAsStream("view/HowToPlayDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots4 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[HowToPlayController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots4
      }
      icons += new Image(getClass.getResourceAsStream("/images/icon.png"))
    }
    control.dialogStage = dialog
    dialog.showAndWait()
  }

  //method to display the scoreboard
  def showScoreboard(): Unit ={
    val resource = getClass.getResourceAsStream("view/ScoreBoardDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots5 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[ScoreboardController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots5
      }
      icons += new Image(getClass.getResourceAsStream("/images/icon.png"))
    }
    control.dialogStage = dialog
    dialog.showAndWait()
  }
}
