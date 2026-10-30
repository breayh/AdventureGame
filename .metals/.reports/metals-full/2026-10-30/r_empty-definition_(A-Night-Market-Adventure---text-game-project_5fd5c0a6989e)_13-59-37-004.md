file://<WORKSPACE>/game/Photo.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -o1.
	 -scala/Predef.o1.
offset: 9
uri: file://<WORKSPACE>/game/Photo.scala
text:
```scala
import o1@@.adventure.game.Item
import o1.gui.Pic
import o1.show


class Photo(name: String, description: String, val photo: Pic) extends Item(name, description) {
  def use() =
    show(photo)
    s"You're looking at: $name"
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 