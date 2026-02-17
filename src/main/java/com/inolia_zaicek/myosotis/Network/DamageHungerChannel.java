package com.inolia_zaicek.myosotis.Network;

import com.inolia_zaicek.myosotis.Network.parket.GetServerManaToClientPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class DamageHungerChannel {
    private static final String PROTOCOL_VERSION = "1.0";

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("myosotis", "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, GetServerManaToClientPacket.class,
                GetServerManaToClientPacket::encode,
                GetServerManaToClientPacket::decode,
                GetServerManaToClientPacket::handle
        );
        INSTANCE.registerMessage(id++, com.inolia_zaicek.myosotis.Network.parket.SandTransformPacket.class,
                com.inolia_zaicek.myosotis.Network.parket.SandTransformPacket::encode,
                com.inolia_zaicek.myosotis.Network.parket.SandTransformPacket::decode,
                com.inolia_zaicek.myosotis.Network.parket.SandTransformPacket::handle
        );
    }
}