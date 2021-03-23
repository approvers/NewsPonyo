package newsPonyo

import newsPonyo.DB.{DataBase, Insert}
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.MongoDatabase

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object AddNews extends Command {
    override val commandName: String = "add"

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        val args: Array[String] = event.getMessage
            .getContent
            .split(" ")

        args
            .length match {
            case 1 => return Left("引数不足")
            case 2 | 3 =>
            case _ => return Left("引数過度")
        }

        val title = args.apply(1)

        val name: String = args
            .length match {
            case 3 => args.apply(2)
            case _ => event
                .getMessage
                .getAuthor
                .getDisplayName
        }

        val client = DataBase.connectDB()
        val database: MongoDatabase = client.getDatabase("News")

        val coll = database.getCollection("News")

        Await.result(Insert.addNews(coll, name, title), Duration
            .Inf)

        Right(event.getChannel
            .sendMessage("Success add new news"))
    }
}
