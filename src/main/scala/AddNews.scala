import org.javacord.api.event.message.MessageCreateEvent
object AddNews extends Command {
  override val commandName: String = AddNews.getClass.getName

  override def command(event: MessageCreateEvent): Either[String, Unit] = {
    event.getChannel.sendMessage("AddNews")
    Left("予期せぬエラー")
  }
}
