package manueh.marvel.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import manueh.marvel.Main;
import manueh.marvel.common.entity.CaptainAmericaShieldEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

/**
 * Created by TGG on 24/08/2020.
 *
 * Used to render your BoomerangEntity
 */
public class CaptainAmericaShieldRenderer extends EntityRenderer<CaptainAmericaShieldEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/items/captain_america_shield.png");

    protected CaptainAmericaShieldRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public ResourceLocation getTextureLocation(CaptainAmericaShieldEntity entity) {
        return TEXTURE;
    }
}