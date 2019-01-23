package me.babbaj.bot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class Utils {

  public static Guild getGuild(String name) {
    return Main.jda.getGuilds()
        .stream()
        .filter(g -> g.getName().equals(name))
        .findFirst()
        .orElse(null);
  }

  public static TextChannel getTextChannel(String name, Guild guild) {
    return guild.getTextChannels()
        .stream()
        .filter(channel -> channel.getName().equals(name))
        .findFirst()
        .orElse(null);
  }


}