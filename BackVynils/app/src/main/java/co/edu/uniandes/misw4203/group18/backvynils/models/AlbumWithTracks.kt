package co.edu.uniandes.misw4203.group18.backvynils.models

import androidx.room.Embedded
import androidx.room.Relation


class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "fkAlbumId"
    )
    val tracks: List<Track>
) {
    val numberOfTracks: String
        get() = "${tracks.size}\nTracks"
}