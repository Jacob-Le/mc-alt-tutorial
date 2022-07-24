package net.jacob6.alttutorial.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModVignetteOverlay  {
    protected static final ResourceLocation INVENTORY_TO_MATRIX_LOCATION = new ResourceLocation("alttutorial:textures/gui/inventory_to_matrix_vignette.png");
    protected static final ResourceLocation INVENTORY_TO_INVENTORY_LOCATION = new ResourceLocation("alttutorial:textures/gui/inventory_to_inventory_vignette.png");
    protected static final ResourceLocation CRAFTING_TO_MATRIX_LOCATION = new ResourceLocation("alttutorial:textures/gui/crafting_to_matrix_vignette.png");
    protected static final ResourceLocation CRAFTING_TO_INVENTORY_LOCATION = new ResourceLocation("alttutorial:textures/gui/crafting_to_inventory_vignette.png");

    private static final Float opacity = 0.7f;
    private static final int textureSize = 256;

    private static VignetteMode vignetteMode = VignetteMode.NONE;

    public enum VignetteMode{
        NONE(-1),
        INVENTORY_TO_MATRIX(0),
        INVENTORY_TO_INVENTORY(1),
        CRAFTING_TO_MATRIX(2),
        CRAFTING_TO_INVENTORY(3);

        private final int id;

        VignetteMode(int id){
            this.id = id;
        }
    } 

    public static void doRender(int x, int y, int width, int height){
        if(vignetteMode == VignetteMode.NONE){
            return;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, opacity);

        switch(vignetteMode){
            case INVENTORY_TO_MATRIX:
                RenderSystem.setShaderTexture(0, INVENTORY_TO_MATRIX_LOCATION);
                break;
            case INVENTORY_TO_INVENTORY:
                RenderSystem.setShaderTexture(0, INVENTORY_TO_INVENTORY_LOCATION);
                break;
            case CRAFTING_TO_MATRIX:
                RenderSystem.setShaderTexture(0, CRAFTING_TO_MATRIX_LOCATION);
                break;
            case CRAFTING_TO_INVENTORY:
                RenderSystem.setShaderTexture(0, CRAFTING_TO_MATRIX_LOCATION);
                break;
            default:
                RenderSystem.setShaderTexture(0, INVENTORY_TO_MATRIX_LOCATION);
                break;
        }
        
        Tesselator tesselator = Tesselator.getInstance();

        // Do scaling
        double scaledX = (double) x;
        double scaledY = (double) y;
        double scaledWidth = (double) textureSize;
        double scaledHeight = (double) textureSize;

        // Do a blit - Determine UV coordinates of the texture
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(scaledX, scaledY + scaledHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        builder.vertex(scaledX + 256, scaledY + scaledHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        builder.vertex(scaledX + scaledWidth, scaledY, -90.0D).uv(1.0F, 0.0F).endVertex();
        builder.vertex(scaledX, scaledY, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();

        // Reset
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }

    public static void setVignetteMode(VignetteMode mode){
        vignetteMode = mode;
    }
    
}
