package o1.adventure.game

import o1.adventure.game.Photo

import scala.collection.mutable.Map

class PhotoAlbum:
  private val photos = Map[String, Photo]() 

  def addPhoto(newPhoto: Photo): Unit =
    this.photos += (newPhoto.name -> newPhoto)

  def listPhotos: String =
    if this.photos.isEmpty then
      "Your photo album is currently empty."
    else
      val photoNames = this.photos.keys.mkString("\n")
      s"Photo Album:\n${photoNames}"

  def viewPhoto(photoName: String): String =
    this.photos.get(photoName) match
      case Some(photo) => photo.use()
      case None        => s"You do not have a photo named '$photoName' in your album."

  def photoCount: Int = this.photos.size

end PhotoAlbum
