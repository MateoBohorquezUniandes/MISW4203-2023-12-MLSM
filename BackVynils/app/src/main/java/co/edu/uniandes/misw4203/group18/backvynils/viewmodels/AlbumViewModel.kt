package co.edu.uniandes.misw4203.group18.backvynils.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.group18.backvynils.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks
import co.edu.uniandes.misw4203.group18.backvynils.models.Track
import co.edu.uniandes.misw4203.group18.backvynils.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val _albums = MutableLiveData<List<Album>>()
    private val _albumDetail = MutableLiveData<AlbumWithTracks>()

    private var _eventNetworkError = MutableLiveData(false)
    private var _isNetworkErrorShown = MutableLiveData(false)

    private val albumsRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao()
    )

    val albums: LiveData<List<Album>>
        get() = _albums

    val albumWithTracks: LiveData<AlbumWithTracks?>
        get() = _albumDetail

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }


    internal fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val albums = albumsRepository.updateAlbumData()
                    _albums.postValue(albums)
                }
                withContext(Dispatchers.IO) {
                    val connectivityErrors = albumsRepository.isNetworkConnectivityError()
                    _eventNetworkError.postValue(connectivityErrors)
                    _isNetworkErrorShown.postValue(connectivityErrors)
                }

            }
        } catch (ex: Exception) {
            _eventNetworkError.value = true
        }
    }

    fun getAlbumDetailDataFromNetwork(albumId: Int) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val album = albumsRepository.updateDetailAlbumData(albumId)
                    _albumDetail.postValue(album)
                }
                withContext(Dispatchers.IO) {
                    val connectivityErrors = albumsRepository.isNetworkConnectivityError()
                    _eventNetworkError.postValue(connectivityErrors)
                    _isNetworkErrorShown.postValue(connectivityErrors)
                }
            }
        } catch (ex: Exception) {
            _eventNetworkError.value = true
        }
    }

    internal fun storeAlbumListDataFromNetwork() {
        try {
            if (!_albums.value.isNullOrEmpty()) {
                viewModelScope.launch {
                    albumsRepository.insertAlbums(_albums.value!!)
                }
            }
        } catch (ex: Exception) {
            Log.d("albumViewModel", "Failed to store albums locally: $ex")
        }
    }

    internal fun storeAlbumWithTracksFromNetwork(albumDetail: AlbumWithTracks) {
        try {
            if (_albumDetail.value != null) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        albumsRepository.insertAlbumWithTracks(albumDetail)
                    }
                }
            }
        } catch (ex: Exception) {
            Log.d("albumViewModel", "Failed to store album with tracks locally: $ex")
        }
    }

    fun postAlbum(album: Album) {
        albumsRepository.postAlbum(
            album,
            {
                // The album was successfully posted
                refreshDataFromNetwork() // Refresh the list of albums
                getAlbumDetailDataFromNetwork(album.albumId) // Refresh album detail
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
