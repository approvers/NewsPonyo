package newsPonyo

import newsPonyo.DB.{DataBase, Insert}
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.result.InsertOneResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
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

        Insert
            .addNews(coll, name, title).onComplete{
            case Success(_) =>
                client.close()
            case Failure(exception) =>
                return Left(exception.toString)
        }


        Right(event.getChannel
            .sendMessage("Success add new news"))

    }
}
