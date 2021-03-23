import DB.{DataBase, Query}
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.MongoDatabase

object SendNews extends Event {
    override val commandName: String = "send"

    override def command(channel: TextChannel): Either[String, Unit] = {
        val client = DataBase.connectDB()
        val database: MongoDatabase = client.getDatabase("News")

        val coll = database.getCollection("News")

        coll.estimatedDocumentCount()
            .subscribe(x => {
                Query.quety(client, coll, x
                    .toInt, channel)
                println(x.toInt)
            }
            )
        Right(
            {}
        )
    }
}
