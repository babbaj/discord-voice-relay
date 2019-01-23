package me.babbaj.bot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.internal.JDAImpl;

public class Main {

  public static JDAImpl jda;

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: <token> <\"CLIENT\" or \"BOT\">");
      System.exit(1);
    }
    try {
      jda = (JDAImpl)new JDABuilder(AccountType.valueOf(args[1]))
          .setToken(args[0])
          .setAutoReconnect(true)
          .setStatus(OnlineStatus.ONLINE)
          .build();
      jda.awaitReady();
    } catch (LoginException | InterruptedException e) {
      e.printStackTrace();
      System.err.println("failed to initialize bot");
      System.exit(1);
    }








    System.out.println("Initialized bot");


  }
}
