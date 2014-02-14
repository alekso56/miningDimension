package alekso56.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.MapGenScatteredFeature;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

public class ChunkProviderMining implements IChunkProvider {
    private Random rand;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    private NoiseGeneratorOctaves noiseGen6;
    private World worldObj;
    private boolean mapFeaturesEnabled;
    private double noiseArray[];
    private double stoneNoise[];
    private MapGenCaves caveGenerator;
    private MapGenStronghold strongholdGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenVillage villageGenerator;
    private MapGenMineshaft mineshaftGenerator;
    private MapGenRavine ravineGenerator;
    private BiomeGenBase biomesForGeneration[];
    double noise3[];
    double noise1[];
    double noise2[];
    double noise6[];
    float field_35388_l[];
    int unusedIntArray32x32[][];
    long genSeed;

    public ChunkProviderMining(World world, long l, boolean flag) {
        stoneNoise = new double[256];
        caveGenerator = new MapGenCaves();
        strongholdGenerator = new MapGenStronghold();
        scatteredFeatureGenerator = new MapGenScatteredFeature();
        villageGenerator = new MapGenVillage();
        mineshaftGenerator = new MapGenMineshaft();
        ravineGenerator = new MapGenRavine();
        unusedIntArray32x32 = new int[32][32];
        worldObj = world;
        mapFeaturesEnabled = flag;
        rand = new Random(l);
        genSeed = l;
        noiseGen1 = new NoiseGeneratorOctaves(rand,
            MiningWorld.CPGNoiseGen1Octaves);
        noiseGen2 = new NoiseGeneratorOctaves(rand,
            MiningWorld.CPGNoiseGen2Octaves);
        noiseGen3 = new NoiseGeneratorOctaves(rand,
            MiningWorld.CPGNoiseGen3Octaves);
        noiseGen4 = new NoiseGeneratorOctaves(rand,
            MiningWorld.CPGNoiseGen4Octaves);
        noiseGen6 = new NoiseGeneratorOctaves(rand,
            MiningWorld.CPGNoiseGen6Octaves);
    }

