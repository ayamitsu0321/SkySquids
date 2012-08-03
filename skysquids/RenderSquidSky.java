package skysquids;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelSquid;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSquidSky extends RenderLiving
{
    public RenderSquidSky()
    {
        super(new ModelSquid(), 0.5F);
    }

    public void func_21008_a(EntitySquidSky var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void func_21007_a(EntitySquidSky var1, float var2, float var3, float var4)
    {
        float var5 = var1.field_21088_b + (var1.field_21089_a - var1.field_21088_b) * var4;
        float var6 = var1.field_21086_f + (var1.field_21087_c - var1.field_21086_f) * var4;
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glRotatef(180.0F - var3, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var5, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.2F, 0.0F);
    }

    protected void func_21005_a(EntitySquidSky var1, float var2) {}

    protected float handleRotationFloat(EntitySquidSky var1, float var2)
    {
        float var3 = var1.lastTentacleAngle + (var1.tentacleAngle - var1.lastTentacleAngle) * var2;
        return var3;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.func_21005_a((EntitySquidSky)var1, var2);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving var1, float var2)
    {
        return this.handleRotationFloat((EntitySquidSky)var1, var2);
    }

    protected void rotateCorpse(EntityLiving var1, float var2, float var3, float var4)
    {
        this.func_21007_a((EntitySquidSky)var1, var2, var3, var4);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_21008_a((EntitySquidSky)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_21008_a((EntitySquidSky)var1, var2, var4, var6, var8, var9);
    }
}
