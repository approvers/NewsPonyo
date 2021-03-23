package newsPonyo

import com.typesafe.config.ConfigFactory
import org.javacord.api.{DiscordApi, DiscordApiBuilder}

import java.util.{Timer, TimerTask}

object Main extends App {
    val botMain = new BotMain
    botMain.run()
}

class BotMain {
    def run(): Unit = {
        val TOKEN = ConfigFactory.load()
            .getString("TOKEN")
        val client: DiscordApi = new DiscordApiBuilder()
            .setToken(TOKEN)
            .login
            .join

        client.addMessageCreateListener(event => {
            if (event.getMessageContent
                .take(2) == "p!") {
                CommandSelector(event
                    .getMessageContent
                    .drop(2), event)
                    .left
                    .foreach(println)
            }
        })

        val timer = new Timer(false)
        val task = new TimerTask {
            override def run(): Unit = {
                val channel = client.getTextChannelById("690909527461199922")
                if (channel.isPresent) {
                    SendNews.command(channel.get())
                }
            }
        }
        timer.schedule(task, 0, 1000 * 3600)
    }
}


