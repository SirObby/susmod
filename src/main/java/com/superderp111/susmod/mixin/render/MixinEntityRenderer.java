package com.superderp111.susmod.mixin.render;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.server.SusModServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity> {

    @Inject(at = @At("HEAD"), method = "renderLabelIfPresent", cancellable = true)
    public void renderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo callbackInfo) {
        if(entity instanceof PlayerEntity) {
            if(SusMod.otherSus != null) {
                if (SusModServer.hasUser(entity.getName().getString())) {
                    text = Text.of(SusMod.prefix).copy().append(text.copy());
                }
            }
        }
        double d = MinecraftClient.getInstance().getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0D)) {
            boolean bl = !entity.isSneaky();
            float f = entity.getHeight() + 0.5F;
            int i = "deadmau5".equals(text.getString()) ? -10 : 0;
            matrices.push();
            matrices.translate(0.0D, (double)f, 0.0D);
            matrices.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float h = (float)(-textRenderer.getWidth((StringVisitable)text) / 2);
            textRenderer.draw(text, h, (float)i, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
            if (bl) {
                textRenderer.draw((Text)text, h, (float)i, -1, false, matrix4f, vertexConsumers, false, 0, light);
            }

            matrices.pop();
        }
        callbackInfo.cancel();
    }
}
