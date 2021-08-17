package newsPonyo

import newsPonyo.DB.{DataBase, Insert}
import org.javacord.api.event.message.MessageCreateEvent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import org.javacord.api.entity.channel.TextChannel

object AddNews extends Command {
  override val commandName: String = "add"

  def variation(
      channel: TextChannel,
      string: Array[String]
    ): Either[Faild, Unit] = {
    string.length match {
      case 1     => Left(Faild(channel, "引数不足"))
      case 2 | 3 => Right({})
      case _     => Left(Faild(channel, "引数過度"))
    }
  }

  def addNews(
      event: MessageCreateEvent,
      args: Array[String]
    ): Either[String, Unit] = {
    val title = args.apply(1)

    val name = args.length match {
      case 3 => args.apply(2)
      case _ => event.getMessage.getAuthor.getDisplayName
    }

    val client = DataBase.connectDB()
    val database = client.getDatabase("News")

    val coll = database.getCollection("News")

    Right(
        Insert
          .addNews(coll, name, title)
          .onComplete {
            case Success(result) =>
              client.close()
              event.getChannel
                .sendMessage(s"Success add new news \nThis news id is ${result.getInsertedId
                  .asObjectId()
                  .getValue
                  .toString}")
            case Failure(exception) =>
              Left(exception.toString)
          }
    )
  }

  override def command(event: MessageCreateEvent): Either[Faild, Unit] = {
    val args = event.getMessage.getContent
      .split(" ")
    if (variation(event.getChannel, args).isLeft)
      variation(event.getChannel, args)
    else Right(addNews(event, args))
  }

  override val help: String =
    "p!add [title] [name]? :: 記事を書けます。\n使用例 [p!add hoge huga]"
}
