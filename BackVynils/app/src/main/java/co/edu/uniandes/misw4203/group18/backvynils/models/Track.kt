package co.edu.uniandes.misw4203.group18.backvynils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks_table",
    foreignKeys = [ForeignKey(
        entity = Album::class,
        parentColumns = arrayOf("albumId"),
        childColumns = arrayOf("fkAlbumId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Track(
    @PrimaryKey val trackId: Int,
    @ColumnInfo(index = true) val fkAlbumId: Int,
    val name: String = "",
    val duration: String = ""
)
