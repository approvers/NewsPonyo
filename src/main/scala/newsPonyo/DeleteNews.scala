package newsPonyo

import newsPonyo.DB.DataBase
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.bson.ObjectId
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

        Right(coll.deleteOne(equal("_id", new ObjectId(args
            .apply(1))
        ))
            .subscribe(_ -> {
                client.close()
                event.getChannel
                    .sendMessage("Success delete news")
            }))
    }

    override val help: String = "p!delete [ID] :: 記事を削除できます。\n使用例 [p!delete 605b5a305ca96d318892173ef]"
}
