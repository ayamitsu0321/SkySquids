package skysquids;

import java.util.List;
import net.minecraft.src.*;

public class EntitySquidSkyTame extends EntitySquidSky {
	//from EntitySquidTame
	public boolean tamed;
	
	public EntitySquidSkyTame(World world) {
		super(world);
//		System.out.println("tamedSquid.");
	}

	@Override
	public int getMaxHealth() {
    	// 最大HP
		return tamed ? 20 : super.getMaxHealth();
	}
	
    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Tamed", tamed);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        tamed = nbttagcompound.getBoolean("Tamed");
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
    	if (!worldObj.isRemote) {
        	return squidInteract(entityplayer);
    	} else {
    		return super.interact(entityplayer);
    	}
    }
    
    @Override
    public void onLivingUpdate() {
    	if (!worldObj.isRemote) {
        	squidOnLivingUpdate();
    	} else {
    		super.onLivingUpdate();
    	}
    }
    
    @Override
    protected void updateEntityActionState() {
    	if (!worldObj.isRemote) {
        	squidUpdateEntityActionState();
    	} else {
    		super.updateEntityActionState();
    	}
    }    
    
    
    // 追加分
    @Override
    protected boolean isAIEnabled() {
    	return false;
    }
    
    @Override
    protected boolean canDespawn() {
    	return isTamed() ? false : super.canDespawn();
    }

    @Override
    public double getYOffset() {
        if(ridingEntity != null) {
            if(ridingEntity instanceof EntityPlayer) {
                return (double)(yOffset - 1.0F);
            }
            if(ridingEntity instanceof EntityChicken) {
                return (double)(yOffset - 0.05F);
            }
            if(ridingEntity instanceof EntitySquid || ridingEntity instanceof EntitySquidSky) {
                return (double)(yOffset + 0.28F);
            }
        }
        return super.getYOffset();
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset();
    }
    
    

    public void showLoveOrHappyFX(boolean flag) {
        if(flag) {
    		// Love
            for(int i = 0; i < 7; i++)
            {
                double d1 = rand.nextGaussian() * 1.0D;
                double d3 = rand.nextGaussian() * 1.0D;
                double d5 = rand.nextGaussian() * 1.0D;
                worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5);
            }
        } else {
    		// Happy
            double d = rand.nextGaussian() * 1.0D;
            double d2 = rand.nextGaussian() * 1.0D;
            double d4 = rand.nextGaussian() * 1.0D;
            worldObj.spawnParticle("note", posX, posY + (double)height + 0.10000000000000001D, posZ, d, d2, d4);
        }
    }

    // 飼いならし
    public boolean isTamed() {
        return tamed;
    }

    public void setTamed(boolean flag) {
        tamed = flag;
    }

    public boolean eatFish() {
    	// 魚を食べて回復
    	if (attackTime > 0 || health > getMaxHealth()) {
    		return false;
    	}
        heal(((ItemFood)Item.fishRaw).getHealAmount());
        worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        attackTime = 15;
        return true;
    }

    public boolean squidInteract(EntityPlayer entityplayer) {
    	// 触った時の判定
        if(entityplayer != null)
        {
            if(entityplayer.getCurrentEquippedItem() == null && isTamed())
            {
    			// 無手
				// RIDE-OFF
                if(entityplayer.ridingEntity == this)
                {
                    entityplayer.mountEntity(this);
                    return true;
                }
    			// RIDE-ON
                Entity entity1 = entityplayer;
                if(entity1.riddenByEntity != this)
                {
                    while (entity1.riddenByEntity != null) {
                    	entity1 = entity1.riddenByEntity;
                    }
                }
                mountEntity(entity1);
                return true;
            }
            ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if(itemstack != null && itemstack.stackSize > 0)
            {
    			// 装備品
                if(itemstack.itemID == Item.fishRaw.shiftedIndex && eatFish())
                {
                    if(--itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    if(isTamed())
                    {
                        showLoveOrHappyFX(false);
                    } else
                    {
                        setTamed(true);
                        showLoveOrHappyFX(true);
                    }
                    return true;
                }
                if(isTamed() && itemstack.itemID == Item.dyePowder.shiftedIndex && itemstack.getItemDamage() == 0)
                {
        			// RIDE-ON
        			// イカに乗る
                	if (entityplayer.riddenByEntity == this)//プレイヤーに乗ってるイカなら
                	{
                		this.mountEntity(null);
                	}
                    if(--itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    entityplayer.mountEntity(this);
                    return true;
                }
            }
        }
        return super.interact(entityplayer);
    }

    public void squidOnLivingUpdate() {
    	super.onLivingUpdate();
    	if (isInWater()) {
            // 追記：何かに乗っている時は縦置き
            if(ridingEntity == null) {
                field_21089_a += (double)(-90F - field_21089_a) * 0.02D;
            } else {
                rotationYaw = ridingEntity.rotationYaw;
                field_21089_a = 0.0F;
                onGround = ridingEntity.onGround;
            }
    	}
    }

    public void squidUpdateEntityActionState() {
        Entity entity = null;
    	// 魚の探索
        double d = -1D;
        List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem.class, boundingBox.copy().expand(16D, 16D, 16D));
        if(list != null) {
            for(int i = 0; i < list.size(); i++) {
                Entity entity1 = (Entity)list.get(i);
                if(!entity1.isDead && (entity1 instanceof EntityItem) && ((EntityItem)entity1).item.itemID == Item.fishRaw.shiftedIndex) {
                    double d2 = getDistanceSqToEntity(entity1);
                    if(d == -1D || d2 < d) {
                        d = d2;
                        entity = entity1;
                    }
                }
            }

            if(entity != null && d < 1.0D && ((EntityItem)entity).delayBeforeCanPickup <= 0 && eatFish()) {
            	// アイテムに接触
            	if (--((EntityItem)entity).item.stackSize <= 0) {
                	entity.setDead();
            	}
                entity = null;
            }
        }
        if(entity == null && isTamed()) {
            double d1 = -1D;
            List list1 = worldObj.getLoadedEntityList();
            for(int j = 0; j < list1.size(); j++) {
                Entity entity2 = (Entity)list1.get(j);
                if((entity2 instanceof EntityPlayer)) {
                    ItemStack itemstack = ((EntityPlayer)entity2).getCurrentEquippedItem();
                    if(itemstack != null && itemstack.itemID == Item.fishRaw.shiftedIndex) {
                		// 水の中の魚を持ったプレーヤーに向かう
                        double d3 = getDistanceSqToEntity(entity2);
                        if(d1 == -1D || d3 < d1)
                        {
                            d1 = d3;
                            entity = entity2;
                        }
                    }
                }
            }

        }
        if(entity != null) {
        	try {
        		float randomMotionSpeed = (Float)ModLoader.getPrivateValue(EntitySquidSky.class, this, 8);
            	if(randomMotionSpeed == 1.0F) {
            		float randomMotionVecX;
            		float randomMotionVecY;
            		float randomMotionVecZ;
            		
                	// 魚に向かってごー
                    double d4 = entity.posX - posX;
                    double d5 = entity.posY - posY - (double)entity.getEyeHeight();
                    double d6 = entity.posZ - posZ;
                    double d7 = MathHelper.sqrt_double(d4 * d4 + d5 * d5 + d6 * d6);
                    if(!worldObj.getBlockMaterial(MathHelper.floor_double(posX + d4 / d7), MathHelper.floor_double(posY), MathHelper.floor_double(posZ + d6 / d7)).isSolid()) {
                        randomMotionVecX = (float)(d4 / d7) * 0.2F;
                        randomMotionVecY = (float)(d5 / d7) * 0.2F;
                        randomMotionVecZ = (float)(d6 / d7) * 0.2F;
                    } else {
                		// 障害物が有る時は上か下に回避
                        randomMotionVecX = 0.0F;
                        randomMotionVecY = d5 <= 0.0D ? -0.2F : 0.2F;
                        randomMotionVecZ = 0.0F;
                    }
            		ModLoader.setPrivateValue(EntitySquidSky.class, this, 11, Float.valueOf(randomMotionVecX));
            		ModLoader.setPrivateValue(EntitySquidSky.class, this, 12, Float.valueOf(randomMotionVecY));
            		ModLoader.setPrivateValue(EntitySquidSky.class, this, 13, Float.valueOf(randomMotionVecZ));
                }
        	}
        	catch (Exception e) {
        	}
            entityAge++;
            despawnEntity();
        } else {
        	super.updateEntityActionState();
        }
    }
}