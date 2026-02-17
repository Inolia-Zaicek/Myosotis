package com.inolia_zaicek.myosotis.Network.parket;

import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.inolia_zaicek.myosotis.Event.HurtEvent.shield_number;


public class GetServerShieldToClientPacket {
    public Map<ResourceLocation,Integer> baseValueMap = new HashMap<>();
    ///输入进来的数据
    public float shield;
    public GetServerShieldToClientPacket(float shield) {
        this.shield = shield;
    }
    public static void encode(GetServerShieldToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.shield);
    }
    public static GetServerShieldToClientPacket decode(FriendlyByteBuf buf) {
        float shield = buf.readFloat();
        return new GetServerShieldToClientPacket(shield);
    }
    public static void handle(GetServerShieldToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            //【这是服务端发客户端用的，因为ctx.getSender()是null；————————客户端发服务端用ctx.getSender()
            LocalPlayer livingEntity = Minecraft.getInstance().player;
            if (livingEntity != null) {
                float maxShield = (float) (livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxShield.get())
                        * livingEntity.getAttributeValue((Attribute) Attributes.MAX_HEALTH));
                livingEntity.getPersistentData().putFloat(shield_number,Math.min(maxShield,Math.max(msg.shield,0)) );
            }
        });
        ctx.setPacketHandled(true);
    }
}