import org.javacord.api.event.message.MessageCreateEvent

object CommandSelector {
    def apply(commandName: String, event: MessageCreateEvent): Either[String, Unit] = {
        val command = commandName.split(" ")
        command.apply(0) match {
            case "ping" => Ping.command(event)
            case AddNews.commandName => AddNews.command(event)
            case SendNews.commandName => SendNews.command(event)
            case _ => Right({})
        }
    }
}
