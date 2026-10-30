package game

class NPC(val name: String, val dialogue: String):
  def matches(inputName: String): Boolean = 
    this.name.trim.equalsIgnoreCase(inputName.trim)
end NPC
