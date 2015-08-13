package ayamitsu.skysquids.client;

import ayamitsu.skysquids.AbstractProxy;
import ayamitsu.skysquids.entity.EntitySquidSky;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Created by ayamitsu0321 on 2015/04/05.
 */
public class ClientProxy extends AbstractProxy {

    @Override
    public void preInit() {
        //RenderingRegistry.registerEntityRenderingHandler(EntitySquidSky.class, new RenderSquid(FMLClientHandler.instance().getClient().getRenderManager(), new ModelSquid(), 0.7F));
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }
}
