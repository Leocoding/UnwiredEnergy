package fr.unwired.nrj.objects.blocks.relay;

import java.util.List;

import fr.unwired.nrj.objects.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyRelay extends BlockBase implements ITileEntityProvider {
	
	public BlockEnergyRelay(String name, Material materialIn, float resistance, float hardness) {
		super(name, materialIn, resistance, hardness);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityEnergyRelay();
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tile = worldIn.getTileEntity(pos);
		((TileEntityEnergyRelay)tile).removePathFromRelay();
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	
	/**
	 * Permet d'ajouter des hitboxes pour refaire "la meme forme" que le relay
	 */
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn,
			boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.125D, 0.75D));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.3125D, 0.125D, 0.3125D, 0.6875D, 0.25D, 0.6875D));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 0.375D, 0.625D));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.4375D, 0.375D, 0.4375D, 0.5625, 1D, 0.5625));
	}
	
	
	/**
	 * Permet de gerer le render de la bordure de selection
	 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return new AxisAlignedBB(0.25D, 0D, 0.25D, 0.75, 1D, 0.75);
	}
	
	/**
	 * Quand changement d'un voisin 
	 */
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.checkForDrop(worldIn, pos, state);
    }

    /**
	 * Permet de drop le relay quand le bloc en dessous est broke
     */
    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Check si le bloc en dessous est compatible pour le relay
     */
    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
        return !worldIn.isAirBlock(pos.down());
    }
    
    @Override
    public boolean canBeOnHead() {
    	return true;
    }
		
}
