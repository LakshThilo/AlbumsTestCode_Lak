package com.lakshita.mycodetest


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lakshita.mycodetest.db.AlbumDao
import com.lakshita.mycodetest.db.AlbumDatabase
import com.lakshita.mycodetest.model.Album
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
class AlbumDatabaseTest {

    private lateinit var albumDao: AlbumDao
    private lateinit var db: AlbumDatabase

    private val albums = listOf(
        Album(1, 5, "Sticky Fingers"),
        Album(1, 6, "Exile On Main Street"),
        Album(1, 9, "Let It Bleed"),
        Album(1, 10, "Beggars Banquet"),
        Album(1, 19, "Tattoo You")
    )

    @Before
    fun createDb() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AlbumDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        albumDao = db.getAlbumDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun createAlbumListAndGetAlbum() {
        albumDao.insertAll(albums)
        assertEquals(albums[0], albumDao.getAlbum(5))
    }

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun createAndRetrieveAlbumLists() {
        albumDao.insertAll(albums)
        assertEquals(albumDao.getAllAlbums().getOrAwaitValue().size, albums.size)
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

}

