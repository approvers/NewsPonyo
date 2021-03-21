import org.javacord.api.event.message.MessageCreateEvent

trait Command {
    val commandName: String

    def command(event: MessageCreateEvent): Either[String, Unit]
}
