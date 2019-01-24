package me.babbaj.bot.audio;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;

public class AudioOutputHandler implements AudioSendHandler {

  private CombinedAudio lastReceivedAudio;

  public void receivedAudio(CombinedAudio audio) {
    if (!audio.getUsers().isEmpty()) {
      this.lastReceivedAudio = audio;
    }
  }

  @Override
  public boolean canProvide() {
    return lastReceivedAudio != null;
  }

  @Override
  public byte[] provide20MsAudio() {
    byte[] out = lastReceivedAudio.getAudioData(1);
    this.lastReceivedAudio = null;
    //System.out.println(Arrays.toString(out));
    return out;
  }
}
