package alekso56.world;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

@Mod(modid="alekso56's miningworld", name="alekso56's miningworld", version="1.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=true)
public class miningworld
{

  @Mod.Instance("alekso56's miningworld")
  public static miningworld instance;
  public static int IblockID;
  public static int fblockid;
  public static int dimension;
  public static int biomeID;
  public static boolean enableMSG;
  public static String sendMSG;
  public static boolean spawnMonsters;
  public static boolean spawnAnimals;
  public static boolean generateDungeons;
  public static boolean generateLakes;
  public static boolean generateLavaLakes;
  public static BiomeGenMining miningBiome;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
      config.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
  }

  @Mod.EventHandler
  public void load(FMLInitializationEvent event)
  {
    miningBiome = new BiomeGenMining(biomeID);
    if (DimensionManager.isDimensionRegistered(dimension)) {
      System.out.println( "Failed to register the Mining Dimension with the ID " + dimension + ". Please pick another one!");
    }
    DimensionManager.registerProviderType(dimension, WorldProviderMining.class, false);

    DimensionManager.registerDimension(dimension, dimension);

    FMLInterModComms.sendMessage("BuildCraft|Energy", "oil-gen-exclude", miningBiome.biomeID + "");

    BiomeManager.addStrongholdBiome(miningBiome);
    BiomeDictionary.registerBiomeType(miningBiome, new BiomeDictionary.Type[] { BiomeDictionary.Type.MOUNTAIN });
  }
  
  @Mod.EventHandler
   public void serverLoad(FMLServerStartingEvent event)
  {
    event.registerServerCommand(new portalactivator());
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {
    miningBiome.clearMonsters();

    System.out.println("Loaded.");
    System.out.println("Dimension registered with ID: " + dimension + ".");
  }
  
  public static EntityPlayerMP getPlayerForName(ICommandSender sender, String name)
  {
          EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(sender, name);

          if (var2 != null)
          {
                  return var2;
          }
          else
          {
                  return getPlayerForName(name);
          }
  }
  
  @SuppressWarnings("unchecked")
  public static EntityPlayerMP getPlayerForName(String name)
  {
          // tru exact match first.
          {
                  EntityPlayerMP tempPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(name);
                  if (tempPlayer != null)
                          return tempPlayer;
          }

          // now try getting others
          List<EntityPlayerMP> possibles = new LinkedList<EntityPlayerMP>();
          ArrayList<EntityPlayerMP> temp = (ArrayList<EntityPlayerMP>) FMLCommonHandler.instance().getSidedDelegate().getServer().getConfigurationManager().playerEntityList;
          for (EntityPlayerMP player : temp)
          {
                  if (player.username.equalsIgnoreCase(name))
                          return player;

                  if (player.username.toLowerCase().contains(name.toLowerCase()))
                  {
                          possibles.add(player);
                  }
          }
          if (possibles.size() == 1)
                  return possibles.get(0);
          return null;
  }
      public static boolean RunningDeobfuscated = true;
      public static WorldType superCustom = null;
      public static boolean BackedOut = false;
      public static Minecraft mcInstance = null;
      public static boolean SaveLoaded = false;
      public static World theWorld = null;
      public static boolean WorldCustomized = false;
      static double SeaLevelScale = 0.5D;
      static int CPGNoiseGen1Octaves = 16;
      static int CPGNoiseGen2Octaves = 16;
      static int CPGNoiseGen3Octaves = 8;
      static int CPGNoiseGen4Octaves = 4;
      static int CPGNoiseGen5Octaves = 10;
      static int CPGNoiseGen6Octaves = 16;
      static int CPGMobSpawnerNoiseOctaves = 8;
      static double CPGGenTerrainYFactor = 0.125D;
      static double CPGGenTerrainXZFactor1 = 0.25D;
      static double CPGGenTerrainXZFactor2 = 0.25D;
      static double CPGGenTerrainSolidCutoff = 0.0D;
      static byte CPGGenTerrainDefaultFillID = (byte) Block.stone.blockID;
      static byte CPGGenTerrainSeaLevelFillID = (byte) Block.waterStill.blockID;
      static double CPGReplaceStoneNoiseScale = 0.03125D;
      static double CPGReplaceStoneNoiseDivisor = 3D;
      static double CPGReplaceStoneNoiseAdd = 3D;
      static double CPGReplaceStoneNoiseRandScale = 0.25D;
      static int CPGReplaceBedrockChance = 5;
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
      static double CPGInitNoiseXZMajorScale = 684.41200000000003D;
      static double CPGInitNoiseYMajorScale = 684.41200000000003D;
      static double CPGInitNoiseXMidScale = 200D;
      static double CPGInitNoiseZMidScale = 200D;
      static double CPGInitNoiseYMidScale = 0.5D;
      static double CPGInitNoiseXSlopeDiv = 80D;
      static double CPGInitNoiseZSlopeDiv = 80D;
      static double CPGInitNoiseYSlopeDiv = 160D;
      static double CPGInitNoiseSlopeDenom = 10D;
      static double CPGInitNoiseSlopeAdd = 1.0D;
      static double CPGInitNoiseSlopeScale = 0.5D;
      static double CPGInitNoiseXLowerScale = 1.0D;
      static double CPGInitNoiseYLowerScale = 1.0D;
      static double CPGInitNoiseZLowerScale = 1.0D;
      static double CPGInitNoiseXUpperScale = 1.0D;
      static double CPGInitNoiseYUpperScale = 1.0D;
      static double CPGInitNoiseZUpperScale = 1.0D;
      static double CPGInitNoiseLowerDenom = 512D;
      static double CPGInitNoiseUpperDenom = 512D;
      static float CPGInitNoiseBiomeNumerator = 10F;  
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
      static double CPGInitNoiseD7HeightTotal = 128D;
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
      private boolean ButtonAdded;


  public static String getWorldMessage()
  {
    String msg = String.copyValueOf(sendMSG.toCharArray());
    if (enableMSG) {
      return msg;
    }
    return null;
  }
}