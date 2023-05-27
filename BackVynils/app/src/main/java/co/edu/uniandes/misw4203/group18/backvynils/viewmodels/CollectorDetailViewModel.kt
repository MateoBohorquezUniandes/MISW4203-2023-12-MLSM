package co.edu.uniandes.misw4203.group18.backvynils.viewmodels
import android.app.Application
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.group18.backvynils.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments
import co.edu.uniandes.misw4203.group18.backvynils.repositories.CollectorRepository
import co.edu.uniandes.misw4203.group18.backvynils.repositories.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(application: Application, id: Int) : AndroidViewModel(application) {
    private val _collector = MutableLiveData<Collector>()
    private val _artist = MutableLiveData<List<Artist>>()
    private val _comment = MutableLiveData<Comments>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    private val collectorRepository = CollectorRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).collectorsDao())
    private val commentsRepository = CommentsRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).commentsDao())


    val collectorId: Int = id
    val collector: LiveData<Collector> get() = _collector
    val artist: LiveData<List<Artist>> get() = _artist
    val comment: LiveData<Comments> get() = _comment

    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    internal fun refreshDataFromNetwork(){
        try{
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = collectorRepository.refreshCollectorDetailData(collectorId)
                    _collector.postValue(data)
                    val data2 = mutableListOf<Artist>()
                    data2.add(0,Artist(243,"Mateo","1998-07-26T00:00:00.000Z","El puto amo","Imagen"))
                    //data2.add(artistRepository.metodounrepo(data.favoritePerformers))
                    _artist.postValue(data2)
                    var data3 = commentsRepository.getCommentCollector(data.comments,collectorId)
                    _comment.postValue(data3)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown(){
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val id:Int) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CollectorDetailViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CollectorDetailViewModel(app,id) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}