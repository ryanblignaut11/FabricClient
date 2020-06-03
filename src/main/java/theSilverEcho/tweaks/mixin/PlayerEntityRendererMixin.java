package theSilverEcho.tweaks.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.cosmetic.CapeFeatureRender;
import theSilverEcho.tweaks.cosmetic.CapeRenderer;
import theSilverEcho.tweaks.cosmetic.WingsFeatureRender;
import theSilverEcho.tweaks.cosmetic.g;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float f)
	{
		super(dispatcher, model, f);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("RETURN")) public void init(
			EntityRenderDispatcher dispatcher, boolean b, CallbackInfo ci)
	{
		this.addFeature(new WingsFeatureRender(this));
		this.addFeature(new CapeFeatureRender(this));
//		this.addFeature(new CapeRenderer(this));

		//		this.addFeature(new g(this));
	}
}
