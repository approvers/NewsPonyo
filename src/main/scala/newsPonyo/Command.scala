package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent
import newsPonyo.Faild._

trait Command {
  val commandName: String

  def command(event: MessageCreateEvent): Either[Faild, Unit]

  val help: String
}
