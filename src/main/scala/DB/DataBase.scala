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
    def connectDB(): MongoClient = {
        val DB = ConfigFactory.load()
            .getString("DB")
        MongoClient(DB)
    }
}
