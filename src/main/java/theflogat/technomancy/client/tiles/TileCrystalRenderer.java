package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelCrystal;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.lib.Ref;

public class TileCrystalRenderer extends TileEntitySpecialRenderer {
	
	ModelCrystal model = new ModelCrystal();
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_CRYSTAL_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float t) {
		if (te instanceof TileCrystal) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glScalef(-1F, -1F, 1f);
			GL11.glTranslatef(-.5F, -1.5F, .5F);
			bindTexture(modelTexture);
			model.render(((TileCrystal)te).getStage(), te.getBlockMetadata());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
