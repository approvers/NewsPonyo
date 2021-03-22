import org.javacord.api.event.message.MessageCreateEvent

object AddNews extends Command {
    override val commandName: String = AddNews
        .getClass
        .getName

    override def command(event: MessageCreateEvent): Either[String, Unit] = {
        val args: Array[String] = event.getMessage
            .getContent
            .split(" ")
        if (args
            .length == 1) {
            return Left("引数不足")
        }

        Right(event.getChannel
            .sendMessage("AddNews"))
    }
}
