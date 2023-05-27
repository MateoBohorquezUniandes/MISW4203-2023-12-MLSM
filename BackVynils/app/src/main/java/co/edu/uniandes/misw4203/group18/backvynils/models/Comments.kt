package co.edu.uniandes.misw4203.group18.backvynils.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_table")
data class Comments(
    @PrimaryKey val id: Int,
    val description: String,
    val rating: Int
) {
}