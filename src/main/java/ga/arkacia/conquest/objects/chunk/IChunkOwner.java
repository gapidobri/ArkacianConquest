package ga.arkacia.conquest.objects.chunk;

import org.bukkit.Chunk;

public interface IChunkOwner {
    /*
    - Status of the chunk [ free / claimed ]
    - CHANGE VOID TO RESPONSE (can you claim this chunk, return who claimed the chunk)
     */
    void claimChunk(Chunk chunk);
    // void unclaimChunk(Chunk chunk);
}
