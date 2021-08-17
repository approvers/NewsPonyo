package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent
import newsPonyo.Faild._

object Ping extends Command {
  override val commandName: String = "ping"

  override def command(event: MessageCreateEvent): Either[Faild, Unit] = {
    Right(
        event.getChannel
          .sendMessage("pong")
    )
  }

  override val help: String = ""
}
