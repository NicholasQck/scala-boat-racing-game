package ch.makery.game.view

import ch.makery.game.MainApp
import ch.makery.game.model.Player
import scalafx.event.ActionEvent
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class ScoreboardController(private val playerTable: TableView[Player],
                           private val playerNameColumn: TableColumn[Player, String],
                           private val playerMoveCountColumn: TableColumn[Player, Int]) {
  var dialogStage: Stage = null

  //setup the scoreboard records
  playerTable.items = MainApp.playerList
  playerNameColumn.cellValueFactory = {_.value.playerName}
  playerMoveCountColumn.cellValueFactory = {_.value.playerMoveCount}

  //method to return to the main menu
  def backToMainSelected(action: ActionEvent): Unit = {
    dialogStage.close()
  }
}
