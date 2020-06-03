package theSilverEcho.tweaks.mixin;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.FontRenderer.GlyphPageFontRenderer;
import theSilverEcho.tweaks.FontRenderer.GlyphPageFontRenderer1;
import theSilverEcho.tweaks.Tweaks;

@Mixin(TextRenderer.class) public class FontRenderMixin
{
	@Inject(method = "<init>", at = @At(value = "RETURN")) private void renderScoreboardSidebar(TextureManager textureManager,
			FontStorage fontStorage, CallbackInfo ci)
	{
		Tweaks.renderer = GlyphPageFontRenderer.create("Comic Sans MS",16,false,false,false);
	}
}
