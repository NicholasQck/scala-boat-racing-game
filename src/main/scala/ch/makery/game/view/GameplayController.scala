package ch.makery.game.view

import ch.makery.game.MainApp
import ch.makery.game.model.{GameBoard, Player, Trap}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty

import scala.util.Random
import scala.util.control.Breaks


@sfxml
class GameplayController(val gameBoardGrid: GridPane,
                         val turnAnnouncementLabel: Label,
                         val player1NameLabel: Label,
                         val player2NameLabel: Label,
                         val rollDiceButton: Button,
                         val turnCountLabel: Label,
                         val logMessageLabel: Label) {
  var dialogStage: Stage = null
  private var _player1: Player = null
  private var _player2: Player = null
  private var winner: Player = null
  private var _difficulty = ""
  private var board: GameBoard = null
  private var gameEnded: Boolean = false
  private var player1Turn: Boolean = true

  def player1 = _player1

  def player1_=(player1: Player) {
    _player1 = player1
    player1NameLabel.text = _player1.playerName.value
  }

  def player2 = _player2

  def player2_=(player2: Player) {
    _player2 = player2
    player2NameLabel.text = _player2.playerName.value
  }

  def difficulty = _difficulty

  def difficulty_=(difficulty: String) {
    _difficulty = difficulty
    board = new GameBoard(_difficulty)
  }

  //get the view of the gameplay ready
  def startGame(): Unit ={
    val boost = new Image("/images/boost.png", 75, 75, true, true)
    val trap = new Image("/images/trap.png", 75, 75, true, true)
    val startingLine = new Image("/images/start.png", 75, 75, true, true)
    val endingLine = new Image("/images/end.png", 75, 75, true, true)
    for (item <- board.boardItems){
      if (item.isInstanceOf[Trap]){
        gameBoardGrid.add(new ImageView(trap), item.col, item.row)
      }
      else{
        gameBoardGrid.add(new ImageView(boost), item.col, item.row)
      }
    }
    gameBoardGrid.add(new ImageView(startingLine), 0, 9)
    gameBoardGrid.add(new ImageView(endingLine), 0, 0)
    logMessageLabel.text = "Roll the dice to start your turn"
    turnAnnouncementLabel.text = s"${_player1.playerName.value}'s turn"
    turnCountLabel.text = 1.toString
  }

  //start a player's turn by rolling the dice
  def rollDice(action:ActionEvent): Unit ={
    var player: Player = null
    var playerId = ""
    var nextPlayerName = ""
    if (player1Turn) {
      player = _player1
      playerId = "player1Boat"
      nextPlayerName = _player2.playerName.value
    }
    else{
      player = _player2
      playerId = "player2Boat"
      nextPlayerName = _player1.playerName.value
    }
    val random1 = new Random()
    val moveRolled = 1 + random1.nextInt(6)
    var checkEffect = 0
    logMessageLabel.text = s"${player.playerName.value} have rolled $moveRolled"
    move(moveRolled, playerId, player.xPosition(), player.yPosition())

    do{
      checkEffect = checkPosition(player.xPosition(), player.yPosition())
      if (checkEffect != 0){
        if (checkEffect < 0){
          logMessageLabel.text = s"${player.playerName.value} rolled $moveRolled and hit a trap! going back ${checkEffect.abs} space(s)"
        }
        else if (checkEffect > 0){
          logMessageLabel.text = s"${player.playerName.value} rolled $moveRolled and hit a boost! going forward ${checkEffect} space(s)"
        }
        move(checkEffect, playerId, player.xPosition(), player.yPosition())
      }
    }
    while (checkEffect != 0)

    if (!gameEnded){
      turnAnnouncementLabel.text = s"$nextPlayerName's turn"
      player1Turn = !player1Turn

      if (player1Turn){
        var tempCount = turnCountLabel.text.value.toInt
        tempCount = tempCount + 1
        turnCountLabel.text = tempCount.toString
      }
    }
    else{
      //processes that will happen when a player wins the game
      player.playerMoveCount = ObjectProperty[Int](turnCountLabel.text.value.toInt)
      rollDiceButton.disable = true
      logMessageLabel.text = s"${player.playerName.value} has won the game"
      winner = player
      MainApp.playerList += winner
      winner.save()
      val alert = new Alert(Alert.AlertType.Information) {
        initOwner(dialogStage)
        title = "Congratulations"
        headerText = s"Congratulations ${winner.playerName.value} for winning the boat racing game!"
        contentText = "Click on OK to return to the main menu"
      }.showAndWait()
      dialogStage.close()
    }
  }

  //the logic behind the movement of the player's boat
  private def move(step: Int, id: String, x: Int, y: Int): Unit ={
    var tempX = x
    var tempY = y
    val player1_boat = new Image("/images/player1_boat.png", 75, 75, true, true)
    val player2_boat = new Image("images/player2_boat.png", 75, 75, true, true)
    val player1BoatImageView: ImageView = new ImageView(player1_boat)
    val player2BoatImageView: ImageView = new ImageView(player2_boat)
    player1BoatImageView.id = "player1Boat"
    player2BoatImageView.id = "player2Boat"
    val loop = new Breaks()
    //locate image of player boat in previous move and remove it before a new move is made
    loop.breakable{
      for (cell <- gameBoardGrid.children.reverse) {
        if (cell.id.value == id){
          gameBoardGrid.children.remove(cell)
          loop.break()
        }
      }
    }

    var isNegative = false
    if (step < 0){
      isNegative = true
    }
    if (!isNegative) {
      var extraStep = false
      for (i <- 0 until step) {
        //check if steps rolled by player exceeds the final position
        if (tempY == 0 && tempX == 0){
          extraStep = true
        }

        //player will rebound if steps rolled is greater than the final position
        //if no extra steps, player will continue normal forwards movement
        if (tempY == 0 && extraStep) {
          tempX = tempX + 1
        }
        else if (tempX == 9 && tempY % 2 != 0) {
          tempY = tempY - 1
        }
        else if (tempX == 0 && tempY % 2 == 0){
          tempY = tempY - 1
        }
        else if (tempY % 2 == 0) {
          tempX = tempX - 1
        }
        else if (tempY % 2 != 0) {

          tempX = tempX + 1
        }
      }
    } else{
      //backwards movement if player hits a trap
      loop.breakable{
        for (move <- 0 until step.abs) {
          if (tempX == 0 && tempY == 9){
            loop.break()
          }
          else if (tempX == 9 && tempY % 2 == 0) {
            tempY = tempY + 1
          }
          else if (tempX == 0 && tempY % 2 != 0) {
            tempY = tempY + 1
          }
          else if (tempY % 2 == 0) {
            tempX = tempX + 1
          }
          else if (tempY % 2 != 0) {
            tempX = tempX - 1
          }
        }
      }
    }
    if (id == "player1Boat"){
      gameBoardGrid.add(player1BoatImageView, tempX, tempY)
      _player1.xPosition = ObjectProperty[Int](tempX)
      _player1.yPosition = ObjectProperty[Int](tempY)
    }
    else{
      gameBoardGrid.add(player2BoatImageView, tempX, tempY)
      _player2.xPosition = ObjectProperty[Int](tempX)
      _player2.yPosition = ObjectProperty[Int](tempY)
    }
  }

  //check player's position for trap or boost
  private def checkPosition(x: Int, y: Int): Int ={
    var itemEffect = 0
    if (!(x == 0 && y == 0)){
      for (item <- board.boardItems) {
        if (x == item.col && y == item.row) {
          itemEffect = item.effect
        }
      }
    }
    else{
      gameEnded = true
    }
    itemEffect
  }

}
