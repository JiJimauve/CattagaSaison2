package fr.cattaga;

import com.mojang.authlib.GameProfile;
import com.sammy.namechanger1_20_1.nickname.NicknameService;
import com.sammy.namechanger1_20_1.client.NicknameClientState;
import net.minecraft.server.MinecraftServer;

public class NicknameConnector {
    public static String getPlayerNicknameOrDefault(MinecraftServer server, GameProfile gameProfile) {
        String senderNick = NicknameService.getNickname(server, gameProfile.getId());
        if (senderNick != null) {
            return senderNick;
        }
        return gameProfile.getName();
    }
    public static String getPlayerNicknameOrDefaultClient(GameProfile gameProfile) {
        return NicknameClientState.getNicknameOrRealName(gameProfile.getId(), gameProfile.getName());
    }
}
