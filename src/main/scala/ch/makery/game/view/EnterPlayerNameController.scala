package ch.makery.game.view

import ch.makery.game.model.Player
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Button, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class EnterPlayerNameController (private val player1NameField: TextField,
                                 private val player2NameField: TextField,
                                 private val setEasyButton: Button,
                                 private val setMediumButton: Button,
                                 private val setHardButton: Button){
  var dialogStage: Stage = null
  private var _player1: Player = null
  private var _player2: Player = null
  var difficulty = "EASY"
  var okClicked = false

  def player1 = _player1
  def player1_=(player1: Player){
    _player1 = player1
    player1NameField.text = _player1.playerName.value
  }

  def player2 = _player2

  def player2_=(player2: Player) {
    _player2 = player2
    player2NameField.text = _player2.playerName.value
  }

  //record the name entered by the two players when the ok button is clicked
  def okSelected(action: ActionEvent): Unit ={
    if (validInput()){
      _player1.playerName <== player1NameField.text
      _player2.playerName <== player2NameField.text

      okClicked = true
      dialogStage.close()
    }
  }

  //return to main menu if the cancel button is clicked
  def cancelSelected(action: ActionEvent): Unit ={
    dialogStage.close()
  }

  //check if input is null
  def nullInput(name: String): Boolean = {
    name == null || name.length == 0
  }

  //check all fields to ensure all input is valid
  def validInput(): Boolean = {
    var errorMessage = ""

    if (nullInput(player1NameField.text.value)){
      errorMessage += "Player 1 name cannot be empty\n"
    }
    else if (nullInput(player2NameField.text.value)){
      errorMessage += "Player 2 name cannot be empty\n"
    }

    if (errorMessage.length() == 0){
      return true
    }
    else{
      val alert = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Error Message"
        headerText = "Please provide a player name to start the game"
        contentText = errorMessage
      }.showAndWait()

      return false
    }
  }

  //set the game difficulty to be easy and indicate to the users by disabling the selected difficulty button
  def setEasy(action:ActionEvent): Unit ={
    difficulty = "EASY"
    setEasyButton.disable = true
    setMediumButton.disable = false
    setHardButton.disable = false
  }

  //set the game difficulty to be medium and indicate to the users by disabling the selected difficulty button
  def setMedium(action: ActionEvent): Unit = {
    difficulty = "MEDIUM"
    setEasyButton.disable = false
    setMediumButton.disable = true
    setHardButton.disable = false
  }

  //set the game difficulty to be hard and indicate to the users by disabling the selected difficulty button
  def setHard(action: ActionEvent): Unit = {
    difficulty = "HARD"
    setEasyButton.disable = false
    setMediumButton.disable = false
    setHardButton.disable = true
  }
}
