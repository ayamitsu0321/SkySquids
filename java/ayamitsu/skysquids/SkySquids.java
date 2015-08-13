package ayamitsu.skysquids;

import ayamitsu.skysquids.entity.EntitySquidSky;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by ayamitsu0321 on 2015/04/05.
 */
@Mod(
        modid  = SkySquids.MODID,
        name   = SkySquids.NAME,
        version = SkySquids.VERSION
)
public class SkySquids {

    /**
     * くｺ:彡
     */

    public static final String MODID ="skysquids";
    public static final String NAME = "SkySquids";
    public static final String VERSION = "1.1.0";

    @Mod.Instance(MODID)
    public static SkySquids instance;

    @SidedProxy(clientSide = "ayamitsu.skysquids.client.ClientProxy", serverSide = "ayamitsu.skysquids.server.ServerProxy")
    public static AbstractProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //EntityRegistry.registerGlobalEntityID(EntitySquidSky.class, "SkySquid", 233, 0x000000, 0x000000);
        EntityRegistry.registerModEntity(EntitySquidSky.class, "SkySquid", 0, this, 64, 3, true);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySquidSky.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntityRegistry.addSpawn(EntitySquidSky.class, 10, 4, 4, EnumCreatureType.MONSTER,
                BiomeGenBase.ocean,
                BiomeGenBase.deepOcean,
                BiomeGenBase.beach,
                BiomeGenBase.forest,
                BiomeGenBase.forestHills,
                BiomeGenBase.birchForest,
                BiomeGenBase.birchForestHills,
                BiomeGenBase.extremeHills,
                BiomeGenBase.extremeHillsEdge,
                BiomeGenBase.extremeHillsPlus,
                BiomeGenBase.jungle,
                BiomeGenBase.jungleEdge,
                BiomeGenBase.jungleHills,
                BiomeGenBase.river,
                BiomeGenBase.plains,
                BiomeGenBase.taiga,
                BiomeGenBase.taigaHills,
                BiomeGenBase.megaTaiga,
                BiomeGenBase.megaTaigaHills,
                BiomeGenBase.savanna,
                BiomeGenBase.savannaPlateau
        );
        this.proxy.preInit();
    }

}
