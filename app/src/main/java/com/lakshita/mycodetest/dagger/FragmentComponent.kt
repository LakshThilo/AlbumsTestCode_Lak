package com.lakshita.mycodetest.dagger

import com.lakshita.mycodetest.db.AlbumDao
import com.lakshita.mycodetest.network.AlbumsApiService
import com.lakshita.mycodetest.repo.AlbumRepository
import com.lakshita.mycodetest.view.AlbumListFragment
import dagger.Component
import dagger.Module
import dagger.Provides

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [AlbumRepositoryModule::class])
interface FragmentComponent {
    fun inject(fragment: AlbumListFragment)
    fun albumRepository() : AlbumRepository
}

@Module
class AlbumRepositoryModule {

    @Provides
    @PerFragment
    fun provideAlbumRepository(albumDao: AlbumDao, albumsApiService: AlbumsApiService) : AlbumRepository {
        return AlbumRepository(albumDao, albumsApiService)
    }
}