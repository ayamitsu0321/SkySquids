package ayamitsu.skysquids;

import ayamitsu.skysquids.entity.EntitySquidSky;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by ayamitsu0321 on 2015/04/05.
 */
@Mod(
        modid = SkySquids.MODID,
        name = SkySquids.NAME,
        version = SkySquids.VERSION
)
public class SkySquids {

    /**
     * くｺ:彡
     */

    public static final String MODID = "skysquids";
    public static final String NAME = "SkySquids";
    public static final String VERSION = "1.1.1";

    @Mod.Instance(MODID)
    public static SkySquids instance;

    @SidedProxy(clientSide = "ayamitsu.skysquids.client.ClientProxy", serverSide = "ayamitsu.skysquids.server.ServerProxy")
    public static AbstractProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //EntityRegistry.registerGlobalEntityID(EntitySquidSky.class, "skysquids.SkySquid", 233, 0x000000, 0x000000);
        EntityRegistry.registerModEntity(EntitySquidSky.class, "SkySquid", 0, this, 64, 3, true, 0x000000, 0x000000);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySquidSky.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntityRegistry.addSpawn(EntitySquidSky.class, 10, 4, 4, EnumCreatureType.MONSTER,
                Biomes.OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.BEACH,
                Biomes.FOREST,
                Biomes.FOREST_HILLS,
                Biomes.BIRCH_FOREST,
                Biomes.BIRCH_FOREST_HILLS,
                Biomes.EXTREME_HILLS,
                Biomes.EXTREME_HILLS_EDGE,
                Biomes.EXTREME_HILLS_WITH_TREES,
                Biomes.JUNGLE,
                Biomes.JUNGLE_EDGE,
                Biomes.JUNGLE_HILLS,
                Biomes.RIVER,
                Biomes.PLAINS,
                Biomes.TAIGA,
                Biomes.TAIGA_HILLS,
                Biomes.MUTATED_TAIGA,
                Biomes.REDWOOD_TAIGA_HILLS,
                Biomes.SAVANNA,
                Biomes.SAVANNA_PLATEAU
        );
        this.proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.proxy.postInit();
    }

}
