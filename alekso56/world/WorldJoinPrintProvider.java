package alekso56.world;

import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldJoinPrintProvider extends WorldProviderSurface {
	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public String getWelcomeMessage() {
		return "broke into the mining world.";
	}

	@Override
	public String getDepartMessage() {
		return "left da mining unfinished";
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderMining(this.worldObj, this.worldObj.getSeed(),
				true);
	}

	@Override
	public String getDimensionName() {
		return "Mining World";
	}

	@Override
	public double getMovementFactor() {
		return 2.0D;
	}

	@Override
	public String getSaveFolder() {
		return "Mining World";
	}

	@Override
	public boolean getWorldHasVoidParticles() {
		return false;
	}

	@Override
	public long getWorldTime() {
		if (!this.worldObj.isRemote) {
			return 6000L;
		}

		if (this.worldObj.getWorldInfo().getWorldTime() > 12000L) {
			this.worldObj.getWorldInfo().setWorldTime(0L);
		}

		return this.worldObj.getWorldInfo().getWorldTime();
	}

	@Override
	public boolean isDaytime() {
		return true;
	}

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(MiningWorld.miningBiome,
				0.5F, 0.0F);
		this.dimensionId = config.dimension;
	}
}