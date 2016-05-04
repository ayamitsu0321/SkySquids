package ayamitsu.skysquids.client;

import ayamitsu.skysquids.AbstractProxy;

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
