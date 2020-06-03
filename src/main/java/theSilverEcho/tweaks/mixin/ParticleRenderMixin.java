package theSilverEcho.tweaks.mixin;

import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import theSilverEcho.tweaks.ColourHelper;
@Mixin(DamageParticle.class) public abstract class ParticleRenderMixin extends SpriteBillboardParticle
{
	protected ParticleRenderMixin(World world, double d, double e, double f)
	{
		super(world, d, e, f);
	}

	/**
	 * @author theSilverEcho
	 * @reason particleColour
	 */
	@Overwrite public void tick()
	{
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge)
		{
			this.markDead();
		} else
		{
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			this.colorRed = ColourHelper.rgb(6000).getRed();
			this.colorGreen = ColourHelper.rgb(6000).getGreen();
			this.colorBlue = ColourHelper.rgb(6000).getBlue();
			this.velocityX *= 0.699999988079071D;
			this.velocityY *= 0.699999988079071D;
			this.velocityZ *= 0.699999988079071D;
			this.velocityY -= 0.019999999552965164D;
			if (this.onGround)
			{
				this.velocityX *= 0.699999988079071D;
				this.velocityZ *= 0.699999988079071D;
			}

		}
	}
}
