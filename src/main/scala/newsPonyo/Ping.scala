package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent

object Ping extends Command {
    override val commandName: String = "ping"

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        Right(event.getChannel
            .sendMessage("pong"))
    }
}
