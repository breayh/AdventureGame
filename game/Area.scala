package o1.adventure.game

import scala.collection.mutable.Map

class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private val people = Map[String, NPC]()

  def neighbor(direction: String) = this.neighbors.get(direction)

  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits

  def addNPC(npc: NPC): Unit =
    this.people += (npc.name.toLowerCase -> npc)

  def findNPC(name: String): Option[NPC] =
    this.people.get(name.toLowerCase)
   
  def setPeople(npcPairs: Vector[(String, NPC)]) =
    npcPairs.foreach(pair => this.addNPC(pair._2))

  private var itemList = LazyList[Item]()

  def addItem(item: Item): Unit =
    itemList = item +: itemList

  def contains(itemName: String): Boolean =
    itemList.exists(_.name == itemName)

  def removeItem(itemName: String): Option[Item] =
    val index = itemList.indexWhere(_.name == itemName)
    if index >= 0 then
      val removedItem = itemList(index)
      itemList = itemList.filterNot(_.name == itemName)
      Some(removedItem)
    else
      None

  def fullDescription: String =
    var description = this.description
    if itemList.nonEmpty then
      description += "\n\nYou see here: " + this.itemList.mkString(", ")
    val peopleNames = this.people.keys.toVector
    if peopleNames.nonEmpty then
      description += "\n\nYou can talk to: " + peopleNames.mkString(", ")
    val exitList = "\n\nYou can go to: " + this.neighbors.keys.mkString(", ")
    description += exitList
    description


  override def toString =
    this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end Area

