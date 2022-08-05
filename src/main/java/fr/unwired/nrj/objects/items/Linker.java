package fr.unwired.nrj.objects.items;
import fr.unwired.nrj.network.Path;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyAcceptor;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyDispenser;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityLinkableBlock;
import fr.unwired.nrj.objects.blocks.relay.TileEntityEnergyRelay;
import fr.unwired.nrj.util.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Linker extends ItemBase {

	
	private boolean reUse = false;
	private BlockPos position;
	
	private Path path;
	
	public Linker(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

		// Si on est cote serveur : 
		if(!worldIn.isRemote) {

			if(!(worldIn.getTileEntity(pos) instanceof TileEntityLinkableBlock)) {
				player.sendMessage(new TextComponentString("Linking to this this block is impossible"));
				return EnumActionResult.FAIL;
			}

			if(!reUse) {
				path = new Path();
				TileEntityLinkableBlock tile = (TileEntityLinkableBlock) worldIn.getTileEntity(pos);
				if (tile == null) return EnumActionResult.FAIL;
				if (!(tile instanceof TileEntityEnergyDispenser)) return EnumActionResult.FAIL;
				player.sendMessage(new TextComponentString("Starting linking"));
				player.sendMessage(new TextComponentString("Current max distance : " + Config.networkDistance));
				
				path.setSource((TileEntityEnergyDispenser)tile);
				position = path.getSource().getPos();
				reUse = true;
			}
			else {
				if(position.equals(pos)) {
					player.sendMessage(new TextComponentString("Can't link to the same block"));
					return EnumActionResult.FAIL;
				}
				
				if(isValidCoord(pos,position)) {
					TileEntityLinkableBlock tile = (TileEntityLinkableBlock) worldIn.getTileEntity(pos);
					if (tile == null) return EnumActionResult.FAIL;
					if (worldIn.getTileEntity(pos) instanceof TileEntityEnergyRelay) {
						((TileEntityEnergyRelay)tile).setNetwork(path.getSource().getNetwork());
						path.addRelay((TileEntityEnergyRelay)tile); 
						position = ((TileEntityEnergyRelay)tile).getPos();
						player.sendMessage(new TextComponentString("Relay added"));
					} else {
						if (path.getSource().getNetwork().isIn((TileEntityEnergyAcceptor)tile)) {
							player.sendMessage(new TextComponentString("Block already linked"));
						} else {
							path.setDestination((TileEntityEnergyAcceptor)tile);
							path.getSource().getNetwork().addPath(path);
							path.getDestination().setNetwork(path.getSource().getNetwork());
							player.sendMessage(new TextComponentString("Ending linking"));

							reUse = false;
							position = null;
						}
					}
				} else {
					player.sendMessage(new TextComponentString("Blocks are too much apart (Max distance : " + Config.networkDistance + ")"));
					reUse = false;
					position = null;
					return EnumActionResult.FAIL;
				}
			}
		}
//		
        return EnumActionResult.SUCCESS;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			if (reUse && playerIn.isSneaking()) {
				reUse = false;
				playerIn.sendMessage(new TextComponentString("Linker has been reset"));
			}
			
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity)
	{
		return armorType == EntityEquipmentSlot.HEAD;
	}




	private boolean isValidCoord(BlockPos pos1, BlockPos pos2) {
		int maxDistance = Config.networkDistance ;
		boolean res = false;
		final int[] coord1 = new int[] {pos1.getX(),pos1.getY(),pos1.getZ()};
		final int[] coord2 = new int[] {pos2.getX(),pos2.getY(),pos2.getZ()};
		int diff; 
		for(int i = 0; i < 3; i++) {
			
			diff = coord1[i] - coord2[i];
			if(diff < 0 ) {
				if(diff >= -maxDistance) {
					res = true;
				}
				else {
					return false;
				}
			}
			else {
				if(diff <= maxDistance) {
					res = true;
				}
				else {
					return false;
				}
			}
		}	
		return res;
	}
	

}