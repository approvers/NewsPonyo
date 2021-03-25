package newsPonyo.DB

import newsPonyo.SendNews
import org.javacord.api.entity.channel.TextChannel
import org.mongodb.scala.{Document, MongoClient, MongoCollection}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Query {
  def newsQuery(
      client: MongoClient,
      coll: MongoCollection[Document],
      count: Int,
      channel: TextChannel
  ): Unit = {
    val query = coll
      .find()
      .skip(
          (math
            .random() * 1000).toInt % count
      )
      .limit(1)
      .first()
      .toFuture()
    Await.ready(query, Duration.Inf).onComplete {

      case Success(result) =>
        coll.drop()
        client.close()
        SendNews.send(result, channel)

      case Failure(e) =>
        println(s"error: ${e.toString}")
        client.close()
    }
  }
}
