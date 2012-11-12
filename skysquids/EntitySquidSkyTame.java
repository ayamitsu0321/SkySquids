package skysquids;

import net.minecraft.src.*;
import java.util.List;

public class EntitySquidSkyTame extends EntitySquidSky
{
	public boolean tamed;
	
	public EntitySquidSkyTame(World world)
	{
		super(world);
	}
	
	@Override
	public int getMaxHealth()
	{
		return this.tamed ? 20 : super.getMaxHealth();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Tamed", this.tamed);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.tamed = nbt.getBoolean("Tamed");
	}
	
	/**
	 * 魚をあげてテイムする
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if (this.worldObj.isRemote)
		{
			return false;
		}
		
		if (player != null)
		{
			// 何ももっていない, テイム状態
			if (player.inventory.getCurrentItem() == null && this.isTamed())
			{
				if (player.ridingEntity == this)
				{
					// プレイヤーが降りる
					player.mountEntity(this);
					return true;
				}
				
				Entity entity1 = player;
				
				if (entity1.ridingEntity != this)
				{
					while (entity1.riddenByEntity != null)
					{
						entity1 = entity1.riddenByEntity;
					}
				}
				
				this.mountEntity(entity1);
				return true;
			}
			
			ItemStack is = player.inventory.getCurrentItem();
			
			if (is != null && is.stackSize > 0)
			{
				// 魚をもってるとき
				if (is.itemID == Item.fishRaw.shiftedIndex)
				{
					if (--is.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
					
					boolean flag = this.isTamed();
					
					if (!flag)
					{
						// テイム
						this.setTamed(true);
					}
					
					this.showParticleFX(flag);
					return true;
				}
				else if (this.isTamed() && is.itemID == Item.dyePowder.shiftedIndex && is.getItemDamage() == 0)
				{
					if (player.riddenByEntity == this)
					{
						this.mountEntity(null);
					}
					
					if (--is.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
					
					// 乗る
					player.mountEntity(this);
					return true;
				}
			}
		}
		
		return super.interact(player);
	}
	
	/**
	 * いろいろ
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.worldObj.isRemote)
		{
			return;
		}
		
		if (this.isInWater())
		{
			if (this.ridingEntity == null)
			{
				this.field_21089_a += (double)(-90F - this.field_21089_a) * 0.02D;
			}
			else
			{
				this.rotationYaw = this.ridingEntity.rotationYaw;
				this.field_21089_a = 0.0F;
				this.onGround = this.ridingEntity.onGround;
			}
		}
	}
	
	/**
	 * いろいろ行動
	 */
	@Override
	protected void updateEntityActionState()
	{
		if (this.worldObj.isRemote)
		{
			return;
		}
		
		Entity entity = this.getNearestEntityItem(Item.fishRaw.shiftedIndex);
		
		if (entity instanceof EntityItem && this.getDistanceSqToEntity(entity) < 1.0D && ((EntityItem)entity).delayBeforeCanPickup <= 0 && eatFish())
		{
			if (--((EntityItem)entity).item.stackSize <= 0)
			{
				entity.setDead();
			}
			
			entity = null;
		}
		
		if (entity == null && this.isTamed())
		{
			// 距離制限しつつ, プレイヤーを探索
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 16D);
			
			if (player != null)
			{
				ItemStack is = player.inventory.getCurrentItem();
				
				if (is != null && is.itemID == Item.fishRaw.shiftedIndex)
				{
					entity = player;
				}
			}
		}
		
