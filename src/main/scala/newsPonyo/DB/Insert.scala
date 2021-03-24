package newsPonyo.DB

import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala.{Document, MongoCollection}

import scala.concurrent.Future

object Insert {
    def addNews(coll: MongoCollection[Document], name: String, title: String): Future[InsertOneResult] = {
            val doc1: Document = Document("name" -> name, "title" -> title)

        coll.insertOne(doc1)
            .toFuture()

    }
}
