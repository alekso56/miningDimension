package alekso56.world;
import net.minecraft.util.ChunkCoordinates;
// le lazy copy of mc
public class portalpos extends ChunkCoordinates
{
public long field_85087_d;
final Portal field_85088_e;
public portalpos(Portal Portal, int par2, int par3, int par4, long par5)
{
super(par2, par3, par4);
this.field_85088_e = Portal;
this.field_85087_d = par5;
}
}

