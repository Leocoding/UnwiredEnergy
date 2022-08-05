package fr.unwired.nrj.world;


import java.util.Random;


import fr.unwired.nrj.init.BlocksInit;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		case 0:
			// generate our surface world
			generateSurface(world, random, chunkX * 16, chunkZ * 16);

		case -1:
			// generate our Nether World
			generateNether(world, random, chunkX * 16, chunkZ * 16);


		case 1:
			// generate our EnderWorld
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateSurface(World world, Random random, int x, int z) {
		this.addOreSpawn(BlocksInit.energystone_ore_block, world, random, x, z, 16, 16, 2 + random.nextInt(4), 30, 15, 60);
	}

	private void generateNether(World world, Random random, int x, int z) {}

	private void generateEnd(World world, Random random, int x, int z) {}

	private void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY) {
		for (int i = 0; i < chanceToSpawn; i++) {
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			(new WorldGenMinable(block.getDefaultState(), maxVeinSize)).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
	
	@SuppressWarnings("unused")
	private void addNetherOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY) {}
}

		

