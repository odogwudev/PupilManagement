package com.bridge.androidtechnicaltest.di

import android.content.Context
import androidx.paging.Pager
import androidx.room.Room
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.repository.IPupilRepository
import com.bridge.androidtechnicaltest.data.local.PupilDao
import com.bridge.androidtechnicaltest.data.remote.PupilApi
import com.bridge.androidtechnicaltest.data.repository.PupilRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "TechnicalTestDb"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePupilDao(db: AppDatabase): PupilDao {
        return db.pupilDao
    }

    @Provides
    @Singleton
    fun providePupilRepository(
        pupilPager: Pager<Int, Pupil>, api: PupilApi, db: AppDatabase
    ): IPupilRepository {
        return PupilRepository(pupilPager, db, api)
    }
}
