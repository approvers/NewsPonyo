package DB

import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Document, MongoClient, MongoCollection}

import java.awt.Color
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Query {
    def quety(client: MongoClient, coll: MongoCollection[Document], count: Int, channel: TextChannel): Unit = {
        println(math
            .random() * 1000
            .toInt)
        val query = coll.find()
            .skip((math
                .random() * 1000)
                .toInt % count)
            .limit(1)
            .first()
            .toFuture()
        Await.ready(query, Duration
            .Inf)
            .onComplete {
                case Success(result) =>
                    coll.drop()
                    client.close()
                    val message = new EmbedBuilder().setAuthor("時刊 ぽにょニュース")
                        .setTitle(result
                            .get("title")
                            .get
                            .asString()
                            .getValue)
                        .setDescription(result
                            .get("name")
                            .get
                            .asString()
                            .getValue)
                        .setColor(Color
                            .GREEN)
                    channel
                        .sendMessage(message)
                case Failure(e) =>
                    println(s"error: ${
                        e
                            .toString
                    }")
                    client.close()
            }
    }
}
