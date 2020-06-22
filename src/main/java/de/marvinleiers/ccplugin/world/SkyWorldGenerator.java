package de.marvinleiers.ccplugin.world;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkyWorldGenerator extends ChunkGenerator
{
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        return new ArrayList<>();
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
    {
        return createChunkData(world);
    }

    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
    {
        return new byte[world.getMaxHeight() / 16][];
    }

    @Override
    public boolean canSpawn(World world, int x, int z)
    {
        return true;
    }
}
