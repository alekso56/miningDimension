package alekso56.world;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class config {
	public static int dimension;
	public static int biomeID;
	public static boolean spawnMonsters;
	public static boolean spawnAnimals;
	public static double height;
	public static int maxGenHeight;
	public static int portal; // inner block id
	public static int frame; // outer block id 2

	static void loadConfig(Configuration config) {
		config.load();

		// dimensionid
		Property dimensionProp = config.get("general", "DimensionID", 6);
		dimensionProp.comment = "The Mining World will have this dimension ID.";
		dimension = dimensionProp.getInt(6);
		// biomeid
		Property biomeProp = config.get("general", "BiomeID", 47);
		biomeProp.comment = "The Mining Biome will have this ID.";
		biomeID = biomeProp.getInt(47);
		// spawnmonsters, bool
		Property spawnMonstersProp = config.get("general", "spawnMonsters",
				false);
		spawnMonstersProp.comment = "If Monsters Spawn in the Mining World.";
		spawnMonsters = spawnMonstersProp.getBoolean(false);
		// spawnanimals bool
		Property spawnAnimalsProp = config
				.get("general", "spawnAnimals", false);
		spawnAnimalsProp.comment = "If Animals spawn in the Mining World.";
		spawnAnimals = spawnAnimalsProp.getBoolean(false);
		// worldgen height int -> double
		Property worldheight = config.get("general", "worldheight", 256);
		worldheight.comment = "Set the worldgen height for the mining dimension";
		height = worldheight.getInt(256);
		// inner portal block id
		Property inner = config.get("general", "blockid", 422);
		inner.comment = "Set the inner blocks in the portal for the mining dimension";
		portal = inner.getInt(422);
		// portal frame id
		Property outer = config.get("general", "blockid2", 57);
		outer.comment = "Set the outer blocks in the portal for the mining dimension";
		frame = outer.getInt(57);

		config.save();
	}
}
