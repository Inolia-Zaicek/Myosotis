package com.inolia_zaicek.myosotis.Network.parket;

import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.inolia_zaicek.myosotis.Event.TickEvent.manaNumber;

public class GetServerManaToClientPacket {
    public Map<ResourceLocation,Integer> baseValueMap = new HashMap<>();
    ///输入进来的数据
    public float mana;
    public GetServerManaToClientPacket(float mana) {
        this.mana = mana;
    }
    public static void encode(GetServerManaToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.mana);
    }
    public static GetServerManaToClientPacket decode(FriendlyByteBuf buf) {
        float mana = buf.readFloat();
        return new GetServerManaToClientPacket(mana);
    }
    public static void handle(GetServerManaToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            //【这是服务端发客户端用的，因为ctx.getSender()是null；————————客户端发服务端用ctx.getSender()
            LocalPlayer livingEntity = Minecraft.getInstance().player;
            if (livingEntity != null) {
                float maxMana = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
                float mana = Math.min(maxMana,Math.max(msg.mana,0));
                livingEntity.getPersistentData().putFloat(manaNumber,mana );
            }
        });
        ctx.setPacketHandled(true);
    }
}