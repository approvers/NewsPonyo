package newsPonyo.DB

import newsPonyo.SendNews
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
    def newsQuety(client: MongoClient, coll: MongoCollection[Document], count: Int, channel: TextChannel): Unit = {
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
                    SendNews.send(result, channel)

                case Failure(e) =>
                    println(s"error: ${
                        e
                            .toString
                    }")
                    client.close()
            }
    }
}