package me.nitkanikita.runeenchantments.util

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.util.BlockVector
import java.util.*

object VeinFinder {
    fun findConnectedBlocks(start: Block, targetType: Material, maxBlocks: Int = 1000): Set<Block> {
        val visited = mutableSetOf<BlockVector>()
        val result = mutableSetOf<Block>()
        val queue: Queue<Block> = LinkedList()

        println("start.type: ${start.type}, expected: $targetType")


        queue.add(start)
        visited.add(start.location.toVector().toBlockVector())
        result.add(start)

        while (queue.isNotEmpty() && result.size < maxBlocks) {
            val current = queue.poll()

            val neighbors = listOf(
                current.getRelative(1, 0, 0),
                current.getRelative(-1, 0, 0),
                current.getRelative(0, 1, 0),
                current.getRelative(0, -1, 0),
                current.getRelative(0, 0, 1),
                current.getRelative(0, 0, -1)
            )

            for (neighbor in neighbors) {
                val vector = neighbor.location.toVector().toBlockVector()

                if (neighbor.type == targetType && vector !in visited) {
                    visited.add(vector)
                    result.add(neighbor)
                    queue.add(neighbor)

                    if (result.size >= maxBlocks) break
                }
            }
        }

        return result
    }
}