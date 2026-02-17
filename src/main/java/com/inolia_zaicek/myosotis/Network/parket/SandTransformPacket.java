// com.inolia_zaicek.mine_duel.network.parket.SandTransformPacket
package com.inolia_zaicek.myosotis.Network.parket;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class SandTransformPacket {
    private BlockPos pos;
    public SandTransformPacket(BlockPos pos) {
        this.pos = pos;
    }
    public static void encode(SandTransformPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }
    public static SandTransformPacket decode(FriendlyByteBuf buf) {
        return new SandTransformPacket(buf.readBlockPos());
    }
    public static void handle(SandTransformPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                ServerLevel world = (ServerLevel) player.level();
                BlockPos pos = msg.pos;
                BlockState suspiciousSandState = Blocks.SUSPICIOUS_SAND.defaultBlockState();
                world.setBlockAndUpdate(pos, suspiciousSandState);
                if (world.getBlockEntity(pos) != null) {
                    CompoundTag tag = new CompoundTag();
                    tag.putString("LootTable", "minecraft:archaeology/desert_pyramid");
                    // 视具体实现而定，某些 BlockEntity 的 set 方法可能不同
                    Objects.requireNonNull(world.getBlockEntity(pos)).load(tag);
                }
                world.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        });
        ctx.setPacketHandled(true);
    }
}