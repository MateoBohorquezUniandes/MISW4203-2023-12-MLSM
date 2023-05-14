package co.edu.uniandes.misw4203.group18.backvynils.viewmodels

import android.app.Application
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Album.Track
import co.edu.uniandes.misw4203.group18.backvynils.repositories.AlbumRepository

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {
    private val _albums = MutableLiveData<List<Album>>()

    private var _eventNetworkError = MutableLiveData(false)
    private var _isNetworkErrorShown = MutableLiveData(false)

    private val albumsRepository = AlbumRepository(application)

    val albums: LiveData<List<Album>>
        get() = _albums

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }


    internal fun refreshDataFromNetwork() {

        albumsRepository.updateAlbumData(
            {
                _albums.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },
            {
                _eventNetworkError.value = true
            }
        )
    }


    fun postAlbum(album: Album) {
    albumsRepository.postAlbum(
        album,
        {
            // The album was successfully posted
            refreshDataFromNetwork() // Refresh the list of albums
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },
        {
            // An error occurred while posting the album
            _eventNetworkError.value = true
            _isNetworkErrorShown.value = true
        }
    )
    }

    fun addTrackToAlbum(albumId: Int, track: Track) {
        albumsRepository.postTrackToAlbum(
            albumId,
            track,
            {
                // The track was successfully added
                refreshDataFromNetwork() // Refresh the list of albums
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },
            {
                // An error occurred while adding the track
                _eventNetworkError.value = true
                _isNetworkErrorShown.value = true
            }
        )
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
