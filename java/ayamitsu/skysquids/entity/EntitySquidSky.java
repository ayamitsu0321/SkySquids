package ayamitsu.skysquids.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

/**
 * Created by ayamitsu0321 on 2015/04/05.
 */
public class EntitySquidSky extends EntitySquid {

    private float _randomMotionSpeed;
    private float _rotationVelocity;
    private float _field_70871_bB;
    private float _randomMotionVecX;
    private float _randomMotionVecY;
    private float _randomMotionVecZ;

    public EntitySquidSky(World world) {
        super(world);
        //this.tasks = new EntityAITasks(world != null && world.theProfiler != null ? world.theProfiler : null);
        //this.tasks.addTask(0, new EntitySquidSky.AIMoveRandom());
        this.changeTask();
    }

    @SuppressWarnings("unchecked")
    public void changeTask() {
        EntityAIBase ai = null;

        for (EntityAITasks.EntityAITaskEntry entityAITaskEntry : (Set<EntityAITasks.EntityAITaskEntry>)this.tasks.taskEntries) {
            if (entityAITaskEntry.priority == 0) {
                ai = entityAITaskEntry.action;
            }
        }

        if (ai != null) {
            this.tasks.removeTask(ai);
            this.tasks.addTask(0, new EntitySquidSky.AIMoveRandom());
        }
    }

    /*@Override
    public boolean isInWater() {
        return true;
    }*/

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.setAir(300);
    }

    public void onLivingUpdate() {
        //System.out.println(this.toString());// debug

        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        //this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this._rotationVelocity;

        if (this.squidRotation > 6.283185307179586D) {
            if (this.worldObj.isRemote) {
                this.squidRotation = 6.283186F;
            } else {
                this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);

                if (this.rand.nextInt(10) == 0) {
                    this._rotationVelocity = (1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F);
                }

                this.worldObj.setEntityState(this, (byte)19);
            }
        }

        if (this.squidRotation < 3.141593F) {
            float f = this.squidRotation / 3.141593F;
            //this.tentacleAngle = (MathHelper.sin(f * f * 3.141593F) * 3.141593F * 0.25F);

            if (f > 0.75D) {
                this._randomMotionSpeed = 1.0F;
                this._field_70871_bB = 1.0F;
            } else {
                this._field_70871_bB *= 0.8F;
            }
        } else {
            this.tentacleAngle = 0.0F;
            this._randomMotionSpeed *= 0.9F;
            this._field_70871_bB *= 0.99F;
        }

        if (!this.worldObj.isRemote) {
            this.motionX = (this._randomMotionVecX * this._randomMotionSpeed);
            this.motionY = (this._randomMotionVecY * this._randomMotionSpeed);
            this.motionZ = (this._randomMotionVecZ * this._randomMotionSpeed);
        }


        float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.renderYawOffset += (-(float)Math.atan2(this.motionX, this.motionZ) * 180.0F / 3.141593F - this.renderYawOffset) * 0.1F;
        this.rotationYaw = this.renderYawOffset;
        this.squidYaw = (float)(this.squidYaw + 3.141592653589793D * this._field_70871_bB * 1.5D);
        this.squidPitch += (-(float)Math.atan2(f, this.motionY) * 180.0F / 3.141593F - this.squidPitch) * 0.1F;
    }

    @Override
    public boolean hasMovementVector() {
        return this._randomMotionVecX != 0.0F || this._randomMotionVecY != 0.0F || this._randomMotionVecZ != 0.0F;
    }

    @Override
    public void setMovementVector(float p_175568_1_, float p_175568_2_, float p_175568_3_) {
        this._randomMotionVecX = p_175568_1_;
        this._randomMotionVecY = p_175568_2_;
        this._randomMotionVecZ = p_175568_3_;
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1;
        int k = MathHelper.floor_double(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.posY >= 90.0D && this.worldObj.canBlockSeeSky(blockpos);
    }


    class AIMoveRandom extends EntityAIBase {
        private EntitySquid squid = EntitySquidSky.this;
        //private static final String __OBFID = "CL_00002232";

        AIMoveRandom() {
        }

        public boolean shouldExecute() {
            return true;
        }

        public void updateTask() {
            int i = this.squid.getAge();

            if (i > 100) {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if ((this.squid.getRNG().nextInt(50) == 0) || !this.squid.hasMovementVector()) {
                float f = this.squid.getRNG().nextFloat() * 3.141593F * 2.0F;
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                this.squid.setMovementVector(f1, f2, f3);
            }
        }
    }
}
