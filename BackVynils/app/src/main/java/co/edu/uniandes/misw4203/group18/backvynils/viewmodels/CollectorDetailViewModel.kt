package co.edu.uniandes.misw4203.group18.backvynils.viewmodels
import android.app.Application
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.repositories.CollectorRepository
class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _collector = MutableLiveData<Collector>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    private val collectorRepository = CollectorRepository(application)

    val collectors: LiveData<Collector> get() = _collector
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork(){
        collectorRepository.updateCollectorDetailData("1",{
            _collector.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },
            {
                _eventNetworkError.value = true
            })
    }

    fun onNetworkErrorShown(){
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CollectorViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}