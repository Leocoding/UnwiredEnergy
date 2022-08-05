package fr.unwired.nrj.objects.blocks.energyexchanger;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.objects.blocks.BlockBase;
import fr.unwired.nrj.util.References;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyAcceptor extends BlockBase implements ITileEntityProvider {

	public BlockEnergyAcceptor(String name, Material materialIn, float resistance, float hardness) {
		super(name, materialIn, resistance, hardness);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityEnergyAcceptor();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return super.hasTileEntity(state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityEnergyExchanger) {
				if (((TileEntityEnergyExchanger) te).getNetwork() != null) {
					playerIn.openGui(UnwiredMod.instance, References.GUI_ENERGY_EXCHANGER, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityEnergyAcceptor) {
			if (((TileEntityEnergyAcceptor) te).getNetwork() != null) ((TileEntityEnergyAcceptor) te).getNetwork().removePathFromAcceptor((TileEntityEnergyAcceptor) te);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
}
