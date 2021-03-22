import DB.{DataBase, Query}
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.MongoDatabase

object SendNews extends Command {
    override val commandName: String = "send"

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        val client = DataBase.connectDB()
        val database: MongoDatabase = client.getDatabase("News")

        val coll = database.getCollection("News")

        coll.estimatedDocumentCount()
            .subscribe(x => {
                Query.quety(client, coll, x
                    .toInt, event)
                println(x.toInt)
            }
            )
        Right(
            {}
        )
    }
}
