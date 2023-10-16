package ch.makery.game.view

import ch.makery.game.MainApp
import ch.makery.game.model.Player
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuController(){
  //initiate the processes required to start a game
  def playSelected(action: ActionEvent) = {
    val player1 = new Player("")
    val player2 = new Player("")
    val config = MainApp.showEnterPlayerName(player1, player2)
    if (config._1){
      MainApp.showGameplay(player1, player2, config._2)
    }
  }

  //shows the players the instructions
  def howToPlaySelected(action: ActionEvent) = {
    MainApp.showHowToPlay()
  }

  //shows the players the scoreboard
  def scoreboardSelected(action: ActionEvent): Unit ={
    MainApp.showScoreboard()
  }
}