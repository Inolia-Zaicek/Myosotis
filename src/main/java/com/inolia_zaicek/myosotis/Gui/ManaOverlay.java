package com.inolia_zaicek.myosotis.Gui;

/*
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;      // 用于获取自定义属性（如最大法力值）
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;          // 识别包含法术的物品接口
import io.redspace.ironsspellbooks.config.ClientConfigs;                // 客户端配置读取（位置、显示模式等）
import io.redspace.ironsspellbooks.item.CastingItem;                    // 表示施法物品的类
import io.redspace.ironsspellbooks.player.ClientMagicData;              // 用于获取客户端玩家的魔法相关数据（当前法力值）
 */
import com.inolia_zaicek.myosotis.Item.CasterWeapon;
import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.ChatFormatting;                                   // 字符串颜色格式
import net.minecraft.client.Minecraft;                                 // Minecraft 客户端主类
import net.minecraft.client.gui.GuiGraphics;                           // GUI 绘制辅助类
import net.minecraft.client.player.LocalPlayer;                        // 客户端玩家实体
import net.minecraft.resources.ResourceLocation;                      // 资源定位符（纹理路径等）
import net.minecraft.world.entity.ai.attributes.Attribute;            // 实体属性类
import net.minecraft.world.entity.player.Player;                      // 玩家类
import net.minecraftforge.client.gui.overlay.ForgeGui;                  // Forge Mod Loader的HUD GUI类，管理HUD坐标等
import net.minecraftforge.client.gui.overlay.IGuiOverlay;               // Forge HUD 渲染接口，实现该接口可添加HUD覆盖层

import static com.inolia_zaicek.myosotis.Event.TickEvent.manaNumber;

/**
 * 法力值条HUD渲染类，负责在客户端屏幕上绘制法力值条和数值
 */
public class ManaOverlay implements IGuiOverlay {
    // 单例对象，用于注册渲染Overlay
    public static final ManaOverlay instance = new ManaOverlay();

    // 存放法力条纹理资源路径
    public static final ResourceLocation TEXTURE = new ResourceLocation("myosotis", "textures/gui/mana.png");

    // 常量：默认法力条宽度（普通），经验条锚点法力条宽度，法力条高度，热键栏高度等用于布局
    static final int DEFAULT_IMAGE_WIDTH = 98;
    static final int XP_IMAGE_WIDTH = 188;
    static final int IMAGE_HEIGHT = 21;
    static final int HOTBAR_HEIGHT = 25;
    static final int ICON_ROW_HEIGHT = 11;
    static final int CHAR_WIDTH = 6;              // 字符宽度，计算文字居中用
    static final int HUNGER_BAR_OFFSET = 50;      // 饥饿条位置偏移，用于联合定位
    static final int SCREEN_BORDER_MARGIN = 20;   // 屏幕边界距离，一般留空白不绘制文字等

    static final int TEXT_COLOR;                   // 文字颜色，初始化在static块中

