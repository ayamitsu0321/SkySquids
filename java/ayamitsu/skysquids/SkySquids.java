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
        EntityRegistry.registerModEntity(EntitySquidSky.class, "skysquids.SkySquid", 0, this, 64, 3, true);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySquidSky.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntityRegistry.addSpawn(EntitySquidSky.class, 10, 4, 4, EnumCreatureType.MONSTER,
                Biomes.ocean,
                Biomes.deepOcean,
                Biomes.beach,
                Biomes.forest,
                Biomes.forestHills,
                Biomes.birchForest,
                Biomes.birchForestHills,
                Biomes.extremeHills,
                Biomes.extremeHillsEdge,
                Biomes.extremeHillsPlus,
                Biomes.jungle,
                Biomes.jungleEdge,
                Biomes.jungleHills,
                Biomes.river,
                Biomes.plains,
                Biomes.taiga,
                Biomes.taigaHills,
                Biomes.megaTaiga,
                Biomes.megaTaigaHills,
                Biomes.savanna,
                Biomes.savannaPlateau
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
