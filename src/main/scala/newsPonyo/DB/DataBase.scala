package newsPonyo.DB

import com.typesafe.config.ConfigFactory
import org.mongodb.scala._

object DataBase {

  def connectDB(): MongoClient = {
    val DB = ConfigFactory
      .load()
      .getString("DB")
    MongoClient(DB)
  }
}
