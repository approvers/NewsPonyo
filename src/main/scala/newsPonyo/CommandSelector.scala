package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent

object CommandSelector {

  def apply(
      commandName: String,
      event: MessageCreateEvent
    ): Either[Faild, Unit] = {
    val command = commandName.split(" ")
    command.apply(0) match {
      case "ping"                 => Ping.command(event)
      case AddNews.commandName    => AddNews.command(event)
      case SendNews.commandName   => SendNews.command(event.getChannel)
      case DeleteNews.commandName => DeleteNews.command(event)
      case Help.commandName       => Help.command(event)
      case _                      => Right({})
    }
  }
}
