package com.lakshita.mycodetest

import android.app.Application
import com.lakshita.mycodetest.dagger.AlbumApiModule
import com.lakshita.mycodetest.dagger.AlbumDaoModule
import com.lakshita.mycodetest.dagger.AppComponent
import com.lakshita.mycodetest.dagger.AppModule
import com.lakshita.mycodetest.dagger.*

class AlbumListApp : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .albumApiModule(AlbumApiModule())
            .appModule(AppModule(this))
            .albumDaoModule(AlbumDaoModule(this))
            .build()
    }

}