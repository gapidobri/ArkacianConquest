package ga.arkacia.conquest.objects.chunk;

import ga.arkacia.conquest.objects.CreationResponse;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ClaimedChunk {
    static List<ClaimedChunk> claimedChunks = new ArrayList<>();

    Chunk chunk;
    IChunkOwner owner;

    public ClaimedChunk(Chunk chunk, IChunkOwner owner) {
        this.chunk = chunk;
        this.owner = owner;
    }

    public static CreationResponse claimChunk(ClaimedChunk claimedChunk) {
        for (ClaimedChunk cc : claimedChunks) {
            if (cc.chunk == claimedChunk.chunk) {
                // TODO: Check if this works
                return CreationResponse.EXISTS;
            }
        }
        claimedChunks.add(claimedChunk);
        return CreationResponse.OK;
    }
}
