package me.babbaj.bot.audio;

import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.UserAudio;

public class NullAudioHandler implements AudioReceiveHandler, AudioSendHandler {

  public static final NullAudioHandler INSTANCE = new NullAudioHandler();

  @Override
  public boolean canReceiveCombined() {
    return false;
  }

  @Override
  public boolean canReceiveUser() {
    return false;
  }

  @Override
  public void handleCombinedAudio(CombinedAudio combinedAudio) {

  }

  @Override
  public void handleUserAudio(UserAudio userAudio) {

  }

  @Override
  public boolean canProvide() {
    return false;
  }

  @Override
  public byte[] provide20MsAudio() {
    return null;
  }
}
