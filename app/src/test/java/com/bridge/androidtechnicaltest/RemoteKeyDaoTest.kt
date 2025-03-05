package com.bridge.androidtechnicaltest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.RemoteKeyDao
import com.bridge.androidtechnicaltest.data.local.RemoteKeyEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RemoteKeyDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var remoteKeyDao: RemoteKeyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        remoteKeyDao = database.remoteKeyDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertRemoteKey_andRetrieveById() = runTest {
        val remoteKey = RemoteKeyEntity(id = "pupil", nextOffset = 2)

        remoteKeyDao.insert(remoteKey)
        val retrievedKey = remoteKeyDao.getById("pupil")

        assertNotNull(retrievedKey)
        assertEquals(remoteKey.id, retrievedKey?.id)
        assertEquals(remoteKey.nextOffset, retrievedKey?.nextOffset)
    }

    @Test
    fun deleteRemoteKeyById() = runTest {
        val remoteKey = RemoteKeyEntity(id = "pupil", nextOffset = 5)
        remoteKeyDao.insert(remoteKey)

        remoteKeyDao.deleteById("pupil")
        val retrievedKey = remoteKeyDao.getById("pupil")

        assertNull(retrievedKey)
    }
}
