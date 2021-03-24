package newsPonyo.DB

import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala.{Document, MongoCollection}

import scala.concurrent.Future

object Insert {
    def addNews(coll: MongoCollection[Document], name: String, title: String, id: Int): Future[InsertOneResult] = {
            val doc1: Document = Document("id" -> id,"name" -> name, "title" -> title)

        coll.insertOne(doc1)
            .toFuture()

    }
}
