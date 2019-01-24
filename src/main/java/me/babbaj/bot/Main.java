package me.babbaj.bot;

import java.io.IOException;
import java.util.Objects;
import javax.security.auth.login.LoginException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.babbaj.bot.audio.AudioInputHandler;
import me.babbaj.bot.audio.AudioOutputHandler;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class Main {

  public static void main(String[] args) throws Exception {
    OptionParser optionParser = new OptionParser();
    OptionSpec helpSpec = optionParser.accepts("help").forHelp();

    OptionSpec<String> tokenSpecA = optionParser.accepts("token_A", "session token for channel A").withRequiredArg();
    OptionSpec<String> tokenSpecB = optionParser.accepts("token_B", "session token for channel B").withRequiredArg();

    OptionSpec<AccountType> accountTypeSpec = optionParser.accepts("account_type", "account type (BOT or CLIENT)")
        .withRequiredArg().ofType(AccountType.class);
    OptionSpec<Long> channelASpec = optionParser.accepts("channel_A", "server and channel id")
        .withRequiredArg().ofType(Long.class).withValuesSeparatedBy(';');
    OptionSpec<Long> channelBSpec = optionParser.accepts("channel_B", "server and channel id")
        .withRequiredArg().ofType(Long.class).withValuesSeparatedBy(';');
    final OptionSet optionSet = optionParser.parse(args);

    if (args.length == 0 || optionSet.has(helpSpec)) {
      try {
        optionParser.printHelpOn(System.out);
      } catch (IOException ex) {ex.printStackTrace();}
    }


    final JDA jdaA = login(optionSet.valueOf(tokenSpecA), optionSet.valueOf(accountTypeSpec), "A");
    final JDA jdaB = login(optionSet.valueOf(tokenSpecB), optionSet.valueOf(accountTypeSpec), "B");
    jdaA.awaitReady();
    jdaB.awaitReady();
    System.out.println("Ready");



    Guild guildA = jdaA.getGuildById(optionSet.valuesOf(channelASpec).get(0));
    VoiceChannel channelA = guildA.getVoiceChannelById(optionSet.valuesOf(channelASpec).get(1));
    Objects.requireNonNull(channelA);


    Guild guildB = jdaB.getGuildById(optionSet.valuesOf(channelBSpec).get(0));
    VoiceChannel channelB = guildB.getVoiceChannelById(optionSet.valuesOf(channelBSpec).get(1));
    Objects.requireNonNull(channelB, "channel B");


    AudioOutputHandler outputA = new AudioOutputHandler();
    AudioOutputHandler outputB = new AudioOutputHandler();

    guildA.getAudioManager().setSendingHandler(outputB);
    guildA.getAudioManager().setReceivingHandler(new AudioInputHandler(outputA::receivedAudio));

    guildB.getAudioManager().setSendingHandler(outputA);
    guildB.getAudioManager().setReceivingHandler(new AudioInputHandler(outputB::receivedAudio));

    guildA.getAudioManager().openAudioConnection(channelB);
    guildB.getAudioManager().openAudioConnection(channelA);


    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      guildA.getAudioManager().closeAudioConnection();
      guildB.getAudioManager().closeAudioConnection();
    }));
  }


  private static JDA login(String token, AccountType type, String name) throws LoginException {
    try {
      return new JDABuilder(type)
          .setToken(token)
          .setAutoReconnect(true)
          .setStatus(OnlineStatus.ONLINE)
          .build();
    } catch (LoginException ex) {
      throw new LoginException("Failed to log into " + name + ": " + ex.getMessage());
    }
  }

}
