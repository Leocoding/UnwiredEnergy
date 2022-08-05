package fr.unwired.nrj.objects.blocks;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.init.BlocksInit;
import fr.unwired.nrj.init.ItemsInit;
import fr.unwired.nrj.util.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class BlockBase extends Block
{

	

	public BlockBase(String name, Material materialIn, float resistance, float hardness)
	{
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setResistance(resistance);
		setHardness(hardness);
		setCreativeTab(UnwiredMod.UNWIRED_TAB);
		BlocksInit.BLOCKS.add(this);
	
		ItemsInit.ITEMS.add(new UnwiredItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	public Item getItemDropped() {
		return Item.getItemFromBlock(this);
		
	}
	
	public int quantityDropped() {
		return 1;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return 0;
	}
	
	public boolean canBeOnHead() {
		return false;
	}
	
	private class UnwiredItemBlock extends ItemBlock{

		/**
		 * @param block
		 */
		public UnwiredItemBlock(Block block) {
			super(block);
		}
		
		@Override
		public boolean isValidArmor(ItemStack stack,
				EntityEquipmentSlot armorType, Entity entity) {
			return armorType == EntityEquipmentSlot.HEAD && canBeOnHead();
		}
		
	}
}