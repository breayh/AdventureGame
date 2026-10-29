package o1.adventure.game

import o1.adventure.game.Action
import o1.gui.*

import scala.collection.mutable.Map
import scala.io.StdIn.readLine
import scala.swing.Dialog

class Adventure:

  val title = "A Night Market Adventure"
//start item - 10 coins && photo
  private val coins = Item("Gold Dabloons", "Remember to spend them carefully!")
  private val originalPhoto = Photo("sentimental photo", "a photo you have with you", Pic("o1/adventure/photos/sentimentalPhoto.png"))
  private val compas = Item("compas", "hmm which way to go now?")
  //main street
  private val mainStreet   = Area("Niam Street", "Several stalls are still open despite the late hour. You see two store fronts, Calendula Apothecary and Harty's Meat Shop, each indicated by its own LED sign vying for attention. A few strangers hang around, but most appear to have congregated down the path around stalls in the market center.")
  //apothecary
  private val apothecaryStop = Area("Calendula Apothecary", "You enter the apothecary shop. A delicate woman behind the counter greets you while busily crushing different herbs, and tells you to ask if there’s anything you need. A young child who looks no older than 9 expertly stirs a massive cauldron in the back. A cabinet sits on the right side from the entrance, covered in tonics and pre-made medicines.")
  private val apothecary = NPC("Apothecary", "You set your photo album on the counter and and call the woman over. She pauses what she’s doing and paces over, listening curiously as you ask whether she recognizes the photo your mother left.\n'Is it finally the day? I’ve been so busy raising Viivi that I didn’t realize it’s been years. Of course I recognize that photo... Kaylina was my best friend. Here, give me a moment.'\nShe rummages through one of the cabinets, then scoffs at her unsuccessful search. 'Viivi, would you be a dear and run to the back where mama keeps her secret stash?' Viivi nods and runs off, before returning to the counter with a box crammed full of notes and keepsakes.' The Apothecary pulls out a photo from near the bottom and hands it to you. 'This should be what you’re looking for. There’s a few more photos to find, but the fortune teller should know more.' \nYou thank her and slip the photo into the next slot in the album. 'Good luck!' She calls after you as you leave.")
  private val photoOne = Photo("twin towers", "photo from the apothecary", Pic("o1/adventure/photos/twin towers.png"))
  //meat monger
  private val meatMongerStop = Area("Harty's Meat Shop", "You enter the meat stand, accidentally banging the corner of your shoe against a table leg. While you try to recover from the unfortunate pain, a small gray and brown tabby cat startles and bolts out the door. \nBehind the counter where the cat was sitting, a hulking man towers over you, skinning a round steak huge enough to have come from a horse.")
  private val meatMonger = NPC("Meat Monger","'I’ve seen all kinds of people in my years running this place, and I can tell from your squishy face that you don’t eat meat. I don’t sell to liberal sissies, so don’t you try anything. It’s for your own good.' \nHe says in a gravely voice before continuing. \n'Do me a favor though and give these to Tina if you see her. She’ll come right back.'\nHe hands you a small paper sack with a couple animal bones, before going back to what he was doing. \nYou decide it’s probably best to leave at this point, and duck out of the shop into the main alley.")
  private val animalBones = Item("animal bones", "A bag of animal bones given to you by the butcher. They still have some meat on them.")
  //market square
  private val marketSquare = Area("Market Square", "The main alley is bustling with shoppers. Numerous stalls line the street, selling painted keepsakes and all kinds of oddities. The delicious scents of exotic dishes fill the air. \nA violet sign marked “Mystyc Fortune” hangs audaciously over one of the stalls, written in an alluring script. You also spot a sparsely populated alleyway squashed between two buildings on the left side of the street.")
  //fortune teller
  private val fortuneTellerStop = Area("Fortune Teller Shop", "You duck into the fortune teller’s stall. A long line of people stretches to the door. \nThe thick scent of incense paired with the soft glow of purple candles casts a soft blanket over your mind.\nThe sign to your right says that you need to throw 2 coins into the jar underneath it if you want to talk to her.")
  private val fortuneTeller = NPC("Fortune Teller", "The light ting of a bell signals that it’s your turn. An assistant parts the drapes, and an old woman on a velvet pillow gestures for you to sit down. \nAs the curtain falls and you’re alone, she squints at you interrogatively. 'You look familiar, you’re Kaylina’s kid aren’t you? The fate spiders told me in their webs that you’d come.'\nYou nod slowly and she continues. 'I presume you must be looking for the photos, then. Unfortunately these are a rarity nowadays so I can’t just give them out for free.' \nYou told her that you have payed already and she responds 'Oh I know you paid, the voices told me about your intersting aura', before handing you a photo. 'The Hotel Owner should have the next one. Tell your mother I say hi if you see her, it’s been a long time.' \nYou exit and the drapes fall behind you.")
  private val photoTwo = Photo("asylum", "photo from the fortune teller", Pic("o1/adventure/photos/asylum.png"))
  //event plaza
  private val eventPlaza = Area("Event Plaza", "Sounds of hearty laughter fill the square as people enjoy the evening. Good company is the best source of happiness these days, so no one is on their phones here. To your right you see a weird looking cart with an rugged man crying hysterically behind it.")
  //oatgurt cart
  private val oatgurtCart = Area("Oatgurt Cart", "A rugged bearded man is crying hysterically behind the cart, tears dripping down into a vat of thick white stuff labelled 'OatGurt'. Ask him what's wrong?")
  private val oatgurtCartOwner = NPC("Oatgurt Cart Owner", "'I just can’t do it anymore… Things aren’t how they used to be.'\nHe says, dribbly sniveling while wiping away tears with his pinstripe sleeve.\n'The youngins don’t appreciate classic OatGurt these days. I tried switching up my recipe, but ever since my beloved Bella left I just haven’t had the talent for it. It’s been so hard to make ends meet… yOu’vE gOtTa heLp mE-' He exclaims, shoving the cart. You’re not sure what to do.")
  private val oatgurt = Item("oatgurt", "A true OatGurt that 'the youngins can't appreciate'. \nIt looks strange and the smell is unpleasant... You're starting to understand why the old man's business is falling apart.")
  //hidden alley
  private val hiddenAlley = Area("Hidden Alley", "The curtain parts, leading you into a hidden alley. Strings of lanterns light the way, illuminating the shapes of several people drunkenly making out against the walls. \nThe path opens up towards a hotel in the middle of a courtyard, where an inviting glow spills out from the windows.")
  private val cat1 = NPC("Tina the cat", "Tina paces around catiously. You try calling to her but she won't approach you.")
  private var cat1Occurence = 0
  private val photoThree = Photo("escapee", "a photo from tina the cat in the hiddden alley", Pic("o1/adventure/photos/escapee.png"))
  //love hotel
  //in front of love hotel
  private val beforeloveHotel = Area("in front of Love Hotel", "Something shiny and pink on the ground catches your eye. Maybe you should examine it before entering the hotel?")
  private val happyPills = Item("happy pills", "Several sparkly, pink pills lie in the dirt. Someone must have dropped them here. Maybe they could come in handy later?")
  private val security = NPC("Security Guard", "The security guard grunts in his sleep. A nametag on his jacket reads 'BieberLuv.' \nYou hope it’s some kind of an inside joke.")
  //reception desk
  private val loveHotelReception = Area("Love Hotel Reception Desk", "You walk down a luxurious hall towards a reception desk in the center. A respectable woman in a tightly laced suit stands behind it.")
  private val receptionist = NPC("Receptionist", "The woman addresses you. 'What can I help you with tonight? Would you be interested in a private room with some… special services? Or an opportunity to mingle? Whatever you’re looking for, I assure you we have it here.”\n You can’t afford to waste time right now, so you ask directly to see the hotel owner.\n'I’m afraid she’s quite busy right now, and doesn’t speak to just anyone. I would encourage you to consider our other options if you’re in a fix' she says, but you insist on seeing her by saying that she’s a close relative. \n'Oh! My apologies, what was your name again?' She asks, checking some things behind the counter. You tell her your name, and she nods briskly. 'My apologies, people ask to see the owner all the time when they shan’t… but you’re a different story. However, just so you know, you get only one chance to talk to het today. Don't waste it.'")
  private val bangle = Item("silver bangle", "The hotel receptionist gave it to you as a pass to see the owner.")
  //hotel owner office (rest on the bottom, bc it's dependent on the player)
  private val hotelOwnerOffice = Area("Manager's Office", "The doors open automatically, leading to an ornate spiral staircase. As you reach the top, you see a woman decked from head to toe in gold, sitting with a big red lizard perched on her shoulder behind a mahogany desk placed in the center of the room. You assume she must be the owner.")
  private val photoFour = Photo("blender buddies", "a photo from the hotel owner", Pic("o1/adventure/photos/blender buddies.png"))
  private var receptionistState = 0
  //love hotel bar
  private val loveHotelBar = Area("Love Hotel Bar", "You pass through some doors on the right. The noise overwhelms you as people crowd around, dancing and drinking to loud music. A bar can be seen to the right, where a beautiful woman is sitting that catches your eye.")
  private val drink = Item("drink", "A drink that's just begging for you to taste it. However, is it really a good time to be drinking right now?")
  private val prettyGirl = NPC("pretty girl", "You ask the girl for her name. ‘It’s Sammy, and you?’ She tucks her hair behind her hair as you answer. \nYou tell a joke about something stupid, and thank the gods internally for inheriting your mother’s talent, as Sammy giggles in response. You go back and forth for a while before she smiles, and says ‘You’re so funny~ can I give you something?’ ‘Of course’ you reply, and she leans in to give you a big smooch. As you kiss, she slides a slip of paper into your hand. ‘Call me?~’ she whispers in your ear, before pulling away and heading off into the crowd to join some friends.'")
  private val phoneNumber = Item("phone number", "A slip of paper with a pretty girl’s phone number. Xoxo is written in the margins.")
  //alleyway
  private val alleyway = Area("Alleyway", "You enter the dingy alleyway. An unresponsive man lies slumped against the wall, with two, tall crooked men whispering near him. It doesn’t seem like they’ve noticed you yet. \nLitter is scattered over the ground, spilling out from a dented, overflowing trashcan near the entrance.")
  private val trashcan = Area("Trashcan", "A dented can filled to the brim with trash. It spills out at every corner.")
  private val crookedMen = NPC("Crooked Men", "One of the men peers at you and motions to the other. They stop whispering and creep menacingly towards you. An uneasy feeling rises in your stomach and you start to back away, but it’s too late before one of them pulls out a metal pipe. \nYou run with all the power you have and hide behind one of the walls in the alley. The men ran past leaving you safe... for now. You stop hiding and see something sparkly on the ground. The men must have dropped it. Looking closer you see that it’s a knife! One side is engraved with a weird name. Is it safe?")
  private val knife = Item("knife", "A knife dropped by the crooked men. Are you sure it's safe to keep with you?")
  //coast
  private val coast = Area("Coast", "The dusky sky stretches out over a sandy shore. Not far off, an old rotted pier can be seen, where a small but sturdy boat is tied which gently rocks to the lull of the waves by the shore. You see someone sitting on a rock nearby, dully gazing at the water. \nOn the other side of the shore, two people are engrossed in a playful conversation, quietly laughing and drinking together. You think it best not to disturb them.\n A cat lays drowsily near a shed by the pier.")
  private val cat2 = NPC("Cat", "The dark gray cat stirs calmly, then opens one eye to peer at you. It is surprisingly comfortable with people, considering it has no collar. \nYou pet the cat and it responds. 'MEowrrr'")
  private val shoreman = NPC("man by the shore", "You approach the man by the shore. He’s scrawny, and even in the dark you can see him picking at his nails. In his hands he clutches a printed photo. You can see the faint outline of a couple numbers under his fingers but can’t make out what they are. \nYou ask to see the picture and he grips it tighter, speaking desperately. \n'I’m afraid I can’t do that… You don’t understand… no one ever understands, this is all I have left… I need SOMETHING to feel good…'")
  private val photoFive = Photo("horse photo", "a photo from man by the shore", Pic("o1/adventure/photos/horse photo.png"))
  private val Boat = Area("Boat", "The boat you need to take home! But have you collected everything you need already?")
  //end game place
  private val destination = Boat
  private val lastPhoto = Photo("full album", "a full album of all the photos you've collected", Pic("o1/adventure/photos/album.png"))
  //main street -> market square
  mainStreet.addItem(compas)
  mainStreet.setNeighbors(Vector("calendula apothecary" -> apothecaryStop, "harty's meat shop" -> meatMongerStop, "market square" -> marketSquare))
  apothecaryStop.setPeople(Vector("apothecary" -> apothecary))
  apothecaryStop.setNeighbors(Vector("niam street" -> mainStreet))
  meatMongerStop.setPeople(Vector("meat monger" -> meatMonger))
  meatMongerStop.setNeighbors(Vector("niam street" -> mainStreet))
  //market square -> event plaza / alleyway
  marketSquare.setNeighbors(Vector("fortune teller shop" -> fortuneTellerStop, "event plaza" -> eventPlaza, "alleyway" -> alleyway, "niam street" -> mainStreet))
  fortuneTellerStop.setPeople(Vector("fortune teller" -> fortuneTeller))
  fortuneTellerStop.setNeighbors(Vector("market square" -> marketSquare))
  //event plaza -> hidden alley / coast
  eventPlaza.setNeighbors(Vector("hidden alley" -> hiddenAlley, "oatgurt cart" -> oatgurtCart, "coast" -> coast, "market square" -> marketSquare))
  oatgurtCart.setNeighbors(Vector("event plaza" -> eventPlaza))
  oatgurtCart.setPeople(Vector("oatgurt cart owner" -> oatgurtCartOwner))
  //hidden alley -> love hotel
  hiddenAlley.setNeighbors(Vector("love hotel" -> beforeloveHotel, "event plaza" -> eventPlaza))
  //in front of love hotel
  beforeloveHotel.setNeighbors(Vector("love hotel" -> loveHotelReception, "hidden alley" -> hiddenAlley))
  beforeloveHotel.addItem(happyPills)
  beforeloveHotel.setPeople(Vector("security guard" -> security))
  //love hotel reception desk
  loveHotelReception.setNeighbors(Vector("hidden alley" -> hiddenAlley, "love hotel bar" -> loveHotelBar))
  loveHotelReception.setPeople(Vector("receptionist" -> receptionist))
  //love hotel bar
  loveHotelBar.setNeighbors(Vector("hidden alley" -> hiddenAlley))
  loveHotelBar.setPeople(Vector("pretty girl at the bar" -> prettyGirl))
  loveHotelBar.addItem(drink)
  //love hotel owner office
  hotelOwnerOffice.setNeighbors(Vector("reception desk" -> loveHotelReception))
  //alleyway -> X
  alleyway.setNeighbors(Vector("trashcan" -> trashcan, "market square" -> marketSquare))
  alleyway.setPeople(Vector("crooked men" -> crookedMen))
  trashcan.setNeighbors(Vector("alleyway" -> alleyway))
  //coast -> boat
  coast.setNeighbors(Vector("boat" -> Boat, "event plaza" -> eventPlaza))
  coast.setPeople(Vector("shoreman" -> shoreman))
  coast.setPeople(Vector("cat" -> cat2))
  //boat -> coast
  Boat.setNeighbors(Vector("coast" -> coast))

  val itemRewards = Map[String, Item](
    "meat monger" -> animalBones,
    "oatgurt cart owner" -> oatgurt,
    "pretty girl" -> phoneNumber,
    "crooked men" -> knife
  )

  val photoRewards = Map[String, Photo](
    "apothecary" -> photoOne,
    "fortune teller" -> photoTwo,
    "hotel owner" -> photoFour
  )


  val player = Player(mainStreet, itemRewards = itemRewards, photoRewards = photoRewards)

  //HOTEL OWNER
  private val hotelOwner = NPC("hotel owner", s"'Oh you, " + this.player.name + ", it’s been years!’ As you take a seat in front you wish to correct that you don’t know her, but she continues. ’I haven’t seen you since you were in daipers… I tried getting your mother to bring you here back then, but she was so adamant about keeping work and home life separate.’ \nYou ask if she knows anything about the photos, watching as the lizard lets out a big yawn. ‘Of course! You know she planned those a long time ago, but I swore I wouldn’t spoil much… your mother knows I like to talk. Let’s just say you’ll get all the knowledge you need from the pictures themselves’ \nShe slides you a glass case containing a golden photo, and tells you to be careful with it. ’I won’t keep you for long, but it’s nice to see you all grown up like this. Do pop back in if you ever have the time.’ You take the photo from the case.")
  hotelOwnerOffice.setPeople(Vector("hotel owner" -> hotelOwner))

  var turnCount = 0
  val timeLimit = 100

  private val allPhotoNames = List(
    "sentimental photo", "twin towers", "escapee", "blender buddies", "asylum", "horse photo")

  def checkGameStatus: String =
    this.player.photoStatus(allPhotoNames)

  def necessaryPhotos: Boolean =
    this.player.hasPhoto(photoOne.name)
    && this.player.hasPhoto(photoTwo.name)
    && this.player.hasPhoto(photoThree.name)
    && this.player.hasPhoto(photoFour.name)
    && this.player.hasPhoto(photoFive.name)
    && this.player.hasPhoto(originalPhoto.name)

  def isComplete =
    val atDestination = this.player.location == this.destination
    val hasPhotos = this.necessaryPhotos
    atDestination && hasPhotos

  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  def updateGameWorld(): Unit =
    if this.player.location == Boat then
      if this.necessaryPhotos then
        Boat.description = "The boat you need to take home! You have everything. Time to sail!"
      else
        Boat.description = "The boat you need to take home! You are still missing the necessary photos to go back. You should go back to the Coast."
    if cat1Occurence < 1 && player.has("animal bones") then
      hiddenAlley.setPeople(Vector("cat" -> cat1))
      cat1Occurence += 1
      hiddenAlley.description = hiddenAlley.description +
        "\n\nYou see a cat crouched next to a stairpost by the entrance, and recognize her as Tina from the meat shop. She looks scared."

  var isInitialized = false
  def initializePlayerWithName(nameInput: String): String =
    this.player.name = nameInput
    this.player.get(compas.name)
    this.player.album.addPhoto(originalPhoto)
    this.player.photoTracker += (originalPhoto.name -> originalPhoto)
    this.isInitialized = true
    s"You look down onto the photo your mother had left you and you see a tiny text on the back that reads: 'To my lovely ${this.player.name}'. You decide to start looking around.\n\n" +
    player.help() + "\n\n" +
    s"${player.album.viewPhoto(originalPhoto.name)}"

  def welcomeMessage =
    "It’s August 9th, and you’re just outside of the Famous Golden Night Market on the west side of the city Deezz, where memes have been illegal for the past 6 and a half years. Back then, along with the shattering news, came the mysterious disappearance of your mother who absconded in the night without saying goodbye. \nAll she left behind was a single photo in an otherwise empty album, marked with a date and a peculiar place." +
    "\nNow, that day has come, and it is time to uncover the truth of what happened to her by collecting 5 lost photos. \nYou have 10 gold dabloons with you, spend them carefully!\n" +
    "\nWhat is your name? "

  def goodbyeMessage: String =
    if this.isComplete then
      s"You found enough photos to find your mom. Now it's time to set sail. Well done! \n" + player.album.viewPhoto(lastPhoto.name)
    else if this.player.currentLocation == Boat && !necessaryPhotos then
      "You found the boat! But you still haven't found all the photos. Go back and look for them before it's too late!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else
      "Quitter!"

  def playTurn(command: String): String =
    val action = Action(command)
    println(checkGameStatus)
    val inputWords = command.toLowerCase.trim.split("\\s+")
    if inputWords.head == "talk" then
      // CAT TINA
      if inputWords.lift(1).contains("tina") && !player.hasClaimedCatReward && player.currentLocation == this.hiddenAlley then
        if hiddenAlley.contains("animal bones") then
          hiddenAlley.removeItem("animal bones")
          player.grantCatPhotoReward(photoThree)
          val baseDescription = hiddenAlley.description.split("You see a cat crouched").head.trim
          hiddenAlley.description = baseDescription
          this.updateGameWorld()
          this.turnCount += 1
          return s"Tina looks satisfied and immediately starts chewing on the ${"animal bones"} you dropped. \nOnce she’s finished, she lets out a satisfied mew and runs off down the alley. You look where she was sitting and see a photo laying on the ground.\n*You pick up the photo* \n${player.album.viewPhoto(photoThree.name)}"
        else
          return s"Tina stares at you suspiciously. She doesn't seem interested in talking. Isn't that the cat you saw at the meat monger shop before? \nMaybe you should try dropping the bones here for her like he asked you to?"

      //SHOREMAN
      else if inputWords.lift(1).contains("man") && !player.hasClaimedShoremanReward && player.currentLocation == this.coast then
        if player.has("happy pills") then
          player.drop("happy pills")
          player.grantShoremanPhotoReward(photoFive)
          this.updateGameWorld()
          this.turnCount += 1
          coast.removeItem("happy pills")
          return shoreman.dialogue + s"He inhales sharply. \n'Wait, that aura. You have the pills. GIVE THEM TO ME.' He shouts, vibrating. He lunges at you, causing the happy pills to fall from your pocket as you flinch away. He quickly scoops up the pills from the ground and swallows them all, dropping the photo in the process. He looks dizzy for a moment, before falling over in a trance. \n*You pick up the photo* \n${player.album.viewPhoto(photoFive.name)}"
        else
          return shoreman.dialogue + s"\nHe lunges at you but is too weak to catch hold as you flinch away. He grovels quietly on the ground before pushing himself back onto the rock and staring at the waves again, the photo still in his hands. Maybe you could trade him something for it?"

      // RECEPTIONIST
      else if inputWords.lift(1).contains("receptionist") && player.currentLocation == this.loveHotelReception then
         if receptionistState == 0 then
           receptionistState = 1
           player.get("silver bangle")
           player.currentLocation = hotelOwnerOffice
           this.updateGameWorld()
           this.turnCount += 1
           return receptionist.dialogue + s"\n'Ah, here it is!' The receptionist smiles, sliding a solid silver bangle across the desk towards you. \n*She gives you the ${bangle.name}!* \nShe then gestures toward a hidden door. 'The owner will see you now.' \n\n[You are now in the Hotel Owner's Office]"
         else
           this.turnCount += 1
           return "The receptionist is busy filing papers and flashes you a pleasant smile. She seems to have finished her business for today."

    val result = action.execute(this.player)
    if result.isDefined then
      this.updateGameWorld()
      this.turnCount += 1
    result.getOrElse(s"""Unknown command: "$command".""")
end Adventure

