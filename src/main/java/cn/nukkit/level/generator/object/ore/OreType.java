package cn.nukkit.level.generator.object.ore;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.math.BedrockRandom;
import cn.nukkit.math.MathHelper;
import cn.nukkit.utils.Identifier;

import static cn.nukkit.block.BlockIds.STONE;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
//porktodo: rewrite this, the whole class is terrible and generated ores look stupid
public class OreType {
    public final Block block;
    public final int clusterCount;
    public final int clusterSize;
    public final int maxHeight;
    public final int minHeight;
    public final Identifier replaceBlockId;

    public OreType(Block material, int clusterCount, int clusterSize, int minHeight, int maxHeight) {
        this(material, clusterCount, clusterSize, minHeight, maxHeight, STONE);
    }

    public OreType(Block material, int clusterCount, int clusterSize, int minHeight, int maxHeight, Identifier replaceBlockId) {
        this.block = material;
        this.clusterCount = clusterCount;
        this.clusterSize = clusterSize;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.replaceBlockId = replaceBlockId;
    }

    public boolean spawn(ChunkManager level, BedrockRandom random, Identifier replaceId, int x, int y, int z) {
        float piScaled = random.nextFloat() * (float) Math.PI;
        double scaleMaxX = (double) ((float) (x + 8) + MathHelper.sin(piScaled) * (float) clusterSize / 8.0F);
        double scaleMinX = (double) ((float) (x + 8) - MathHelper.sin(piScaled) * (float) clusterSize / 8.0F);
        double scaleMaxZ = (double) ((float) (z + 8) + MathHelper.cos(piScaled) * (float) clusterSize / 8.0F);
        double scaleMinZ = (double) ((float) (z + 8) - MathHelper.cos(piScaled) * (float) clusterSize / 8.0F);
        double scaleMaxY = (double) (y + random.nextInt(3) - 2);
        double scaleMinY = (double) (y + random.nextInt(3) - 2);

        for (int i = 0; i < clusterSize; ++i) {
            float sizeIncr = (float) i / (float) clusterSize;
            double scaleX = scaleMaxX + (scaleMinX - scaleMaxX) * (double) sizeIncr;
            double scaleY = scaleMaxY + (scaleMinY - scaleMaxY) * (double) sizeIncr;
            double scaleZ = scaleMaxZ + (scaleMinZ - scaleMaxZ) * (double) sizeIncr;
            double randSizeOffset = random.nextDouble() * (double) clusterSize / 16.0D;
            double randVec1 = (double) (MathHelper.sin((float) Math.PI * sizeIncr) + 1.0F) * randSizeOffset + 1.0D;
            double randVec2 = (double) (MathHelper.sin((float) Math.PI * sizeIncr) + 1.0F) * randSizeOffset + 1.0D;
            int minX = MathHelper.floor(scaleX - randVec1 / 2.0D);
            int minY = MathHelper.floor(scaleY - randVec2 / 2.0D);
            int minZ = MathHelper.floor(scaleZ - randVec1 / 2.0D);
            int maxX = MathHelper.floor(scaleX + randVec1 / 2.0D);
            int maxY = MathHelper.floor(scaleY + randVec2 / 2.0D);
            int maxZ = MathHelper.floor(scaleZ + randVec1 / 2.0D);

            for (int xSeg = minX; xSeg <= maxX; ++xSeg) {
                double xVal = ((double) xSeg + 0.5D - scaleX) / (randVec1 / 2.0D);

                if (xVal * xVal < 1.0D) {
                    for (int ySeg = minY; ySeg <= maxY; ++ySeg) {
                        double yVal = ((double) ySeg + 0.5D - scaleY) / (randVec2 / 2.0D);

                        if (xVal * xVal + yVal * yVal < 1.0D) {
                            for (int zSeg = minZ; zSeg <= maxZ; ++zSeg) {
                                double zVal = ((double) zSeg + 0.5D - scaleZ) / (randVec1 / 2.0D);

                                if (xVal * xVal + yVal * yVal + zVal * zVal < 1.0D) {
                                    if (level.getBlockId(xSeg, ySeg, zSeg) == replaceBlockId) {
                                        level.setBlockAt(xSeg, ySeg, zSeg, block);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
}
