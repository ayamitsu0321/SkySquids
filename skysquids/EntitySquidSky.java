package skysquids;

import net.minecraft.src.*;

public class EntitySquidSky extends EntityCreature
{
    public float field_21089_a = 0.0F;
    public float field_21088_b = 0.0F;
    public float field_21087_c = 0.0F;
    public float field_21086_f = 0.0F;
    public float field_21085_g = 0.0F;
    public float field_21084_h = 0.0F;
    public float tentacleAngle = 0.0F;
    public float lastTentacleAngle = 0.0F;
    private float randomMotionSpeed = 0.0F;
    private float field_21080_l = 0.0F;
    private float field_21079_m = 0.0F;
    private float randomMotionVecX = 0.0F;
    private float randomMotionVecY = 0.0F;
    private float randomMotionVecZ = 0.0F;

    public EntitySquidSky(World var1)
    {
        super(var1);
        this.texture = "/mob/squid.png";
        this.setSize(0.95F, 0.95F);
        this.field_21080_l = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float var1) {}

    public int getMaxHealth()
    {
        return 10;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return null;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return 0;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean var1, int var2)
    {
        int var3 = this.rand.nextInt(3 + var2) + 1;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
        }
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 120;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        return super.interact(var1);
    }

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
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
            this.tentacleAngle = MathHelper.sin(var1 * var1 * (float)Math.PI) * (float)Math.PI * 0.25F;

            if ((double)var1 > 0.75D)
            {
                this.randomMotionSpeed = 1.0F;
                this.field_21079_m = 1.0F;
            }
            else
            {
                this.field_21079_m *= 0.8F;
            }
        }
        else
        {
            this.tentacleAngle = 0.0F;
            this.randomMotionSpeed *= 0.9F;
            this.field_21079_m *= 0.99F;
        }

        if (!this.worldObj.isRemote)
        {
            this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
            this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
            this.motionY *= 0.9800000190734863D;
            this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
        }

        var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
        this.rotationYaw = this.renderYawOffset;
        this.field_21087_c += (float)Math.PI * this.field_21079_m * 1.5F;
        this.field_21089_a += (-((float)Math.atan2((double)var1, this.motionY)) * 180.0F / (float)Math.PI - this.field_21089_a) * 0.1F;
        this.motionY -= 0.012086000000000003D;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
	@Override
    public boolean getCanSpawnHere()
	{
    	return super.getCanSpawnHere() && this.posY > 95.0D && getEntityCount() <= mod_SkySquids.spawnLimit;
    }
	
	public int getEntityCount()
	{
		return this.worldObj.countEntities(skysquids.EntitySquidSky.class);
	}

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return false;
    }

    protected void updateEntityActionState()
    {
        ++this.entityAge;

        if (this.entityAge > 100)
        {
            this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
        }
        else if (this.rand.nextInt(50) == 0 || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F)
        {
            float var1 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
            this.randomMotionVecX = MathHelper.cos(var1) * 0.2F;
        	this.randomMotionVecY = -0.1F + this.rand.nextFloat() * 0.2F;
            this.randomMotionVecZ = MathHelper.sin(var1) * 0.2F;
        }

        this.despawnEntity();
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float var1, float var2)
    {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
