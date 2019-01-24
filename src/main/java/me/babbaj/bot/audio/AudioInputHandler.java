package me.babbaj.bot.audio;

import java.util.function.Consumer;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.UserAudio;

public class AudioInputHandler implements AudioReceiveHandler {

  private Consumer<CombinedAudio> onAudioReceived;

  public AudioInputHandler(Consumer<CombinedAudio> onAudioReceived) {
    this.onAudioReceived = onAudioReceived;
  }

  @Override
  public boolean canReceiveCombined() {
    return true;
  }

  @Override
  public boolean canReceiveUser() {
    return false;
  }

  @Override
  public void handleCombinedAudio(CombinedAudio combinedAudio) {
    onAudioReceived.accept(combinedAudio);
  }

  @Override
  public void handleUserAudio(UserAudio userAudio) {
    throw new UnsupportedOperationException("handleUserAudio");
  }
}
