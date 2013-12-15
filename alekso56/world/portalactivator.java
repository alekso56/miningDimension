package alekso56.world;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class portalactivator extends CommandBase
{
    @Override
    public int compareTo(Object arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "tm";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/tm";
    }

    @Override
    public List getCommandAliases()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
    	EntityPlayerMP playermp = null;

        if (astring.length != 2 && astring.length != 4)
        {
            playermp = getCommandSenderAsPlayer(icommandsender);
        }
        else
        {
            playermp = getPlayer(icommandsender, astring[0]);

            if (playermp == null)
            {
                throw new PlayerNotFoundException();
            }
        }
    	if (astring.length < 2 && playermp != null)
        {
            throw new WrongUsageException("Playername <letter o for overworld or something else for miningworld>", new Object[0]);
        }
        if (playermp.dimension != miningworld.dimension && !astring[1].equalsIgnoreCase("o"))
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(playermp, miningworld.dimension,new Portal(playermp.mcServer.worldServerForDimension(miningworld.dimension)));
           // System.out.println("transfer mining");
        }
        else if (astring[1].equalsIgnoreCase("o") && playermp.dimension != 0)
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(playermp, 0);
            //System.out.println("ntransfer o");
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        return icommandsender.canCommandSenderUseCommand(2, "/tm");
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender,
                                        String[] astring)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i)
    {
        // TODO Auto-generated method stub
        return false;
    }
}
