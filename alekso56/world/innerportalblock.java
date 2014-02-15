package alekso56.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class innerportalblock extends BlockBreakable {
	public innerportalblock(int id) {
		super(id, "alekso56:miningportal", Material.portal, false);
		this.setUnlocalizedName("miningportal");
		this.setTickRandomly(true);
		this.setHardness(-1.0F);
		this.setStepSound(soundGlassFootstep);
		this.setLightValue(0.75F);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);
		if (par1World.provider.isSurfaceWorld()
				&& par5Random.nextInt(2000) < par1World.difficultySetting) {
			int l;
			for (l = par3; !par1World.doesBlockHaveSolidTopSurface(par2, l,
					par4) && l > 0; --l) {
				;
			}
			if (l > 0 && !par1World.isBlockNormalCube(par2, l + 1, par4)) {
				Entity entity = ItemMonsterPlacer.spawnCreature(par1World, 57,
						par2 + 0.5D, l + 1.1D,
						par4 + 0.5D);
				if (entity != null) {
					entity.timeUntilPortal = entity.getPortalCooldown();
				}
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		float f;
		float f1;
		if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) != this.blockID
				&& par1IBlockAccess.getBlockId(par2 + 1, par3, par4) != this.blockID) {
			f = 0.125F;
			f1 = 0.5F;
			this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F,
					0.5F + f1);
		} else {
			f = 0.5F;
			f1 = 0.125F;
			this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F,
					0.5F + f1);
		}
	}

	/**
	 * Is this block (a) opaque and (B) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	/**
	 * Checks to see if this location is valid to create a portal and will
	 * return True if it does. Args: world, x, y, z
	 */
	public boolean tryToCreatePortal(World par1World, int par2, int par3,
			int par4) {
		byte b0 = 0;
		byte b1 = 0;
		if (par1World.getBlockId(par2 - 1, par3, par4) == config.frame
				|| par1World.getBlockId(par2 + 1, par3, par4) == config.frame) {
			b0 = 1;
		}
		if (par1World.getBlockId(par2, par3, par4 - 1) == config.frame
				|| par1World.getBlockId(par2, par3, par4 + 1) == config.frame) {
			b1 = 1;
		}
		if (b0 == b1) {
			return false;
		} else {
			if (par1World.getBlockId(par2 - b0, par3, par4 - b1) == 0) {
				par2 -= b0;
				par4 -= b1;
			}
			int l;
			int i1;
			for (l = -1; l <= 2; ++l) {
				for (i1 = -1; i1 <= 3; ++i1) {
					boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;
					if (l != -1 && l != 2 || i1 != -1 && i1 != 3) {
						int j1 = par1World.getBlockId(par2 + b0 * l, par3 + i1,
								par4 + b1 * l);
						if (flag) {
							if (j1 != config.frame) {
								return false;
							}
						} else if (j1 != 0 && j1 != Block.fire.blockID) {
							return false;
						}
					}
				}
			}
			for (l = 0; l < 2; ++l) {
				for (i1 = 0; i1 < 3; ++i1) {
					par1World.setBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l,
							config.portal, 0, 2);
				}
			}
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates. Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4, int par5) {
		if (par1IBlockAccess.getBlockId(par2, par3, par4) == this.blockID) {
			return false;
		} else {
			boolean flag = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.blockID
					&& par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != this.blockID;
			boolean flag1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.blockID
					&& par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != this.blockID;
			boolean flag2 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.blockID
					&& par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != this.blockID;
			boolean flag3 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.blockID
					&& par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != this.blockID;
			boolean flag4 = flag || flag1;
			boolean flag5 = flag2 || flag3;
			return flag4 && par5 == 4 ? true : (flag4 && par5 == 5 ? true
					: (flag5 && par5 == 2 ? true : flag5 && par5 == 3));
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		if ((par5Entity.ridingEntity == null)
				&& (par5Entity.riddenByEntity == null)
				&& ((par5Entity instanceof EntityPlayerMP))) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) par5Entity;
			if (thePlayer.timeUntilPortal > 0) {
				thePlayer.timeUntilPortal = 10;
			} else if (thePlayer.dimension != config.dimension && thePlayer.timeUntilPortal == 0) {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer
						.getConfigurationManager()
						.transferPlayerToDimension(
								thePlayer,
								config.dimension,
								new Portal(
										thePlayer.mcServer
												.worldServerForDimension(config.dimension)));
			} else if (thePlayer.timeUntilPortal == 0) {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager()
						.transferPlayerToDimension(
								thePlayer,
								0,
								new Portal(thePlayer.mcServer
										.worldServerForDimension(0)));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		if (par5Random.nextInt(100) == 0) {
			par1World.playSound(par2 + 0.5D, par3 + 0.5D,
					par4 + 0.5D, "portal.portal", 0.5F,
					par5Random.nextFloat() * 0.4F + 0.8F, false);
		}
		for (int l = 0; l < 4; ++l) {
			double d0 = par2 + par5Random.nextFloat();
			double d1 = par3 + par5Random.nextFloat();
			double d2 = par4 + par5Random.nextFloat();
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = par5Random.nextInt(2) * 2 - 1;
			d3 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d4 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d5 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			if (par1World.getBlockId(par2 - 1, par3, par4) != this.blockID
					&& par1World.getBlockId(par2 + 1, par3, par4) != this.blockID) {
				d0 = par2 + 0.5D + 0.25D * i1;
				d3 = par5Random.nextFloat() * 2.0F * i1;
			} else {
				d2 = par4 + 0.5D + 0.25D * i1;
				d5 = par5Random.nextFloat() * 2.0F * i1;
			}
			par1World.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return config.portal;
	}
}