package o1.adventure.game

import o1.adventure.game.{Area, Item, Photo, PhotoAlbum}

import scala.collection.mutable.Map

class Player(startingArea: Area, val itemRewards: Map[String, Item], val photoRewards: Map[String, Photo]):
  var name: String = "name"
  var currentLocation = startingArea
  private var quitCommandGiven = false
  private var inventoryItems = Map[String, Item]()
  private val rewardTracker = Map[String, Boolean]()
  var photoTracker = Map[String, Photo]()
  val album = new PhotoAlbum
  private val locationRewardTracker = Map[String, Boolean]()

  private var goldDabloons = 10
  def currentgoldDabloons = s"You have ${this.goldDabloons} gold Dabloons"
  def addgoldDabloons(amount: Int): Unit = this.goldDabloons += amount
  def subtractgoldDabloons(amount: Int): Boolean =
    if this.goldDabloons >= amount then
      this.goldDabloons -= amount
      true
    else
      false

  def hasQuit = this.quitCommandGiven

  def location = this.currentLocation

  /**CAT*/
  def hasClaimedCatReward: Boolean = this.rewardTracker.contains("cat")

  def grantCatPhotoReward(photo: Photo): Unit =
    this.album.addPhoto(photo)
    this.photoTracker += (photo.name -> photo)
    this.rewardTracker += ("cat" -> true)

  /**SHOREMAN*/
  def hasClaimedShoremanReward : Boolean = this.rewardTracker.contains("shoreman")

  def grantShoremanPhotoReward(photo: Photo): Unit =
    this.album.addPhoto(photo)
    this.photoTracker += (photo.name -> photo)
    this.rewardTracker += ("shoreman" -> true)

  /**HOTEL OWNER*/
  var talkedToReceptionist: Boolean = false

  def grantHotelOwnerReward(photo: Photo): Unit =
    this.album.addPhoto(photo)
    this.photoTracker += (photo.name -> photo)
    this.rewardTracker += ("hotel owner" -> true)
  
  private var trashcanOccurence = 0

  def go(direction: String): String =
    val trueDirection = direction.toLowerCase.trim
    this.currentLocation.neighbor(trueDirection) match
      case Some(newArea) =>
        this.currentLocation = newArea
        val newAreaKey = newArea.name.toLowerCase
        var rewardMessage = ""
        if trashcanOccurence < 1 then
          if newAreaKey == "trashcan" && !this.locationRewardTracker.contains(newAreaKey) then
            this.goldDabloons += 1
            trashcanOccurence += 1
            this.locationRewardTracker += (newAreaKey -> true)
            rewardMessage = s"\nAs you come closer to the trashcan you see something blink, you automatically root around in the $newAreaKey and find 1 extra gold Dabloon! Total Gold: ${this.goldDabloons}."
        s"You go to $trueDirection." + rewardMessage
      case None =>
        s"You can't go to $trueDirection."

  def talk(targetName: String): String =
    val person = this.currentLocation.findNPC(targetName)
    val npcKey = targetName.toLowerCase
    person match
      case Some(npc) =>
        var costMessage = ""
        var paid = false
        //FORTUNE TELLER
        if npcKey == "fortune teller" then
          if !this.rewardTracker.contains(npcKey) then
            // FIRST TIME
            if this.goldDabloons < 2 then
              return s"The Fortune Teller glares. The sign clearly states you need 2 Dabloons to speak with her. You only have ${this.goldDabloons}."
            else
              this.goldDabloons -= 2
              paid = true
              val rewardMsg = this.handleReward(npcKey, npc.name)
              costMessage = s"'I can see from your aura that you are a fair child' you heard something whispering as you dropped the money in the jar.' \n[You pay 2 Dabloons to the Fortune Teller. Remaining Dabloons: ${this.goldDabloons}.]\n"
              return costMessage + npc.dialogue
          else
            // ALREADY REWARDED
            ()
        //OATGURT CART OWNER
        else if npcKey == "oatgurt cart owner" then
          if !this.rewardTracker.contains(npcKey) then
            // FIRST TIME
            if this.goldDabloons < 1 then
              return s"The cart owner stares at you, waiting for you to buy some OatGurt. You don't have the money to pay him though, so you feel his constant stare as you back away uncomfortably."
            else
              this.goldDabloons -= 1
              paid = true
              val rewardMsg = this.handleReward(npcKey, npc.name)
              costMessage = s"\nHe hands you an oatgurt.\n*You get an oatgurt!*\nOut of pity you leave 1 Dabloon for him and uncomfortably retreat into the square. \n[You give 1 gold Dabloon to the Oatgurt Cart owner. Remaining Dabloons: ${this.goldDabloons}.]"
              return npc.dialogue + costMessage
          else
            // ALREADY REWARDED
            ()
        if npcKey == "receptionist" then
          talkedToReceptionist = true

        //GENERAL NPC & REWARD
        var response = npc.dialogue
        costMessage += response
        // Reward check for npsc:
        // 1. Must NOT be claimed already (!rewardTracker.contains(npcKey))
        // 2. Must be a npc who just paid (paid == true) OR not Fortune Teller/Oatgurt Owner
        if !this.rewardTracker.contains(npcKey) && (paid || (npcKey != "fortune teller" && npcKey != "oatgurt cart owner")) then
          response += this.handleReward(npcKey, npc.name)
        response
      case None => s"You don't see anyone named '$targetName' here."

  private def handleReward(npcKey: String, npcName: String):String =
    var rewardMessage= ""
    this.itemRewards.get(npcKey) match {
      case Some(rewardItem) =>
        this.inventoryItems += (rewardItem.name -> rewardItem)
        this.rewardTracker += (npcKey -> true)
        rewardMessage += s"\n*The $npcName gives you the ${rewardItem.name}!*"
      case None =>
        this.photoRewards.get(npcKey) match {
          case Some(rewardPhoto) =>
            this.album.addPhoto(rewardPhoto)
            this.photoTracker += (rewardPhoto.name -> rewardPhoto)
            this.rewardTracker += (npcKey -> true)
            rewardMessage += s"\n*The $npcName gives you a photo.*"
            rewardMessage += s"\n${rewardPhoto.use()}"
        }
    }
    rewardMessage

  def photoStatus(allPhotos: List[String]): String =
    val collectedCount = allPhotos.count(name => this.hasPhoto(name))
    val totalCount = allPhotos.size
    s"You have $collectedCount / " + (totalCount - 1) + " photos collected."
   
  def checkGameStatus: String =
    this.photoStatus(allPhotoNames)
  
  private val allPhotoNames = 
    List("sentimental photo", "twin towers", "escapee", "blender buddies", "asylum", "horse photo")

  def help() = "What you can do:  \n'go' - go somewhere \n'quit' - quit\n'talk' - talk to someone\n'inventory' - check your inventory\n'get' - get an item you see\n'drop' - drop an item from your inventory\n'examine' - get an item's description\n'dabloons' - check how much Dabloons you have\n'view' - view a photo you have collected\n'help' - see what actions you can perform"

  def view(photoName: String): String =
    this.album.viewPhoto(photoName)

  def quit() =
    this.quitCommandGiven = true
    ""

  def get(itemName: String): String =
    val item = this.location.removeItem(itemName)
    item match
      case Some(nwm) =>
        inventoryItems = inventoryItems += itemName -> nwm
        s"You pick up the $itemName."
      case None => s"There is no $itemName here to pick up."

  def drop(itemName: String): String =
    inventoryItems.remove(itemName) match
      case Some(jakiescos) =>
        this.location.addItem(jakiescos)
        s"You drop the $itemName."
      case None =>
        "You don't have that!"

  def inventory: String =
    var output = ""
    if inventoryItems.isEmpty then
      output += "You are empty-handed (items).\n"
    else
      output += "You are carrying:\n" + inventoryItems.keys.mkString("\n") + "\n"
    output += "\n" + this.album.listPhotos
    output.trim

  def has(itemName: String): Boolean =
    inventoryItems.contains(itemName.toLowerCase)

  def hasPhoto(photoName: String): Boolean =
    this.photoTracker.contains(photoName)

  def examine(itemName: String): String =
    val index = inventoryItems.get(itemName)
    index match
      case Some(itemHere) => s"You look closely at the $itemName.\n${itemHere.description}"
      case None => s"If you want to examine something, you need to pick it up first."

  override def toString = "Now at: " + this.location.name

end Player