    /**
     * 绘制法力条方法。每帧调用，负责计算位置、绘制背景和填充的法力条，及可选文本
     * @param gui Forge的HUD GUI对象，管理HUD状态及字体
     * @param guiHelper 辅助绘制对象，封装绘图API
     * @param partialTick 帧间隔时间，暂未用到
     * @param screenWidth 屏幕宽度
     * @param screenHeight 屏幕高度
     */
    public void render(ForgeGui gui, GuiGraphics guiHelper, float partialTick, int screenWidth, int screenHeight) {
        // 获取当前客户端玩家实体
        LocalPlayer player = Minecraft.getInstance().player;

        // 判断是否满足显示法力条条件（非观察者模式、配置允许显示、满足其他条件）
        if (player != null && shouldShowManaBar(player)) {
            // 获取玩家的最大法力值（自定义属性）

            // 获取玩家当前法力值（客户端同步的魔法数据）
            int nowMana = Minecraft.getInstance().player.getPersistentData().getInt(manaNumber);
            int maxMana = (int) player.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
            int manaRegan = (int) player.getAttributeValue((Attribute) MyAttributesRegister.MamaRegan.get());

            // 从配置读取法力条的偏移量（允许玩家调整显示位置）
            int configOffsetY = -10;
            int configOffsetX = -225;

            // 读取当前法力条显示锚点位置（屏幕上的哪个位置绘制法力条）
            Anchor anchor = Anchor.Hunger;

            // 如果锚点为经验条旁，且玩家正处于飞翔状态（跳跃骑乘等），则不绘制，用于避免和经验条冲突
            if (anchor != Anchor.XP || !(player.getJumpRidingScale() > 0.0F)) {
                // 计算显示坐标X,Y，结合锚点和偏移量
                int barX = getBarX(anchor, screenWidth) + configOffsetX;
                int barY = getBarY(anchor, screenHeight, gui) - configOffsetY;

                // 根据锚点选择法力条宽度（XP模式更宽）
                int imageWidth = anchor == Anchor.XP ? 188 : 98;

                // 纹理中法力条区域坐标（纹理图中的法力条切片位置）
                int spriteX = anchor == Anchor.XP ? 68 : 0;
                int spriteY = anchor == Anchor.XP ? 40 : 0;

                // 绘制法力条的背景（空白条）
                guiHelper.blit(TEXTURE, barX, barY, (float) spriteX, (float) spriteY, imageWidth, 21, 256, 256);

                // 绘制法力值填充部分，宽度根据 当前法力/最大法力 * 法力条宽度 比例动态缩放，最大不能超过法力条宽度
                guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + 21, (int) (imageWidth * Math.min((double) nowMana / maxMana, 1.0)), 21);

                // 构造显示的法力值文本，例如 "34/100"
                String manaFraction = nowMana + "/" + maxMana;

                // 计算文字X坐标，基于法力条的中点减去文字宽度的一半（字符个数乘字符宽度），并加上配置偏移
                int textX = (Integer) 24 + barX + imageWidth / 2 - (int) (((double) (manaFraction.length()) + 0.5) * CHAR_WIDTH);

                // 计算文字Y坐标，基于法力条Y加上不同锚点下不同的偏移值
                int textY = (Integer) 0 + barY + (anchor == Anchor.XP ? 3 : 11);

                // 如果配置允许显示法力文字，则绘制文字，颜色为预设的天蓝色
                if ((Boolean) (1>0) ) {
                    guiHelper.drawString(gui.getFont(), manaFraction, textX, textY, TEXT_COLOR);
                }
            }
        }
    }

    /**
     *  判断是否应该显示法力条（根据当前玩家状态和配置）
     * @param player 当前玩家实体
     * @return 是否显示法力条
     */
    public static boolean shouldShowManaBar(Player player) {
        // 从客户端配置读取法力条显示模式（Never, Always, Contextual）
        Display display = Display.Contextual;

        // 显示条件说明：
        // 1. 不是观察者模式
        // 2. 显示模式不是 Never
        // 3. 满足下面任意之一则显示：
        //    - 显示模式是 Always（总显示）
        //    - 玩家手上持有可施法的物品（CastingItem 或 法术容器且不必装备）
        //    - 当前法力值小于最大法力值（即法力值没满）
        int nowMana = player.getPersistentData().getInt(manaNumber);
        int maxMana = (int) player.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
        return !player.isSpectator() &&
                display != ManaOverlay.Display.Never &&
                (
                        display == ManaOverlay.Display.Always || MyUtil.hasHandImplements(player, CasterWeapon.class)
                        || nowMana<maxMana
                                /*
                                player.isHolding(itemStack ->

                                        itemStack.getItem() instanceof CastingItem ||      // 持有施法物品
                                                (ISpellContainer.isSpellContainer(itemStack) && !ISpellContainer.get(itemStack).mustEquip())  // 持有不限需装备的法术容器
                                ) ||
                                ClientMagicData.getPlayerMana() < player.getAttributeValue((Attribute)AttributeRegistry.MAX_MANA.get()) // 法力不满
                                         */
                );
    }

    /**
     * 根据锚点和屏幕宽度，计算法力条绘制的X坐标
     * @param anchor 显示锚点
     * @param screenWidth 屏幕宽度
     * @return X坐标
     */
    private static int getBarX(Anchor anchor, int screenWidth) {
        if (anchor == ManaOverlay.Anchor.XP) {
            // 如果锚点在经验（XP）条旁，放到屏幕正中央略偏左位置
            return screenWidth / 2 - 91 - 3;
        } else if (anchor != ManaOverlay.Anchor.Hunger && anchor != ManaOverlay.Anchor.Center) {
            // 如果锚点非饥饿条和中心，左右边距加载不同处理
            // 如果不是左上或左下，则放到右边距偏左的位置，否则放左边距偏右点的位置
            return anchor != ManaOverlay.Anchor.TopLeft && anchor != ManaOverlay.Anchor.BottomLeft ? screenWidth - 20 - 98 : 20;
        } else {
            // 饥饿条/中心锚点，放到屏幕宽度中间偏左或正中
            return screenWidth / 2 - 49 + (anchor == ManaOverlay.Anchor.Center ? 0 : 50);
        }
    }

    /**
     * 根据锚点和屏幕高度，计算法力条绘制的Y坐标
     * @param anchor 显示锚点
     * @param screenHeight 屏幕高度
     * @param gui 当前Forge GUI，用于访问rightHeight变量（右侧堆叠高度）
     * @return Y坐标
     */
    private static int getBarY(Anchor anchor, int screenHeight, ForgeGui gui) {
        if (anchor == ManaOverlay.Anchor.XP) {
            // 经验条锚点，放在屏幕底部稍上位置
            return screenHeight - 32 + 3 - 8;
        } else if (anchor == ManaOverlay.Anchor.Hunger) {
            // 饥饿条锚点，结合rightHeight堆叠高度动态计算（避免遮挡）
            return screenHeight - (getAndIncrementRightHeight(gui) - 2) - 10;
        } else if (anchor == ManaOverlay.Anchor.Center) {
            // 屏幕中心锚点，放在底部和经验条上方
            return screenHeight - 25 - 27 - 10;
        } else {
            // 左上/右上锚点靠屏幕顶端，否则放在底部偏下位置
            return anchor != ManaOverlay.Anchor.TopLeft && anchor != ManaOverlay.Anchor.TopRight ? screenHeight - 20 - 21 : 20;
        }
    }

    /**
     * 递增控制的右侧高度计数器，供多HUD层叠时使用，避免遮挡
     * @param gui 当前Forge GUI，用于修改rightHeight属性
     * @return 当前rightHeight数值
     */
    private static int getAndIncrementRightHeight(ForgeGui gui) {
        int x = gui.rightHeight;    // 读取当前累积高度
        gui.rightHeight += 10;      // 预留10像素空间用于堆叠下一个元素
        return x;
    }

    // 静态代码块初始化文字颜色为天蓝色
    static {
        TEXT_COLOR = ChatFormatting.AQUA.getColor();
    }

    /**
     * HUD显示位置锚点枚举，定义法力条可摆放的多种预设位置
     */
    public static enum Anchor {
        Hunger,       // 饥饿条旁
        XP,           // 经验条旁
        Center,       // 屏幕底部居中
        TopLeft,      // 左上角
        TopRight,     // 右上角
        BottomLeft,   // 左下角
        BottomRight;  // 右下角
    }

    /**
     * HUD显示模式枚举，控制法力条显示的不同条件
     */
    public static enum Display {
        Never,       // 从不显示
        Always,      // 始终显示
        Contextual;  // 情景显示（如手持施法物品或法力不满时显示）
    }
}