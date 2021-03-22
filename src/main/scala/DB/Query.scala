package DB

import DB.DataBase.mongoClient
import org.mongodb.scala.bson.collection.immutable.Document.fromSpecific
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Document, MongoClient, MongoCollection}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Query {
    def quety(client: MongoClient, coll: MongoCollection[Document]): Unit = {
        val query = coll.find(equal("name", "MongoDB"))
            .first()
            .toFuture()
        Await.ready(query, Duration
            .Inf)
            .onComplete {
                case Success(result) =>
                    println(result
                        .get("name")
                        .get
                        .asString()
                        .getValue)
                    coll.drop()
                    client.close()
                case Failure(e) =>
                    println(s"error: ${
                        e
                            .toString
                    }")
                    client.close()
            }
    }
}