		// 向かう
		if (entity != null)
		{
			if (this.randomMotionSpeed == 1.0F)
			{
				double var1 = entity.posX - this.posX;
				double var2 = entity.posY - this.posY - (double)entity.getEyeHeight();
				double var3 = entity.posZ - this.posZ;
				double var4 = MathHelper.sqrt_double(var1 * var1 + var2 * var2 + var3 * var3);
				
				// 向かう先のBlock
				if (!this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX + var1 / var4), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ + var3 / var4)).isSolid())
				{
					this.randomMotionVecX = (float)(var1 / var4) * 0.2F;
					this.randomMotionVecY = (float)(var2 / var4) * 0.2F;
					this.randomMotionVecZ = (float)(var3 / var4) * 0.2F;
				}
				else
				{
					this.randomMotionVecX = 0.0F;
					this.randomMotionVecY = var2 <= 0.0D ? -0.2F : 0.2F;
					this.randomMotionVecZ = 0.0F;
				}
			}
			
			this.entityAge++;
			this.despawnEntity();
		}
		else
		{
			super.updateEntityActionState();
		}
	}
	
	protected EntityItem getNearestEntityItem(int itemID)
	{
		List var1 = this.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem.class, this.boundingBox.copy().expand(16D, 16D, 16D));
		Entity var2 = null;
		double var3 = -1;
		
		if (var1 != null)
		{
			for (int i = 0; i < var1.size(); i++)
			{
				Entity var4 = (Entity)var1.get(i);
				
				if (!var4.isDead && var4 instanceof EntityItem && ((EntityItem)var4).item.itemID == itemID)
				{
					double var5 = this.getDistanceSqToEntity(var4);
					
					if (var3 == -1 || var5 < var3)
					{
						var5 = var3;
						var2 = var4;
					}
				}
			}
		}
		
		return (EntityItem)var2;
	}
	
	@Override
	protected boolean isAIEnabled()
	{
		return false;
	}
	
	@Override
	protected boolean canDespawn()
	{
		return this.isTamed() ? false : super.canDespawn();
	}
	
	@Override
	public double getYOffset()
	{
		if (this.ridingEntity != null)
		{
			if (this.ridingEntity instanceof EntityPlayer)
			{
				return (double)(this.yOffset - 1.0F);
			}
			else if (this.ridingEntity instanceof EntityChicken)
			{
				return (double)(this.yOffset - 0.05F);
			}
			else if (this.ridingEntity instanceof EntitySquid || this.ridingEntity instanceof EntitySquidSky)
			{
				return (double)(this.yOffset + 0.28F);
			}
		}
		
		return super.getYOffset();
	}
	
	@Override
	public double getMountedYOffset()
	{
		return super.getMountedYOffset();
	}
	
	public void showParticleFX(boolean flag)
	{
		/*if (!this.worldObj.isRemote)
		{
			return;
		}*/
		
		if (flag)
		{
			for (int i = 0; i < 7; i++)
			{
				double var1 = this.getRNG().nextGaussian() * 0.02D;
				double var2 = this.getRNG().nextGaussian() * 0.02D;
				double var3 = this.getRNG().nextGaussian() * 0.02D;
				this.worldObj.spawnParticle("heart", (this.posX + (double)(this.getRNG().nextFloat() * this.width * 2.0F)) - (double)this.width, this.posY + 0.5D + (double)(this.getRNG().nextFloat() * this.height), (this.posZ + (double)(this.getRNG().nextFloat() * this.width * 2.0F)) - (double)this.width, var1, var2, var3);
			}
		}
		else
		{
			double var1 = this.getRNG().nextGaussian() * 1.0D;
			double var2 = this.getRNG().nextGaussian() * 1.0D;
			double var3 = this.getRNG().nextGaussian() * 1.0D;
			this.worldObj.spawnParticle("note", this.posX, this.posY + (double)this.height + 0.10000000000000001D, this.posZ, var1, var2, var3);
		}
	}
	
	public boolean isTamed()
	{
		return this.tamed;
	}
	
	public void setTamed(boolean flag)
	{
		this.tamed = flag;
	}
	
	public boolean eatFish()
	{
		if (this.attackTime > 0 || this.getHealth() > this.getMaxHealth())
		{
			return false;
		}
		
		this.heal(((ItemFood)Item.fishRaw).getHealAmount());
		this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.getRNG().nextFloat() - this.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
		this.attackTime = 15;
		return true;
	}
	
	
}