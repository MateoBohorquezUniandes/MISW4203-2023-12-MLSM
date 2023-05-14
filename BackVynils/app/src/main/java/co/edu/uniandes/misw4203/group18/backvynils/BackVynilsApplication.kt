package co.edu.uniandes.misw4203.group18.backvynils

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.database.VinylRoomDatabase

class BackVynilsApplication: Application() {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}