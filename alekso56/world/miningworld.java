package alekso56.world;

import net.minecraft.block.Block;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "alekso56Mining", name = "alekso56's miningworld", version = "1.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class MiningWorld {
	@Mod.Instance("alekso56Mining")
	public static MiningWorld instancez;
	public static BiomeGenMining miningBiome;
	public static innerportalblock innerportalblockz = new innerportalblock(422);
	public static portalcreatorItem portalcreator = new portalcreatorItem(2045);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config.loadConfig(new Configuration(event
				.getSuggestedConfigurationFile()));
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		GameRegistry.registerItem(portalcreator, "PortalCItem");
		GameRegistry.registerBlock(innerportalblockz, "MiningPortal");
		miningBiome = new BiomeGenMining(config.biomeID);

		if (DimensionManager.isDimensionRegistered(config.dimension)) {
			System.out.println("Failed to register the Mining Dimension with the ID "+ config.dimension + ". Please pick another one!");
		} else {
			DimensionManager.registerProviderType(config.dimension,WorldJoinPrintProvider.class, false);
			DimensionManager.registerDimension(config.dimension,config.dimension);
			
			FMLInterModComms.sendMessage("BuildCraft|Energy","oil-gen-exclude", miningBiome.biomeID + "");
			BiomeManager.addStrongholdBiome(miningBiome);
			BiomeDictionary.registerBiomeType(miningBiome,new BiomeDictionary.Type[] { BiomeDictionary.Type.MOUNTAIN });

			miningBiome.clearMonsters();
		}
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new portalActivator());
	}

	static double SeaLevelScale = 0.5D;
	static int CPGNoiseGen1Octaves = 16;
	static int CPGNoiseGen2Octaves = 16;
	static int CPGNoiseGen3Octaves = 8;
	static int CPGNoiseGen4Octaves = 4;
	static int CPGNoiseGen5Octaves = 10;
	static int CPGNoiseGen6Octaves = 16;
	static int CPGMobSpawnerNoiseOctaves = 8;
	static double CPGGenTerrainYFactor = 0.128D;
	static double CPGGenTerrainXZFactor1 = 0.40D;
	static double CPGGenTerrainXZFactor2 = 0.40D;
	static double CPGGenTerrainSolidCutoff = 0.0D;
	static byte CPGGenTerrainDefaultFillID = (byte) Block.stone.blockID;
	static byte CPGGenTerrainSeaLevelFillID = (byte) Block.waterStill.blockID;
	static double CPGReplaceStoneNoiseScale = 0.03125D;
	static double CPGReplaceStoneNoiseDivisor = 3D;
	static double CPGReplaceStoneNoiseAdd = 3D;
	static double CPGReplaceStoneNoiseRandScale = config.height;
	static int CPGReplaceBedrockChance = 1; // flat bedrock set? meh, me luv flat bedrock
	static byte CPGReplaceBedrockID = (byte) Block.bedrock.blockID;
	static int CPGBedrockMode = 0;
	static int CPGReplaceColumnSkipID = (byte) Block.stone.blockID;
	static byte CPGReplaceDefaultFillID = (byte) Block.stone.blockID;
	static float CPGReplaceIceTempCutoff = 0.15F;
	static boolean CPGReplaceIceTempCompareLess = true;
	static byte CPGReplaceIceID = (byte) Block.ice.blockID;
	static byte CPGReplaceWaterID = (byte) Block.waterStill.blockID;
	static int CPGReplaceSandFillID = Block.sand.blockID;
	static int CPGReplaceSandSupportRollChance = 4;
	static byte CPGReplaceSandSupportID = (byte) Block.sandStone.blockID;
	static long CPGProvideXSeedMult = 0x4f9939f508L;
	static long CPGProvideZSeedMult = 0x1ef1565bd5L;
	static double CPGInitNoiseXZMajorScale = 684.41200000000003D; // Terrain Noise Scaling Parameters
	static double CPGInitNoiseYMajorScale = 684.41200000000003D;
	static double CPGInitNoiseXMidScale = 200D;
	static double CPGInitNoiseZMidScale = 200D;
	static double CPGInitNoiseYMidScale = 0.5D;
	static double CPGInitNoiseXSlopeDiv = 80D; // Terrain Noise Interpolation Parameters
	static double CPGInitNoiseZSlopeDiv = 80D;
	static double CPGInitNoiseYSlopeDiv = 160D;
	static double CPGInitNoiseSlopeDenom = 10D;
	static double CPGInitNoiseSlopeAdd = 1.0D;
	static double CPGInitNoiseSlopeScale = 0.5D;
	static double CPGInitNoiseXLowerScale = 1.0D; // Terrain Noise Upper/Lower Limit Parameters
	static double CPGInitNoiseYLowerScale = 1.0D;
	static double CPGInitNoiseZLowerScale = 1.0D;
	static double CPGInitNoiseXUpperScale = 1.0D;
	static double CPGInitNoiseYUpperScale = 1.0D;
	static double CPGInitNoiseZUpperScale = 1.0D;
	static double CPGInitNoiseLowerDenom = 512D;
	static double CPGInitNoiseUpperDenom = 512D;
	static float CPGInitNoiseBiomeNumerator = 10F; // Misc Terrain Parameters
	static float CPGInitNoiseBiomeAdd = 0.2F;
	static float CPGInitNoiseBiomeMinHeightBump = 2.0F;
	static float CPGInitNoiseBiomeInterpFactor = 2F;
	static float CPGInitNoiseF2Scale = 4F;
	static float CPGInitNoiseF2Sub = 1.0F;
	static float CPGInitNoiseF2Denom = 8F;
	static double CPGInitNoiseD2ReverseFactor = 0.29999999999999999D;
	static double CPGInitNoiseD2Scale = 1D;
	static double CPGInitNoiseD2Subtract = 2D;
	static double CPGInitNoiseD2PreClampScale = 2D;
	static double CPGInitNoiseD2PostClampScale = 2.7999999999999998D;
	static double CPGInitNoiseD2NonClampScale = 8D;
	static double CPGInitNoiseD2FinalScale = 2D;
	static double CPGInitNoiseD3HeightFactor = 16D;
	static double CPGInitNoiseD3BlockScale = 4D;
	static double CPGInitNoiseD7ClampFactor = 4D;
	static double CPGInitNoiseD7HeightScale = 12D;
	static double CPGInitNoiseD7HeightTotal = config.height;
	static float CPGInitNoiseSolidFloorFactor = 0.1F;
	static float CPGInitNoiseMidDenom = 8000F;
	static double CPGInitNoiseHeightLimitDenom = 3D;
	static double CPGInitNoiseHeightLimitRoundoff = -10D;
	static int CPGPopulateLakeChance = 5;
	static int CPGPopulateLakeYMin = 0;
	static int CPGPopulateLakeYRange = 256;
	static int CPGPopulateLakeID = Block.waterStill.blockID;
	static int CPGPopulateLavaChance = 15;
	static int CPGPopulateLavaYMin = 0;
	static int CPGPopulateLavaYRange = 256;
	static int CPGPopulateAboveGroundLavaChance = 10;
	static int CPGPopulateLavaID = Block.lavaStill.blockID;
	static int CPGPopulateDungeonCount = 8;
	static int CPGPopulateDungeonYMin = 0;
	static int CPGPopulateDungeonYRange = 256;
}