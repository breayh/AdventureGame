package o1.adventure.game

import o1.adventure.game.Item
import o1.gui.Pic
import o1.show


class Photo(name: String, description: String, val photo: Pic) extends Item(name, description) {
  def use() =
    show(photo)
    s"You're looking at: $name"
}
