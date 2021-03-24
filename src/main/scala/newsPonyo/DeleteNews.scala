package newsPonyo

import newsPonyo.DB.DataBase
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.model.Filters.equal

object DeleteNews extends Command {
    override val commandName: String = "delete"

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        val args = event.getMessage
            .getContent
            .split(" ")
        val client = DataBase.connectDB()
        val database = client.getDatabase("News")

        val coll = database.getCollection("News")

        Right(coll.deleteOne(equal("id", args
            .apply(1)
            .toInt))
            .subscribe(_ -> client.close()))
    }
}
