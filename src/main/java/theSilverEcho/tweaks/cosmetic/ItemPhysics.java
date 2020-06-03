package theSilverEcho.tweaks.cosmetic;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Random;

public abstract class ItemPhysics extends EntityRenderer<ItemEntity>
{
	public static long tick;
	private static final Random random = new Random();
	private static final MinecraftClient minecraft = MinecraftClient.getInstance();

	protected ItemPhysics(EntityRenderDispatcher entityRenderDispatcher)
	{
		super(entityRenderDispatcher);
	}

	public static void render2(ItemEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
	{
		matrices.push();
		ItemStack stack = entity.getStack();
		int j = stack.isEmpty() ? 187 : Item.getRawId(stack.getItem()) + stack.getDamage();
		random.setSeed(j);
		BakedModel model = minecraft.getItemRenderer().getModels().getModel(stack);
		boolean hasDepth = model.hasDepth();
		int itemAmount = getRenderedAmount(stack);

		float rotateAmount = (System.nanoTime() - tick) / 200000000F;
		if (minecraft.isPaused())
			rotateAmount = 0;
		matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion((float) Math.PI / 2));
		matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(entity.yaw));
		boolean applyEffects = entity.getAge() != 0 && (hasDepth || minecraft.getEntityRenderManager().gameOptions != null);

		if (entity.isSubmergedInWater())
			rotateAmount /= 2 * 10;

		if (applyEffects)
		{
			if (hasDepth)
			{
				if (!entity.onGround)
				{
					rotateAmount *= 2;
					entity.pitch += rotateAmount;
				}
			} else if (!Double.isNaN(entity.getX()) && !Double.isNaN(entity.getY()) && !Double.isNaN(entity.getZ()) && entity.world != null)
			{
				if (entity.onGround)
				{
					entity.pitch = 0;
				} else
				{
					rotateAmount *= 2;
					entity.pitch += rotateAmount;
				}
			}

			if (hasDepth)
				matrices.translate(0, -0.2, -0.08);

			else
				matrices.translate(0, 0, -0.04);

			double height = 0.2;
			if (hasDepth)
				matrices.translate(0, height, 0);
			matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(entity.pitch));
			if (hasDepth)
				matrices.translate(0, -height, 0);
		}

		if (!hasDepth)
		{
			float f7 = -0.0F * (itemAmount - 1) * 0.5F;
			float f8 = -0.0F * (itemAmount - 1) * 0.5F;
			float f9 = -0.09375F * (itemAmount - 1) * 0.5F;
			matrices.translate(f7, f8, f9);
		}

		for (int k = 0; k < itemAmount; ++k)
		{
			matrices.push();
			if (k > 0)
			{
				if (hasDepth)
				{
					float f11 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f13 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f10 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					matrices.translate(f11, f13, f10);
				}
			}
			minecraft.getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, light,
					OverlayTexture.DEFAULT_UV, model);
			matrices.pop();
			if (!hasDepth)
				matrices.translate(0.0, 0.0, 0.05375F);
		}

		matrices.pop();

	}

/*
	public static void render(ItemEntity entity, float partial, ModelTransformation.Mode renderMode, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, int overlay*/
/*, double x, double y, double z*//*
)
	{

		//		rotation = (double) (System.nanoTime() - tick) / 2500000 * 1;
		//		if (!minecraft.isWindowFocused())
		//			rotation = 0;
		ItemStack stack = entity.getStack();
		int j = stack.isEmpty() ? 187 : Item.getRawId(stack.getItem()) + stack.getDamage();
		random.setSeed(j);

		matrices.push();
		BakedModel model = minecraft.getItemRenderer().getModels().getModel(stack);
		boolean flag1 = model.hasDepth();
		int amount = getRenderedAmount(stack);

		//		matrices.translate(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
		//		RenderSystem.translated(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
		//		if (model.isBuiltin())
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(1.571f));
		Rotator rotator = (Rotator) entity;
		if (!entity.onGround && !entity.isSubmergedInWater())
		{
			float rotation3 = ((float) entity.getAge() + partial) / 20.0F + entity.hoverHeight;
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float) rotation));
			rotator.setRotation(new Vec3d(0, 0, rotation3));

		} else
		{
			matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion((float) rotator.getRotation().z));
		}
		matrices.translate(-0.07f, 0, -0.01f);
		if (entity.getStack().getItem() instanceof BlockItem)
		{
			matrices.translate(0.23f, 0, -0.14f);
		}
		*/
/*if (!(entity.getStack().getItem() instanceof BlockItem))
		{
			matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
		}*//*


		float scaleX = model.getTransformation().ground.scale.getX();
		float scaleY = model.getTransformation().ground.scale.getY();
		float scaleZ = model.getTransformation().ground.scale.getZ();
		matrices.scale(0.5f, 0.5f, 0.5f);
		*/
/*if (!flag1) {
			float r = -0.0F * (float)(amount - 1) * 0.5F * 1;
			float v = -0.0F * (float)(amount - 1) * 0.5F * 1;
			float w = -0.09375F * (float)(amount - 1) * 0.5F * 1;
			matrices.translate((double)r, (double)v, (double)w);
		}*//*


		float v;
		float w;

		for (int k = 0; k < amount; k++)
		{
			matrices.push();
			if (k > 0)
			{
				if (flag1)
				{
					v = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					w = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f6 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					matrices.translate(v, w, f6);
				} else
				{

					v = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					w = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					matrices.translate(v, w, 0);
				}
			}
			if (!flag1)
			{
				matrices.translate((double) (0.0F * scaleX), (double) (0.0F * scaleY), (double) (0.09375F * scaleZ));
			}

			minecraft.getItemRenderer().renderItem(stack, renderMode, false, matrices, vertexConsumers, light, overlay, model);
			matrices.pop();
		}
		matrices.pop();

	}
*/

	private static int getRenderedAmount(ItemStack stack)
	{
		int i = 1;
		if (stack.getCount() > 48)
		{
			i = 5;
		} else if (stack.getCount() > 32)
		{
			i = 4;
		} else if (stack.getCount() > 16)
		{
			i = 3;
		} else if (stack.getCount() > 1)
		{
			i = 2;
		}

		return i;
	}

}
