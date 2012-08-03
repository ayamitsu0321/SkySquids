package net.minecraft.src;

import java.util.Map;
import java.util.List;
import skysquids.*;

public class mod_SkySquids extends BaseMod
{
	/*
	* ���R:�c 
	*/
	@MLProp(info="Enable SkySquid Tame")
	public static boolean canTame = false;//�ʏ펞�̂ݎg�p
	@MLProp(info="Enable SkySquid Tame with IKATORITame")
	public static boolean canTameWith = true;//IKATORITame���������Ă���Ƃ��̂ݎg�p
	@MLProp(info="Spawn Limit")
	public static int spawnLimit = 50;//���[�h�`�����N���̗N�����
	@MLProp(info="Spawn Rate")
	public static int spawnRate = 10;//�N����

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
    	//IKATORITame��MOD�̗L��
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
        this.addName("SkySquid", "ja_JP", "�C�J�t���C");
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
