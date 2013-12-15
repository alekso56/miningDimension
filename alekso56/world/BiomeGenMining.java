package alekso56.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeGenMining extends BiomeGenBase {
	public BiomeGenMining(int par1) {
		super(par1);
		setColor(2900485);
		setBiomeName("Mining Biome");
		setDisableRain(); // fak u rain
		setTemperatureRainfall(0.8F, 0.0F); // temperature of the mining world, amount of rain.
		setMinMaxHeight(0.0F, 0.0F); // min,max height of biome (world height not gen height)
		this.topBlock = ((byte) Block.grass.blockID); // top of ground
		this.fillerBlock = ((byte) Block.dirt.blockID); // just beneath ground
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.bigMushroomsPerChunk = 1;
		this.theBiomeDecorator.clayPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = -999;
		this.theBiomeDecorator.mushroomsPerChunk = 2;
		this.theBiomeDecorator.reedsPerChunk = 50;
		this.theBiomeDecorator.sandPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk2 = -999;
		this.theBiomeDecorator.treesPerChunk = 2;
		this.theBiomeDecorator.waterlilyPerChunk = -999;
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4) {
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = par2Random.nextInt(2);

		for (int var6 = 0; var6 < var5; var6++) {
			int var7 = par3 + par2Random.nextInt(16);
			int var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			int var10 = par1World.getBlockId(var7, var8, var9);

			if (var10 == Block.stone.blockID) {
				par1World.setBlock(var7, var8, var9, Block.oreEmerald.blockID,
						0, 2);
			}
		}

		var5 = par2Random.nextInt(16);
		WorldGenMinable worldGenSilverfish = new WorldGenMinable(
				Block.silverfish.blockID, 10);

		for (int var6 = 0; var6 < var5; var6++) {
			int var7 = par3 + par2Random.nextInt(16);
			int var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			int var10 = par1World.getBlockId(var7, var8, var9);

			if (var10 == Block.stone.blockID) {
				worldGenSilverfish.generate(par1World, par2Random, var7, var8,
						var9);
			}
		}
	}

	public void clearMonsters() {
		if (!config.spawnMonsters) {
			this.spawnableMonsterList.clear();
			this.spawnableCaveCreatureList.clear();
		}

		if (!config.spawnAnimals) {
			this.spawnableCreatureList.clear();
			this.spawnableWaterCreatureList.clear();
		}
	}

	public float getSpawningChance() {
		if ((!config.spawnAnimals) && (!config.spawnMonsters)) {
			return 0.0F;
		}

		return super.getSpawningChance();
	}

	public List getSpawnableList(EnumCreatureType par1EnumCreatureType) {
		if ((!config.spawnAnimals) && (!config.spawnMonsters)) {
			return new ArrayList();
		}

		return super.getSpawnableList(par1EnumCreatureType);
	}
}