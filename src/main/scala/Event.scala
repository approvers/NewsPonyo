import org.javacord.api.entity.channel.TextChannel

trait Event {
    val commandName: String

    def command(channel: TextChannel): Either[String, Unit]
}
