package newsPonyo

import org.javacord.api.event.message.MessageCreateEvent

object Help extends Command {
  override val commandName: String = "help"

  override def command(event: MessageCreateEvent): Either[String, Unit] = {
    val helpWord = s"```asciidoc\n" +
      Help.help + "\n" +
      AddNews.help + "\n" +
      DeleteNews.help + "\n" +
      "```"

    Right(
        event.getChannel
          .sendMessage(helpWord)
    )
  }

  override val help: String = "p!help :: これ"
}
