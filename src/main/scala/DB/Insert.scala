package DB

import org.mongodb.scala.{Document, MongoClient, MongoCollection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Insert {
    def addNews(coll: MongoCollection[Document], name: String, title: String): Future[Unit] = {
        val doc1: Document = Document("name" -> name, "title" -> title)
        Future(coll.insertOne(doc1)
            .toFuture())
    }
}
