package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.util.Coords;

public class FakeAirNGHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileFakeAirNG tileAir = (TileFakeAirNG) accessor.getTileEntity();
		Coords nodeGenerator = tileAir.getMain();
		final TileNodeGenerator tileEntity = (TileNodeGenerator) nodeGenerator.w.getTileEntity(nodeGenerator.x, nodeGenerator.y, nodeGenerator.z);
		if(tileEntity != null) {
			if (tileEntity.getBoost()) {
				currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
			}
			currenttip.add("Redstone Setting: " + formatSetting(((IRedstoneSensitive)tileEntity).getCurrentSetting().id));
			currenttip.add(((TileNodeGenerator)tileEntity).canRun() ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
			if (accessor.getNBTData().getBoolean("Active")) {
				currenttip.add(accessor.getNBTData().getBoolean("Spawn") ? "Mode: Create Node" : tileEntity.getBoost() ? "Mode: Enhance Node" : "Mode: Recharge Node");
			}
		}
		return currenttip;
 	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return new ItemStack(Item.getItemFromBlock(TMBlocks.nodeGenerator),1);
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null) {
			Coords nodeGenerator = ((TileFakeAirNG)te).getMain();
			TileNodeGenerator tileEntity = (TileNodeGenerator) nodeGenerator.w.getTileEntity(nodeGenerator.x, nodeGenerator.y, nodeGenerator.z);
			tileEntity.writeToNBT(tag);
		}
        return tag;
	}

	private static String formatSetting(String id) {
		if (id.equals("High")) {
			return SpecialChars.RED + "High";
		} else if (id.equals("Low")) {
			return SpecialChars.GREEN + "Low";
		} else {
			return SpecialChars.GRAY + "None";
		}
	}
}
