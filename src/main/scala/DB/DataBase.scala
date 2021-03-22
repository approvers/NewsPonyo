package DB

import com.typesafe.config.ConfigFactory
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object DataBase {
    val DB = ConfigFactory.load().getString("DB")
    val mongoClient: MongoClient = MongoClient(DB)

    val database: MongoDatabase = mongoClient.getDatabase("test")

    val coll: MongoCollection[Document] = database.getCollection("test")

    /*val doc: Document = Document(
        "_id" -> 0,
        "name" -> "MongoDB",
        "type" -> "database",
        "count" -> 1,
        "info" -> Document("x" -> 203, "y" -> 102))*/

    //coll.insertOne(doc)
    val doc1: Document = Document("name" -> "MongoDB", "type" -> "database", "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))
    coll.insertOne(doc1)
        .toFuture()
    Query.quety(mongoClient, coll)
}
