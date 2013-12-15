package alekso56.world;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class config
{
    static void loadConfig(Configuration config)
    {
        config.load();
        Property dimensionProp = config.get("general", "DimensionID", 6);
        dimensionProp.comment = "The Mining World will have this dimension ID.";
        miningworld.dimension = dimensionProp.getInt(6);
        Property biomeProp = config.get("general", "BiomeID", 47);
        biomeProp.comment = "The Mining Biome will have this ID.";
        miningworld.biomeID = biomeProp.getInt(47);
        Property spawnMonstersProp = config.get("general", "spawnMonsters", false);
        spawnMonstersProp.comment = "If Monsters Spawn in the Mining World.";
        miningworld.spawnMonsters = spawnMonstersProp.getBoolean(false);
        Property spawnAnimalsProp = config.get("general", "spawnAnimals", false);
        spawnAnimalsProp.comment = "If Animals spawn in the Mining World.";
        miningworld.spawnAnimals = spawnAnimalsProp.getBoolean(false);
        Property worldheight = config.get("general", "worldheight", 256);
        worldheight.comment = "Set the world height for the mining dimension";
        miningworld.height = worldheight.getInt();
        Property inner = config.get("general", "blockid", 5);
        inner.comment = "Set the inner blocks in the portal for the mining dimension";
        miningworld.blockid = inner.getInt();
        Property outer = config.get("general", "blockid2", 57);
        outer.comment = "Set the outer blocks in the portal for the mining dimension";
        miningworld.blockid2 = outer.getInt();
        config.save();
    }
}
