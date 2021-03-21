import org.javacord.api.event.message.MessageCreateEvent

object CommandSelector {
  def apply(commandName: String, event: MessageCreateEvent): Either[String, Unit] = {
    commandName match {
      case "ping" => Ping.command(event)
      case "oppai" => AddNews.command(event)
    }
  }
}
