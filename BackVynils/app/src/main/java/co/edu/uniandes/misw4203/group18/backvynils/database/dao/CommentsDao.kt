package co.edu.uniandes.misw4203.group18.backvynils.database.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments_table where id = :commentId ORDER BY rating DESC")
    fun getComments(commentId: Int): Comments

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comments)

    @Query("DELETE FROM comments_table")
    suspend fun clear():Void

    @Query("DELETE FROM comments_table WHERE id = :commentId")
    suspend fun deleteAll(commentId: Int):Void
}