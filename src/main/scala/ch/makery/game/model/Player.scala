package ch.makery.game.model

import ch.makery.game.util.Database
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalikejdbc._

import scala.util.Try

class Player(val name: String) extends Database{
  val playerName = new StringProperty(name)
  var xPosition = ObjectProperty[Int](-1)
  var yPosition = ObjectProperty[Int](9)
  var playerMoveCount = ObjectProperty[Int](0)

  def save(): Try[Int] = {
      Try(DB autoCommit { implicit session =>
        sql"""
          insert into player (playerName, moveCount) values
            (${playerName.value}, ${playerMoveCount()})
        """.update.apply()
      })
    }
}

object Player extends Database{
  def apply(name: String, moveCount: Int): Player = {

    new Player(name){
      playerMoveCount.value = moveCount
    }
  }

  def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
      create table player (
        id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        playerName varchar(64),
        moveCount int
      )
      """.execute.apply()
    }
  }

  def getAllPlayers: List[Player] = {
    DB readOnly { implicit session =>
      sql"select * from player".map(row => Player(row.string("playerName"),
        row.int("moveCount"))).list.apply()
    }
  }
}