package co.edu.uniandes.misw4203.group18.backvynils.viewmodels

import android.app.Application
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.group18.backvynils.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.repositories.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(application: Application) :  AndroidViewModel(application) {
    private val _musicians = MutableLiveData<List<Artist>>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    private val artistsRepository = ArtistRepository(application,
        VinylRoomDatabase.getDatabase(application.applicationContext).artistsDao())

    val musicians: LiveData<List<Artist>>
        get() = _musicians

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    internal fun refreshDataFromNetwork() {
       try {
           viewModelScope.launch (Dispatchers.Default){
               withContext(Dispatchers.IO){
                   var data = artistsRepository.updateMusicianData()
                   _musicians.postValue(data)
               }
               _eventNetworkError.postValue(false)
               _isNetworkErrorShown.postValue(false)
           }
       }
       catch (e:Exception){
           _eventNetworkError.value = true
       }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
