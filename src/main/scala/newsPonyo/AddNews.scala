package newsPonyo

import newsPonyo.DB.{DataBase, Insert}
import org.javacord.api.event.message.MessageCreateEvent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object AddNews extends Command {
    override val commandName: String = "add"

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        val args = event.getMessage
            .getContent
            .split(" ")

        args
            .length match {
            case 1 => return Left("引数不足")
            case 2 | 3 =>
            case _ => return Left("引数過度")
        }

        val title = args.apply(1)

        val name = args
            .length match {
            case 3 => args.apply(2)
            case _ => event
                .getMessage
                .getAuthor
                .getDisplayName
        }

        val client = DataBase.connectDB()
        val database = client.getDatabase("News")

        val coll = database.getCollection("News")


        Right(coll.estimatedDocumentCount().subscribe(count => {
            val id = count.toInt + 1
            Insert
                .addNews(coll, name, title, id).onComplete{
                case Success(_) =>
                    client.close()
                    event.getChannel
                    .sendMessage(s"Success add new news \n This news id is $id")
                case Failure(exception) =>
                    return Left(exception.toString)
            }
        }))

    }
}
