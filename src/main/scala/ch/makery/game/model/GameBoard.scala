package ch.makery.game.model

import scala.collection.mutable.ListBuffer

class GameBoard (val difficulty: String){
  val boardItems: ListBuffer[BoardItem] = new ListBuffer()


  if (difficulty == "EASY"){
    //20 board items 8 trap 12 boost
    boardItems += new Trap(5, 9, -5)
    boardItems += new Trap(8, 8, -3)
    boardItems += new Trap(1, 7, -5)
    boardItems += new Trap(8, 6, -6)
    boardItems += new Trap(2, 6, -4)
    boardItems += new Trap(1, 4, -6)
    boardItems += new Trap(6, 1, -6)
    boardItems += new Trap(5, 0, -4)
    boardItems += new Boost(1, 9, 5)
    boardItems += new Boost(9, 9, 6)
    boardItems += new Boost(2, 8, 2)
    boardItems += new Boost(7, 7, 7)
    boardItems += new Boost(4, 6, 10)
    boardItems += new Boost(0, 5, 6)
    boardItems += new Boost(8, 5, 8)
    boardItems += new Boost(3, 3, 5)
    boardItems += new Boost(6, 3, 6)
    boardItems += new Boost(5, 2, 2)
    boardItems += new Boost(1, 1, 12)
    boardItems += new Boost(3, 0, 3)

  }
  else if (difficulty == "MEDIUM"){
    //20 board items 10 trap 10 boost
    boardItems += new Trap(1, 0, -4)
    boardItems += new Trap(7, 1, -7)
    boardItems += new Trap(2, 2, -5)
    boardItems += new Trap(8, 3, -4)
    boardItems += new Trap(3, 4, -6)
    boardItems += new Trap(4, 5, -4)
    boardItems += new Trap(4, 6, -3)
    boardItems += new Trap(9, 7, -9)
    boardItems += new Trap(0, 8, -6)
    boardItems += new Trap(7, 9, -6)
    boardItems += new Boost(3, 9, 3)
    boardItems += new Boost(9, 8, 5)
    boardItems += new Boost(1, 7, 2)
    boardItems += new Boost(5, 6, 4)
    boardItems += new Boost(7, 5, 7)
    boardItems += new Boost(0, 4, 8)
    boardItems += new Boost(2, 3, 4)
    boardItems += new Boost(6, 2, 6)
    boardItems += new Boost(4, 1, 6)
    boardItems += new Boost(8, 0, 4)
  }
  else if (difficulty == "HARD"){
    //20 board items 15 trap 5 boost
    boardItems += new Trap(2, 0, -3)
    boardItems += new Trap(7, 0, -8)
    boardItems += new Trap(5, 1, -5)
    boardItems += new Trap(4, 2, -4)
    boardItems += new Trap(7, 3, -11)
    boardItems += new Trap(2, 3, -4)
    boardItems += new Trap(6, 4, -6)
    boardItems += new Trap(3, 5, -3)
    boardItems += new Trap(1, 6, -5)
    boardItems += new Trap(7, 6, -5)
    boardItems += new Trap(4, 7, -4)
    boardItems += new Trap(1, 8, -3)
    boardItems += new Trap(7, 8, -2)
    boardItems += new Trap(9, 9, -4)
    boardItems += new Trap(2, 9, -2)
    boardItems += new Boost(8, 8, 3)
    boardItems += new Boost(2, 7, 5)
    boardItems += new Boost(4, 4, 6)
    boardItems += new Boost(9, 3, 4)
    boardItems += new Boost(1, 1, 2)
  }



}
