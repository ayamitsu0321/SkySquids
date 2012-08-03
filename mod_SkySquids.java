package net.minecraft.src;

import java.util.Map;
import java.util.List;
import skysquids.*;

public class mod_SkySquids extends BaseMod
{
	/*
	* くコ:彡 
	*/
	@MLProp(info="Enable SkySquid Tame")
	public static boolean canTame = false;//通常時のみ使用
	@MLProp(info="Enable SkySquid Tame with IKATORITame")
	public static boolean canTameWith = true;//IKATORITameが導入してあるときのみ使用
	@MLProp(info="Spawn Limit")
	public static int spawnLimit = 50;//ロードチャンク中の湧き上限
	@MLProp(info="Spawn Rate")
	public static int spawnRate = 10;//湧き率

    public String getVersion()
    {
        return "1.3.1_v1.0.7";
    }

    public void load()
    {
        this.addEntities();
        this.changeName();
    }

    private void addEntities()
    {
    	Class class1;
    	//IKATORITameのMODの有無
    	if (ModLoader.isModLoaded("mod_IKATORITame")) {
    		if (canTameWith) {
    			class1 = EntitySquidSkyTame.class;
    		} else {
    			class1 = EntitySquidSky.class;
    		}
    	} else if (canTame) {
    		class1 = EntitySquidSkyTame.class;
    	} else {
    		class1 = EntitySquidSky.class;
    	}
    	ModLoader.registerEntityID(class1, "SkySquid", ModLoader.getUniqueEntityId(), 0x223b4d, 0x708899);
    	ModLoader.addSpawn(class1, spawnRate, 1, 3, EnumCreatureType.monster);
    }

    private void changeName()
    {
        this.addName("SkySquid", "en_US", "SkySquid");
        this.addName("SkySquid", "ja_JP", "イカフライ");
    }

    public void addRenderer(Map map)
    {
    	Class class1;
    	if (ModLoader.isModLoaded("mod_IKATORITame")) {
    		if (canTameWith) {
    			class1 = EntitySquidSkyTame.class;
    		} else {
    			class1 = EntitySquidSky.class;
    		}
    	} else if (canTame) {
    		class1 = EntitySquidSkyTame.class;
    	} else {
    		class1 = EntitySquidSky.class;
    	}
        map.put(class1, new RenderSquidSky());
    }

    public void addName(Object obj, String string, String string1)
    {
        String string2 = "entity." + obj + ".name";
        ModLoader.addLocalization(string2, string, string1);
    }
}
