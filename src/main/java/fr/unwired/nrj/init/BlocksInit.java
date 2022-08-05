
package fr.unwired.nrj.init;

import java.util.ArrayList;
import java.util.List;

import fr.unwired.nrj.objects.blocks.BlockBase;
import fr.unwired.nrj.objects.blocks.BlockOreBase;
import fr.unwired.nrj.objects.blocks.energizer.EnergizerBlock;
import fr.unwired.nrj.objects.blocks.energyexchanger.BlockEnergyAcceptor;
import fr.unwired.nrj.objects.blocks.energyexchanger.BlockEnergyDispenser;
import fr.unwired.nrj.objects.blocks.relay.BlockEnergyRelay;
import fr.unwired.nrj.util.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class BlocksInit
{
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	

	public static Block energystone_ore_block = new BlockOreBase("energystone_ore_block", 2,5.0F,3.0F,true);;
	public static Block energystone_block =  new BlockBase("energystone_block",Material.ROCK,5.0F,5.0F);;
	
	public static Block energizer_block = new EnergizerBlock("energizer_block", Material.IRON, 5.0F, 5.0F);;
	public static Block energy_dispenser = new BlockEnergyDispenser("energy_dispenser", Material.IRON, 5.0F, 5.0F);
	public static Block energy_acceptor = new BlockEnergyAcceptor("energy_acceptor", Material.IRON, 5.0F, 5.0F);
	
	public static Block energy_relay = new BlockEnergyRelay("energy_relay", Material.IRON, 5.0F, 5.0F);
}