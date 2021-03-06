package net.gliby.voicechat.common.networking.voiceservers.udp;

import com.google.common.io.ByteArrayDataOutput;

public class UDPClientVoicePacket extends UDPPacket {

    byte[] samples;
    byte divider;


    public UDPClientVoicePacket(byte divider, byte[] samples) {
        this.samples = samples;
        this.divider = divider;
    }

    @Override
    public byte id() {
        return (byte) 1;
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        UDPByteUtilities.writeBytes(this.samples, out);
        out.writeByte(this.divider);
    }
}
