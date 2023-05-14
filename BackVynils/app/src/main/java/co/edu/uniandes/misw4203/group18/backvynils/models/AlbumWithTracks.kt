package co.edu.uniandes.misw4203.group18.backvynils.models

import androidx.room.Embedded
import androidx.room.Relation


class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "trackId"
    )
    val tracks: List<Track>
)