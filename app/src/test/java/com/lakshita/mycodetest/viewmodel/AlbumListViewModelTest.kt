package com.lakshita.mycodetest.viewmodel




import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lakshita.mycodetest.TestCoroutineRule
import com.lakshita.mycodetest.db.AlbumDao
import com.lakshita.mycodetest.model.Album
import com.lakshita.mycodetest.network.AlbumsApiService
import com.lakshita.mycodetest.repo.AlbumRepository
import com.lakshita.mycodetest.viewmodel.AlbumListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val dao: AlbumDao = mock(AlbumDao::class.java)!!
    private val api: AlbumsApiService = mock(AlbumsApiService::class.java)!!

    @Mock
    private lateinit var albumObserver: Observer<List<Album>>
    private val validAlbumList = listOf(Album(1, 2, "abc"), Album(1, 3, "def"), Album(1, 4, "xyz"))
    private val liveDataAlbums : MutableLiveData<List<Album>> by lazy {
        MutableLiveData<List<Album>>()
    }

    private val emptyAlbumList = listOf<Album>()

    @ExperimentalCoroutinesApi
    @Test
    fun whenDaoReturnsValidData_ViewModelDataStateIsTrue() {
        testCoroutineRule.runBlockingTest {
            liveDataAlbums.value = validAlbumList
                `when`(dao.getAllAlbums()).thenReturn(liveDataAlbums)
            val repository = AlbumRepository(dao, api)
            val viewModel = AlbumListViewModel(repository)
            viewModel.albumList.observeForever(albumObserver)
            verify(albumObserver).onChanged(validAlbumList)
            assertEquals(3, viewModel.albumList.value?.size)
            viewModel.dataState.value?.let { assertTrue(it) }
            viewModel.albumList.removeObserver(albumObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun whenNetworkAvailable_ViewModelDataStateIsFalse() {
        testCoroutineRule.runBlockingTest {
            liveDataAlbums.value = emptyAlbumList
            `when`(dao.getAllAlbums()).thenReturn(liveDataAlbums)

            val repository = AlbumRepository(dao, api)
            val viewModel = AlbumListViewModel(repository)
            viewModel.albumList.observeForever(albumObserver)
            verify(albumObserver).onChanged(emptyAlbumList)
            assertEquals(0, viewModel.albumList.value?.size)
            viewModel.dataState.value?.let { assertTrue(it) }

            liveDataAlbums.value = validAlbumList
            verify(albumObserver).onChanged(validAlbumList)
            assertEquals(3, viewModel.albumList.value?.size)
            viewModel.dataState.value?.let { assertTrue(it) }
            viewModel.albumList.removeObserver(albumObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun whenAlbumDataIsEmptyAndNetworkErrorOccurs_ViewModelDataStateIsFalse() {
        testCoroutineRule.runBlockingTest {
            liveDataAlbums.value = emptyAlbumList
            `when`(dao.getAllAlbums()).thenReturn(liveDataAlbums)
            `when`(api.getAlbums()).thenReturn(null)
            val repository = AlbumRepository(dao, api)
            val viewModel = AlbumListViewModel(repository)
            viewModel.albumList.observeForever(albumObserver)
            viewModel.dataState.value?.let { assertFalse(it) }
            viewModel.albumList.removeObserver(albumObserver)
        }
    }
}
