package newsPonyo

import org.javacord.api.entity.channel.TextChannel
import newsPonyo.Faild._

trait Event {
  val commandName: String

  def command(channel: TextChannel): Either[Faild, Unit]
}
