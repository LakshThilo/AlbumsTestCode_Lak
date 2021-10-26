package com.lakshita.mycodetest.dagger

import androidx.room.Room
import com.lakshita.mycodetest.AlbumListApp
import com.lakshita.mycodetest.MainActivity
import com.lakshita.mycodetest.db.AlbumDao
import com.lakshita.mycodetest.db.AlbumDatabase
import com.lakshita.mycodetest.network.AlbumsApiService
import com.lakshita.mycodetest.utils.APIEndPoint
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AppModule::class,
    AlbumApiModule::class,
    AlbumDaoModule::class
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun context(): AlbumListApp
    fun albumApiService(): AlbumsApiService
    fun albumDao(): AlbumDao
}

@Module
class AppModule(private val context: AlbumListApp) {
    @Provides
    @Singleton fun provideContext() = context
}

@Module
class AlbumApiModule {


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(APIEndPoint.appBaseURL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumApiService(retrofit: Retrofit) : AlbumsApiService {
        return retrofit.create(AlbumsApiService::class.java)
    }
}

@Module
class AlbumDaoModule(private val context: AlbumListApp) {

    @Provides
    @Singleton
    fun provideAlbumDataBase() : AlbumDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlbumDatabase::class.java,
            "database_album"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(albumDatabase: AlbumDatabase): AlbumDao {
        return albumDatabase.getAlbumDao()
    }
}


