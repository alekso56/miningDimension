package alekso56.world;

import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class Worldmining extends WorldProviderSurface
{
    public boolean canRespawnHere()
    {
        return true;
    }
    
    @Override
    public String getWelcomeMessage()
    {
            return "broke into the mining world.";
    }
    
    @Override
    public String getDepartMessage()
    {
            return "left da mining unfinished";
    }

    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderMining(this.worldObj, this.worldObj.getSeed(), true);
    }

    public String getDimensionName()
    {
        return "Mining World";
    }

    public double getMovementFactor()
    {
        return 2.0D;
    }

    public String getSaveFolder()
    {
        return "Mining World";
    }

    public boolean getWorldHasVoidParticles()
    {
        return false;
    }

    public long getWorldTime()
    {
        if (!this.worldObj.isRemote)
        {
            return 6000L;
        }

        if (this.worldObj.getWorldInfo().getWorldTime() > 12000L)
        {
            this.worldObj.getWorldInfo().setWorldTime(0L);
        }

        return this.worldObj.getWorldInfo().getWorldTime();
    }

    public boolean isDaytime()
    {
        return true;
    }

    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerHell(miningworld.miningBiome, 0.5F, 0.0F);
        this.dimensionId = miningworld.dimension;
    }
}