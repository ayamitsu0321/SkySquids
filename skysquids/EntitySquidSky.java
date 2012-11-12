package skysquids;

import net.minecraft.src.*;

public class EntitySquidSky extends EntityCreature implements IAnimals
{
    public float field_21089_a = 0.0F;
    public float field_21088_b = 0.0F;
    public float field_21087_c = 0.0F;
    public float field_21086_f = 0.0F;
    public float field_21085_g = 0.0F;
    public float field_21084_h = 0.0F;
    public float tentacleAngle = 0.0F;
    public float lastTentacleAngle = 0.0F;
    protected float randomMotionSpeed = 0.0F;
    private float field_21080_l = 0.0F;
    private float field_21079_m = 0.0F;
    protected float randomMotionVecX = 0.0F;
    protected float randomMotionVecY = 0.0F;
    protected float randomMotionVecZ = 0.0F;

    public EntitySquidSky(World var1)
    {
        super(var1);
        this.texture = "/mob/squid.png";
        this.setSize(0.95F, 0.95F);
        this.field_21080_l = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
		this.rand.setSeed(187L);
    }

	@Override
	protected void fall(float par1) {}

    public int getMaxHealth()
    {
        return 10;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    protected int getDropItemId()
    {
        return 0;
    }

    @Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(3 + par2) + 1;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
		}
	}

	@Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public int getTalkInterval()
    {
        return 120;
    }
	
	@Override
	protected boolean canDespawn()
    {
        return true;
    }

	@Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return 1 + this.worldObj.rand.nextInt(3);
    }

    @Override
    public boolean interact(EntityPlayer var1)
    {
        return super.interact(var1);
    }

    @Override
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_21088_b = this.field_21089_a;
        this.field_21086_f = this.field_21087_c;
        this.field_21084_h = this.field_21085_g;
        this.lastTentacleAngle = this.tentacleAngle;
        this.field_21085_g += this.field_21080_l;

        if (this.field_21085_g > ((float)Math.PI * 2F))
        {
            this.field_21085_g -= ((float)Math.PI * 2F);

            if (this.rand.nextInt(10) == 0)
            {
                this.field_21080_l = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
            }
        }

        float var1;

        if (this.field_21085_g < (float)Math.PI)
        {
            var1 = this.field_21085_g / (float)Math.PI;
        	
        	if (this.worldObj.isRemote)
        	{
            	this.tentacleAngle = MathHelper.sin(var1 * var1 * (float)Math.PI) * (float)Math.PI * 0.25F;
        	}

            if ((double)var1 > 0.75D)
            {
            	if (!this.worldObj.isRemote)
            	{
                	this.randomMotionSpeed = 1.0F;
            	}
            	else
            	{
            		this.field_21079_m = 1.0F;
            	}
            }
        	else if (this.worldObj.isRemote)
            {
                this.field_21079_m *= 0.8F;
            }
        }
        else
        {
        	if (!this.worldObj.isRemote)
        	{
            	this.randomMotionSpeed *= 0.9F;
        	}
        	else
        	{
            	this.tentacleAngle = 0.0F;
            	this.field_21079_m *= 0.99F;
        	}
        }

        if (!this.worldObj.isRemote)
        {
            this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
            this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
            this.motionY *= 0.9800000190734863D;
            this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
        	this.motionY -= 0.012086000000000003D;
        }
    	else
    	{
	    	var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
	        this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
	        this.rotationYaw = this.renderYawOffset;
	        this.field_21087_c += (float)Math.PI * this.field_21079_m * 1.5F;
	        this.field_21089_a += (-((float)Math.atan2((double)var1, this.motionY)) * 180.0F / (float)Math.PI - this.field_21089_a) * 0.1F;
    	}
    }

	@Override
    public boolean getCanSpawnHere()
	{
    	return super.getCanSpawnHere() && this.posY > 95.0D && getEntityCount() <= mod_SkySquids.spawnLimit;
    }
	
	public int getEntityCount()
	{
		return this.worldObj.countEntities(skysquids.EntitySquidSky.class);
	}

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }

	@Override
    protected void updateEntityActionState()
    {
    	if (this.worldObj.isRemote)
    	{
    		return;
    	}
    	
        ++this.entityAge;

        if (this.entityAge > 100)
        {
            this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
        }
        else if (this.rand.nextInt(50) == 0 || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F)
        {
            float var1 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
            this.randomMotionVecX = MathHelper.cos(var1) * 0.2F;
        	this.randomMotionVecY = -0.05F + this.rand.nextFloat() * 0.2F;
            this.randomMotionVecZ = MathHelper.sin(var1) * 0.2F;
        }

        this.despawnEntity();
    }

    @Override
    public void moveEntityWithHeading(float var1, float var2)
    {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
