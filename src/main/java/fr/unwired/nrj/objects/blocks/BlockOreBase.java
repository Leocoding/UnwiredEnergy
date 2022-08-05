package fr.unwired.nrj.objects.blocks;

import java.util.Random;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.init.BlocksInit;
import fr.unwired.nrj.init.ItemsInit;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOreBase extends BlockOre {
	
	private static boolean multipleQty = false;
	@SuppressWarnings("unused")
	private static int minDrop = 0;
	@SuppressWarnings("unused")
	private static int maxDrop = 0;
	
	public BlockOreBase(String name , int harvestLevel,float resistance, float hardness) {
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel("pickaxe", harvestLevel);
		setResistance(resistance);
		setHardness(hardness);
		setCreativeTab(UnwiredMod.UNWIRED_TAB);
		BlocksInit.BLOCKS.add(this);
		ItemsInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));	
	}
	
	public BlockOreBase(String name , int harvestLevel, float resistance, float hardness , boolean multiple) {
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel("pickaxe", harvestLevel);
		setResistance(resistance);
		setHardness(hardness);
		setCreativeTab(UnwiredMod.UNWIRED_TAB);
		BlocksInit.BLOCKS.add(this);
		ItemsInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		multipleQty = multiple;		
	}
	
	public Item getItemDropped(IBlockState state ,Random rand , int fortune) {
			return Item.getByNameOrId("unwiredenergy:energystone");
		
	}
	
	public int quantityDropped(Random rand) {
		return BlockOreBase.multipleQty ? MathHelper.getInt(rand, 3,5) : 1;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		
		if(this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
			if(this  == BlocksInit.energystone_ore_block) {
				return MathHelper.getInt(rand, 1,5);	
			}
		}
		else {	
		}
		return 0;
	}

}
