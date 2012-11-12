package net.minecraft.src;

import skysquids.*;
import java.util.Map;

public class mod_SkySquids extends BaseMod
{
	/**
	 * くｺ:彡
	 */
	
	@MLProp(info = "enable tame")
	public static boolean canTame = false;
	
	@MLProp(info = "eanble tame with IKATORITame")
	public static boolean canTameWith = true;
	
	@MLProp(info = "spawn limit")
	public static int spawnLimit = 50;
	
	@MLProp(info = "spawn rate")
	public static int spawnRate = 10;
	
	@MLProp(info = "entity id")
	public static int id = 112;
	
	public String getVersion()
	{
		return "1.4.4_v1.0.8";
	}
	
	@Override
	public void load()
	{
		this.addNames();
	}
	
	@Override
	public void modsLoaded()
	{
		this.addEntities();
	}
	
	@Override
	public void addRenderer(Map map)
	{
		map.put(EntitySquidSky.class, new RenderSquidSky());
	}
	
	private void addEntities()
	{
		Class clazz;
		
		if (ModLoader.isModLoaded("mod_IKATORITame"))
		{
			clazz = this.canTameWith ? EntitySquidSkyTame.class : EntitySquidSky.class;
		}
		else
		{
			clazz = this.canTame ? EntitySquidSkyTame.class : EntitySquidSky.class;
		}
		
		ModLoader.registerEntityID(clazz, "SkySquid", this.id, 0x223b4d, 0x708899);
		ModLoader.addSpawn(clazz, this.spawnRate, 1, 3, EnumCreatureType.monster);
	}
	
	private void addNames()
	{
		this.addName("SkySquid", "en_US", "SkySquid");
		this.addName("SkySquid", "ja_JP", "イカフライ");
	}
	
	public void addName(Object obj, String lang, String name)
	{
		String entityName = "entity." + obj + "name";
		ModLoader.addLocalization(entityName, lang, name);
	}
}