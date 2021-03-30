package newsPonyo
import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.channel.server.voice.{
  ServerVoiceChannelMemberJoinEvent,
  ServerVoiceChannelMemberLeaveEvent
}

import java.awt.Color

object VcDiff {
  val commandName: String = "vcdiff"

  def join(
      event: ServerVoiceChannelMemberJoinEvent,
      client: DiscordApi
    ): Either[String, Unit] = {
    val message =
      new EmbedBuilder()
        .setTitle(s"${event.getUser
          .getDisplayName(client.getServerById("683939861539192860").get())}が${event.getChannel.getName}に入りました")
        .setDescription("何かが始まる予感がする。")
        .setColor(Color.BLUE)
        .setAuthor("ぽにょからのおしらせ", "", client.getOwner.get().getAvatar)
        .setThumbnail(event.getUser.getAvatar)
    Right(
        client
          .getTextChannelById("690909527461199922")
          .get()
          .sendMessage(message)
    )
  }

  def leave(
      event: ServerVoiceChannelMemberLeaveEvent,
      client: DiscordApi
    ): Either[String, Unit] = {
    val message =
      new EmbedBuilder()
        .setTitle(s"${event.getUser
          .getDisplayName(client.getServerById("683939861539192860").get())}が${event.getChannel.getName}から抜けました")
        .setDescription("あいつは良い奴だったよ。")
        .setColor(Color.BLUE)
        .setAuthor("ぽにょからのおしらせ", "", client.getOwner.get().getAvatar)
        .setThumbnail(event.getUser.getAvatar)
    Right(
        client
          .getTextChannelById("690909527461199922")
          .get()
          .sendMessage(message)
    )
  }
}
