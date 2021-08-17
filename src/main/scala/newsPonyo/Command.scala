package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent

trait Command {
  val commandName: String

  def command(event: MessageCreateEvent): Either[Faild, Unit]

  val help: String
}
