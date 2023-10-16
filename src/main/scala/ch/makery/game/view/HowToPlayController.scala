package ch.makery.game.view

import scalafx.event.ActionEvent
import scalafx.scene.control.TextArea
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class HowToPlayController (val instructionsArea: TextArea){
  var dialogStage: Stage = null
  private val instruction =
    """
      |1. Start the Boat Racing Game by clicking on the Play button in the main menu.
      |2. Enter a name for player 1 and player 2.
      |3. Choose a difficulty for the Boat Racing Game. The default difficulty is set to easy.
      |4. Roll the dice to play the game.
      |5. The player that reaches the end of the game first will be the winner.""".stripMargin

  //set the text area to display the instructions provided
  instructionsArea.text = instruction

  //method to return to the main menu
  def backToMainSelected(action: ActionEvent): Unit ={
    dialogStage.close()
  }
}
