package newsPonyo

import com.typesafe.config.ConfigFactory
import newsPonyo.DB.{DataBase, Query}
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.mongodb.scala.{Document, MongoDatabase}
import scalaj.http.Http

import java.awt.Color

object SendNews extends Event {
  override val commandName: String = "send"

  def variation(
      channel: TextChannel,
      string: Array[String]
    ): Either[Faild, Unit] = {
    string.length match {
      case 1 => Right({})
      case _ => Left(Faild(channel, "引数過度"))
    }
  }

  override def command(channel: TextChannel): Either[Faild, Unit] = {
    val client = DataBase.connectDB()
    val database: MongoDatabase = client.getDatabase("News")

    val coll = database.getCollection("News")

    coll
      .estimatedDocumentCount()
      .subscribe(x => {
        Query.newsQuery(client, coll, x.toInt, channel)
        println(x.toInt)
      })
    Right(
        {}
    )
  }

  def sendDiscord(
      result: Document,
      channel: TextChannel
    ): Unit = {
    val message = new EmbedBuilder()
      .setAuthor("時刊 ぽにょニュース")
      .setTitle(
          result
            .get("title")
            .get
            .asString()
            .getValue
      )
      .setDescription(s"${result
        .get("name")
        .get
        .asString()
        .getValue} ${result
        .get("_id")
        .get
        .asObjectId()
        .getValue
        .toString}")
      .setColor(Color.GREEN)
    channel
      .sendMessage(message)
  }

  def sendMastodon(result: Document): Unit = {
    val TOKEN = ConfigFactory
      .load()
      .getString("MASTODON")

    val news = s"【時刊ぽにょニュース】\n${result.get("title").get.asString().getValue}"

    Http("https://mastodon.approvers.dev/api/v1/statuses")
      .postForm(
          Seq("status" -> news)
      )
      .header(
          "Authorization",
          TOKEN
      )
      .asString
  }
}