    public void generateTerrain(int i, int j, byte abyte0[], boolean flag) {
        byte byte0 = 4;
        byte byte1 = 32;
        int k = (int)(127D * MiningWorld.SeaLevelScale);
        int l = byte0 + 1;
        byte byte2 = 33;
        int i1 = byte0 + 1;

        if (!flag) {
            biomesForGeneration = worldObj.getWorldChunkManager()
                .getBiomesForGeneration(biomesForGeneration, i * 4 - 2,
                    j * 4 - 2, l + 5, i1 + 5);
        } else {
            WorldChunkManager worldchunkmanager = new WorldChunkManager(genSeed, null);
            biomesForGeneration = worldchunkmanager.getBiomesForGeneration(
                biomesForGeneration, i * 4 - 2, j * 4 - 2, l + 5, i1 + 5);
        }

        noiseArray = initializeNoiseField(noiseArray, i * byte0, 0, j * byte0,
            l, byte2, i1);

        for (int j1 = 0; j1 < byte0; j1++) {
            for (int k1 = 0; k1 < byte0; k1++) {
                for (int l1 = 0; l1 < byte1; l1++) {
                    double d = MiningWorld.CPGGenTerrainYFactor;
                    double d1 = noiseArray[((j1 + 0) * i1 + (k1 + 0)) * byte2 + (l1 + 0)];
                    double d2 = noiseArray[((j1 + 0) * i1 + (k1 + 1)) * byte2 + (l1 + 0)];
                    double d3 = noiseArray[((j1 + 1) * i1 + (k1 + 0)) * byte2 + (l1 + 0)];
                    double d4 = noiseArray[((j1 + 1) * i1 + (k1 + 1)) * byte2 + (l1 + 0)];
                    double d5 = (noiseArray[((j1 + 0) * i1 + (k1 + 0)) * byte2 + (l1 + 1)] - d1) * d;
                    double d6 = (noiseArray[((j1 + 0) * i1 + (k1 + 1)) * byte2 + (l1 + 1)] - d2) * d;
                    double d7 = (noiseArray[((j1 + 1) * i1 + (k1 + 0)) * byte2 + (l1 + 1)] - d3) * d;
                    double d8 = (noiseArray[((j1 + 1) * i1 + (k1 + 1)) * byte2 + (l1 + 1)] - d4) * d;

                    for (int i2 = 0; i2 < 8; i2++) {
                        double d9 = MiningWorld.CPGGenTerrainXZFactor1;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int j2 = 0; j2 < 4; j2++) {
                            int k2 = j2 + j1 * 4 << 12 | 0 + k1 * 4 << 8 | l1 * 8 + i2;
                            char c = 256;
                            k2 -= c;
                            double d14 = MiningWorld.CPGGenTerrainXZFactor2;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            d15 -= d16;

                            for (int l2 = 0; l2 < 4; l2++) {
                                if ((d15 += d16) > MiningWorld.CPGGenTerrainSolidCutoff) {
                                    abyte0[k2 += c] = MiningWorld.CPGGenTerrainDefaultFillID;
                                    continue;
                                }

                                if (l1 * 8 + i2 < k) {
                                    abyte0[k2 += c] = MiningWorld.CPGGenTerrainSeaLevelFillID;
                                } else {
                                    abyte0[k2 += c] = 0;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int i, int j, byte abyte0[],
        BiomeGenBase abiomegenbase[]) {
        int k = (int)(127D * MiningWorld.SeaLevelScale);
        double d = MiningWorld.CPGReplaceStoneNoiseScale;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, i * 16, j * 16,
            0, 16, 16, 1, d * 2D, d * 2D, d * 2D);

        for (int l = 0; l < 16; l++) {
            for (int i1 = 0; i1 < 16; i1++) {
                BiomeGenBase biomegenbase = abiomegenbase[i1 + l * 16];
                float f = biomegenbase.getFloatTemperature();
                int j1 = (int)(stoneNoise[l + i1 * 16] / MiningWorld.CPGReplaceStoneNoiseDivisor + MiningWorld.CPGReplaceStoneNoiseAdd + rand
                    .nextDouble() * MiningWorld.CPGReplaceStoneNoiseRandScale);
                int k1 = -1;
                byte byte0 = biomegenbase.topBlock;
                byte byte1 = biomegenbase.fillerBlock;

                for (int l1 = 255; l1 >= 0; l1--) {
                    int i2 = (i1 * 16 + l) * 256 + l1;

                    if (l1 <= 0 + rand.nextInt(MiningWorld.CPGReplaceBedrockChance)) {
                        abyte0[i2] = MiningWorld.CPGReplaceBedrockID;
                        continue;
                    }

                    byte byte2 = abyte0[i2];

                    if (byte2 == 0) {
                        k1 = -1;
                        continue;
                    }

                    if (byte2 != MiningWorld.CPGReplaceColumnSkipID) {
                        continue;
                    }

                    if (k1 == -1) {
                        if (j1 <= 0) {
                            byte0 = 0;
                            byte1 = MiningWorld.CPGReplaceDefaultFillID;
                        } else if (l1 >= k - 4 && l1 <= k + 1) {
                            byte0 = biomegenbase.topBlock;
                            byte1 = biomegenbase.fillerBlock;
                        }

                        if (l1 < k && byte0 == 0) {
                            if (MiningWorld.CPGReplaceIceTempCompareLess ? f < MiningWorld.CPGReplaceIceTempCutoff : f >= MiningWorld.CPGReplaceIceTempCutoff) {
                                byte0 = MiningWorld.CPGReplaceIceID;
                            } else {
                                byte0 = MiningWorld.CPGReplaceWaterID;
                            }
                        }

                        k1 = j1;

                        if (l1 >= k - 1) {
                            abyte0[i2] = byte0;
                        } else {
                            abyte0[i2] = byte1;
                        }

                        continue;
                    }

                    if (k1 <= 0) {
                        continue;
                    }

                    k1--;
                    abyte0[i2] = byte1;

                    if (k1 == 0 && byte1 == MiningWorld.CPGReplaceSandFillID) {
                        k1 = rand
                            .nextInt(MiningWorld.CPGReplaceSandSupportRollChance);
                        byte1 = MiningWorld.CPGReplaceSandSupportID;
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int i, int j) {
        return provideChunk(i, j);
    }

    public byte[] getChunkByteMap(int i, int j) {
        rand.setSeed((long) i * 0x4f9939f508L + (long) j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[0x10000];
        generateTerrain(i, j, abyte0, true);
        return abyte0;
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it
     * will generates all the blocks for the specified chunk from the map seed
     * and chunk seed
     */
    public Chunk provideChunk(int i, int j) {
        rand.setSeed((long) i * 0x4f9939f508L + (long) j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[0x65536];
        generateTerrain(i, j, abyte0, false);
        biomesForGeneration = worldObj.getWorldChunkManager()
            .loadBlockGeneratorData(biomesForGeneration, i * 16, j * 16,
                16, 16);
        replaceBlocksForBiome(i, j, abyte0, biomesForGeneration);
        caveGenerator.generate(this, worldObj, i, j, abyte0);
        ravineGenerator.generate(this, worldObj, i, j, abyte0);

        if (mapFeaturesEnabled) {
            mineshaftGenerator.generate(this, worldObj, i, j, abyte0);
            villageGenerator.generate(this, worldObj, i, j, abyte0);
            strongholdGenerator.generate(this, worldObj, i, j, abyte0);
            scatteredFeatureGenerator.generate(this, worldObj, i, j, abyte0);
        }

        Chunk chunk = new Chunk(worldObj, i, j);
        ExtendedBlockStorage aextendedblockstorage[] = chunk
            .getBlockStorageArray();
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k) {
            abyte1[k] = (byte) this.biomesForGeneration[k].biomeID;
        }

        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                for (int i1 = 0; i1 < 256; i1++) {
                    byte byte0 = abyte0[k << 12 | l << 8 | i1];

                    if (byte0 == 0) {
                        continue;
                    }

                    int j1 = i1 >> 4;

                    if (aextendedblockstorage[j1] == null) {
                        aextendedblockstorage[j1] = new ExtendedBlockStorage(
                            j1 << 4, mapFeaturesEnabled);
                    }

                    aextendedblockstorage[j1].setExtBlockID(k, i1 & 0xf, l,
                        byte0 & 0xff);
                }
            }
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] initializeNoiseField(double ad[], int i, int j, int k,
        int l, int i1, int j1) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }

        if (field_35388_l == null) {
            field_35388_l = new float[25];

            for (int k1 = -2; k1 <= 2; k1++) {
                for (int l1 = -2; l1 <= 2; l1++) {
                    float f = MiningWorld.CPGInitNoiseBiomeNumerator / MathHelper.sqrt_float((float)(k1 * k1 + l1 * l1) + MiningWorld.CPGInitNoiseBiomeAdd);
                    field_35388_l[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d = MiningWorld.CPGInitNoiseXZMajorScale;
        double d1 = MiningWorld.CPGInitNoiseYMajorScale;
        noise6 = noiseGen6.generateNoiseOctaves(noise6, i, k, l, j1,
            MiningWorld.CPGInitNoiseXMidScale,
            MiningWorld.CPGInitNoiseZMidScale,
            MiningWorld.CPGInitNoiseYMidScale);
        noise3 = noiseGen3.generateNoiseOctaves(noise3, i, j, k, l, i1, j1, d / MiningWorld.CPGInitNoiseXSlopeDiv, d1 / MiningWorld.CPGInitNoiseYSlopeDiv, d / MiningWorld.CPGInitNoiseZSlopeDiv);
        noise1 = noiseGen1.generateNoiseOctaves(noise1, i, j, k, l, i1, j1, d * MiningWorld.CPGInitNoiseXLowerScale, d1 * MiningWorld.CPGInitNoiseYLowerScale, d * MiningWorld.CPGInitNoiseZLowerScale);
        noise2 = noiseGen2.generateNoiseOctaves(noise2, i, j, k, l, i1, j1, d * MiningWorld.CPGInitNoiseXUpperScale, d1 * MiningWorld.CPGInitNoiseYUpperScale, d * MiningWorld.CPGInitNoiseZUpperScale);
        i = k = 0;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < l; k2++) {
            for (int l2 = 0; l2 < j1; l2++) {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte byte0 = 2;
                BiomeGenBase biomegenbase = biomesForGeneration[k2 + 2 + (l2 + 2) * (l + 5)];

                for (int i3 = -byte0; i3 <= byte0; i3++) {
                    for (int j3 = -byte0; j3 <= byte0; j3++) {
                        BiomeGenBase biomegenbase1 = biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (l + 5)];
                        float f4 = field_35388_l[i3 + 2 + (j3 + 2) * 5] / (biomegenbase1.minHeight + MiningWorld.CPGInitNoiseBiomeMinHeightBump);

                        if (biomegenbase1.minHeight > biomegenbase.minHeight) {
                            f4 /= MiningWorld.CPGInitNoiseBiomeInterpFactor;
                        }

                        f1 += biomegenbase1.maxHeight * f4;
                        f2 += biomegenbase1.minHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * (1.0F - MiningWorld.CPGInitNoiseSolidFloorFactor) + MiningWorld.CPGInitNoiseSolidFloorFactor;
                f2 = (f2 * MiningWorld.CPGInitNoiseF2Scale - MiningWorld.CPGInitNoiseF2Sub) / MiningWorld.CPGInitNoiseF2Denom;
                double d2 = noise6[j2] / (double) MiningWorld.CPGInitNoiseMidDenom;

                if (d2 < 0.0D) {
                    d2 = -d2 * MiningWorld.CPGInitNoiseD2ReverseFactor;
                }

                d2 = d2 * MiningWorld.CPGInitNoiseD2Scale - MiningWorld.CPGInitNoiseD2Subtract;

                if (d2 < 0.0D) {
                    d2 /= MiningWorld.CPGInitNoiseD2PreClampScale;

                    if (d2 < -1D) {
                        d2 = -1D;
                    }

                    d2 /= MiningWorld.CPGInitNoiseD2PostClampScale;
                } else {
                    if (d2 > 1.0D) {
                        d2 = 1.0D;
                    }

                    d2 /= MiningWorld.CPGInitNoiseD2NonClampScale;
                }

                j2++;

                for (int k3 = 0; k3 < i1; k3++) {
                    double d3 = f2;
                    double d4 = f1;
                    d3 += d2 * MiningWorld.CPGInitNoiseD2FinalScale;
                    d3 = (d3 * (double) i1) / MiningWorld.CPGInitNoiseD3HeightFactor;
                    double d5 = (double) i1 / 2D + d3 * MiningWorld.CPGInitNoiseD3BlockScale;
                    double d6 = 0.0D;
                    double d7 = (((double) k3 - d5) * MiningWorld.CPGInitNoiseD7HeightScale * MiningWorld.CPGInitNoiseD7HeightTotal) / 256D / d4;

                    if (d7 < 0.0D) {
                        d7 *= MiningWorld.CPGInitNoiseD7ClampFactor;
                    }

                    double d8 = noise1[i2] / MiningWorld.CPGInitNoiseLowerDenom;
                    double d9 = noise2[i2] / MiningWorld.CPGInitNoiseUpperDenom;
                    double d10 = (noise3[i2] / MiningWorld.CPGInitNoiseSlopeDenom + MiningWorld.CPGInitNoiseSlopeAdd) * MiningWorld.CPGInitNoiseSlopeScale;

                    if (d10 < 0.0D) {
                        d6 = d8;
                    } else if (d10 > 1.0D) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > i1 - 4) {
                        double d11 = (double)(float)(k3 - (i1 - 4)) / MiningWorld.CPGInitNoiseHeightLimitDenom;
                        d6 = d6 * (1.0D - d11) + MiningWorld.CPGInitNoiseHeightLimitRoundoff * d11;
                    }

                    ad[i2] = d6;
                    i2++;
                }
            }
        }

        return ad;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int i, int j) {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        BiomeGenBase biomegenbase = worldObj.getWorldChunkManager()
            .getBiomeGenAt(k + 16, l + 16);
        rand.setSeed(worldObj.getSeed());
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        long l2 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long) i * l1 + (long) j * l2 ^ worldObj.getSeed());
        boolean flag = false;

        if (mapFeaturesEnabled) {
            mineshaftGenerator.generateStructuresInChunk(worldObj, rand, i, j);
            flag = villageGenerator.generateStructuresInChunk(worldObj, rand,
                i, j);
            strongholdGenerator.generateStructuresInChunk(worldObj, rand, i, j);
            scatteredFeatureGenerator.generateStructuresInChunk(worldObj, rand,
                i, j);
        }

        if (!flag && rand.nextInt(MiningWorld.CPGPopulateLakeChance) == 0) {
            int i1 = k + rand.nextInt(16) + 8;
            int j2 = MiningWorld.CPGPopulateLakeYMin + rand.nextInt(MiningWorld.CPGPopulateLakeYRange);
            int k3 = l + rand.nextInt(16) + 8;
            (new WorldGenLakes(MiningWorld.CPGPopulateLakeID)).generate(
                worldObj, rand, i1, j2, k3);
        }

        if (!flag && rand.nextInt(MiningWorld.CPGPopulateLavaChance) == 0) {
            int j1 = k + rand.nextInt(16) + 8;
            int k2 = MiningWorld.CPGPopulateLavaYMin + rand.nextInt(rand
                .nextInt(MiningWorld.CPGPopulateLavaYRange - 8) + 8);
            int l3 = l + rand.nextInt(16) + 8;

            if (k2 < (int)(256D * MiningWorld.SeaLevelScale) || rand.nextInt(MiningWorld.CPGPopulateAboveGroundLavaChance) == 0) {
                (new WorldGenLakes(MiningWorld.CPGPopulateLavaID)).generate(
                    worldObj, rand, j1, k2, l3);
            }
        }

        for (int k1 = 0; k1 < MiningWorld.CPGPopulateDungeonCount; k1++) {
            int i3 = k + rand.nextInt(16) + 8;
            int i4 = MiningWorld.CPGPopulateDungeonYMin + rand.nextInt(MiningWorld.CPGPopulateDungeonYRange);
            int k4 = l + rand.nextInt(16) + 8;

            if ((new WorldGenDungeons()).generate(worldObj, rand, i3, i4, k4))
            ;
        }

        biomegenbase.decorate(worldObj, rand, k, l);
        SpawnerAnimals.performWorldGenSpawning(worldObj, biomegenbase, k + 8,
            l + 8, 16, 16, rand);
        k += 8;
        l += 8;

        for (int i2 = 0; i2 < 16; i2++) {
            for (int j3 = 0; j3 < 16; j3++) {
                int j4 = worldObj.getPrecipitationHeight(k + i2, l + j3);

                if (worldObj.isBlockFreezable(i2 + k, j4 - 1, j3 + l)) {
                    worldObj.setBlock(i2 + k, j4 - 1, j3 + l,
                        Block.ice.blockID, 0, 2);
                }

                if (worldObj.canSnowAt(i2 + k, j4, j3 + l)) {
                    worldObj.setBlock(i2 + k, j4, j3 + l, Block.snow.blockID,
                        0, 2);
                }
            }
        }

        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go. If
     * passed false, save up to two chunks. Return true if all chunks have been
     * saved.
     */
    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with
     * chunkSet.add() never being called it thinks the list is always empty and
     * will not remove any chunks.
     */
    public boolean unload100OldestChunks() {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave() {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString() {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the
     * given location.
     */
    public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i,
        int j, int k) {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(i, k);

        if (biomegenbase == null) {
            return null;
        } else {
            return biomegenbase.getSpawnableList(enumcreaturetype);
        }
    }

    /**
     * Returns the location of the closest structure of the specified type. If
     * not found returns null.
     */
    public ChunkPosition findClosestStructure(World world, String s, int i,
        int j, int k) {
        if ("Stronghold".equals(s) && strongholdGenerator != null) {
            return strongholdGenerator.getNearestInstance(world, i, j, k);
        } else {
            return null;
        }
    }

    @
    Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @
    Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @
    Override
    public void recreateStructures(int i, int j) {
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, i, j, (byte[]) null);
            this.villageGenerator.generate(this, this.worldObj, i, j, (byte[]) null);
            this.strongholdGenerator.generate(this, this.worldObj, i, j, (byte[]) null);
            this.scatteredFeatureGenerator.generate(this, this.worldObj, i, j, (byte[]) null);
        }
    }

    public void saveExtraData() {}
}