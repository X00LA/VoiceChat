package net.gliby.voicechat.common.api.examples;

import net.gliby.voicechat.common.api.VoiceChatAPI;
import net.gliby.voicechat.common.api.events.ServerStreamEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ExampleStreamHandlerOnlyOP {

    public ExampleStreamHandlerOnlyOP() {
        VoiceChatAPI.instance().setCustomStreamHandler(this);
    }

    @SubscribeEvent
    public void createStream(ServerStreamEvent.StreamCreated event) {
        if (!this.isOP(event.stream.player)) {
            event.stream.player.sendMessage(new TextComponentString("Only OP\'s are allowed to talk!"));
        }

    }

    @SubscribeEvent
    public void feedStream(ServerStreamEvent.StreamFeed event) {
        List<EntityPlayerMP> players = event.stream.player.mcServer.getPlayerList().getPlayers();
        EntityPlayerMP speaker = event.stream.player;
        if (this.isOP(speaker)) {
            for (int i = 0; i < players.size(); ++i) {
                EntityPlayerMP player = players.get(i);
                if (this.isOP(player) && player.getEntityId() != speaker.getEntityId()) {
                    event.streamManager.feedStreamToPlayer(event.stream, event.voiceLet, player, false);
                }
            }
        }

    }

    public boolean isOP(EntityPlayerMP player) {
        return player.mcServer.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile()) != null;
    }
}
