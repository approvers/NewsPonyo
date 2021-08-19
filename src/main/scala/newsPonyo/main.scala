package newsPonyo

import com.typesafe.config.ConfigFactory
import org.javacord.api.{DiscordApi, DiscordApiBuilder}

object Main extends App {
  val botMain = new BotMain
  botMain.run()
}

class BotMain {

  def run(): Unit = {
    val TOKEN = ConfigFactory
      .load()
      .getString("TOKEN")
    val client: DiscordApi = new DiscordApiBuilder()
      .setToken(TOKEN)
      .login
      .join

    client.addMessageCreateListener(event => {
      if (event.getMessageContent
            .take(2) == "p!") {
        CommandSelector(
            event.getMessageContent
              .drop(2),
            event
        ) match {
          case Left(value) => value.channel.sendMessage(value.message)
          case Right(_)    =>
        }
      }
    })

    new PublishTask(client)

  }
}
