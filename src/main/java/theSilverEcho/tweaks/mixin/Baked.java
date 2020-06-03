package theSilverEcho.tweaks.mixin;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.WeightedBakedModel;
import net.minecraft.util.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Mixin(WeightedBakedModel.class) public abstract class Baked implements FabricBakedModel
{
	/*@Final
	@Shadow
	private List models;
	//	@Shadow private List models;

	@Final
	@Shadow
	private BakedModel defaultModel;

	@Final
	@Shadow
	private int totalWeight;

	@Override public boolean isVanillaAdapter()
	{
		return false;
	}

	@Override public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier,
			RenderContext context)
	{
		Random random_1 = randomSupplier.get();
		FabricBakedModel entry = ((FabricBakedModel) WeightedPicker.getAt(this.models, Math.abs((int) random_1.nextLong()) % this.totalWeight));
		entry.emitBlockQuads(blockView, state, pos, randomSupplier, context);
	}*/
}
