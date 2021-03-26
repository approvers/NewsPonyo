package newsPonyo

import org.javacord.api.DiscordApi

import java.text.SimpleDateFormat
import java.util.{Date, Timer, TimerTask}

class PublishTask(client: DiscordApi) {
  val timer = new Timer(false)

  def getNowTimeInt: Int = {
    new SimpleDateFormat("HH").format(new Date()).toInt
  }

  val task: TimerTask = new TimerTask {
    override def run(): Unit = {
      getNowTimeInt match {
        case 0 | 1 | 2 | 3 | 4 | 6 | 10 | 14 | 18 | 20 | 21 | 22 | 23 =>
          val channel = client.getTextChannelById("690909527461199922")
          if (channel.isPresent) {
            SendNews.command(channel.get())
          }
        case _ =>
      }
    }
  }
  timer.schedule(task, 0, 1000 * 3600)
}
