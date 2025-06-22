package me.nitkanikita.runeenchantments.util

import org.bukkit.Material
import org.bukkit.block.Block
import java.util.*

object VeinFinder {
    fun findConnectedBlocks(start: Block, type: Material, maxBlocks: Int): Set<Block> {
        val visited = mutableSetOf<Block>()
        val queue: Queue<Block> = LinkedList()
        queue.add(start)

        while (queue.isNotEmpty() && visited.size < maxBlocks) {
            val current = queue.poll()
            if (current.type != type || !visited.add(current)) continue

            for (dx in -1..1) for (dy in -1..1) for (dz in -1..1) {
                if (dx == 0 && dy == 0 && dz == 0) continue
                val neighbor = current.getRelative(dx, dy, dz)
                if (!visited.contains(neighbor) && neighbor.type == type) queue.add(neighbor)
            }
        }
        return visited
    }
}