package newsPonyo

import newsPonyo.DB.DataBase
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal
import org.javacord.api.entity.channel.TextChannel

object DeleteNews extends Command {
  override val commandName: String = "delete"

  def variation(
      channel: TextChannel,
      string: Array[String]
    ): Either[Faild, Unit] = {
    string.length match {
      case 0 => Left(Faild(channel, "引数不足"))
      case 1 => Right({})
      case _ => Left(Faild(channel, "引数過度"))
    }
  }

  override def command(event: MessageCreateEvent): Either[Faild, Unit] = {
    val args = event.getMessage.getContent
      .split(" ")
    val client = DataBase.connectDB()
    val database = client.getDatabase("News")

    val coll = database.getCollection("News")

    variation(event.getChannel(), args) match {
      case Left(x) => {
        client.close()
        Left(x)
      }
      case Right(_) =>
        Right(
            coll
              .deleteOne(
                  equal(
                      "_id",
                      new ObjectId(
                          args
                            .apply(1)
                      )
                  )
              )
              .subscribe(_ -> {
                client.close()
                event.getChannel
                  .sendMessage("Success delete news")
              })
        )

    }

  }

  override val help: String =
    "p!delete [ID] :: 記事を削除できます。\n使用例 [p!delete 605b5a305ca96d318892173ef]"
}
