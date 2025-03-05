package com.bridge.androidtechnicaltest

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.data.local.PupilDao
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
class PupilDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var pupilDao: PupilDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        pupilDao = database.pupilDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPupil_andRetrieveById() = runTest {
        val pupil = Pupil(
            pupilId = 1,
            name = "John Doe",
            country = "Kenya",
            image = "https://example.com/john.jpg",
            latitude = -1.286389,
            longitude = 36.817223
        )

        pupilDao.insert(pupil)
        val retrievedPupil = pupilDao.getById(1)

        assertNotNull(retrievedPupil)
        assertEquals(pupil.pupilId, retrievedPupil?.pupilId)
        assertEquals(pupil.name, retrievedPupil?.name)
        assertEquals(pupil.country, retrievedPupil?.country)
    }

    @Test
    fun insertMultiplePupils_andRetrieveSorted() = runTest {
        val pupils = listOf(
            Pupil(2, "Alice", "USA", "https://example.com/alice.jpg", 34.0522, -118.2437),
            Pupil(3, "Bob", "Canada", "https://example.com/bob.jpg", 45.4215, -75.6993)
        )

        pupilDao.insertAll(pupils)

        val pagingSource = pupilDao.pagingSource()
        val snapshot = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(2, snapshot.data.size)
        assertEquals("Alice", snapshot.data[0].name) // Ensure sorting by name
    }

    @Test
    fun deletePupilById() = runTest {
        val pupil = Pupil(4, "Charlie", "UK", "https://example.com/charlie.jpg", 51.5074, -0.1278)
        pupilDao.insert(pupil)

        pupilDao.deletePupilById(4)
        val retrievedPupil = pupilDao.getById(4)

        assertNull(retrievedPupil)
    }

    @Test
    fun clearAllPupils() = runTest {
        val pupils = listOf(
            Pupil(5, "David", "Germany", "https://example.com/david.jpg", 52.52, 13.405),
            Pupil(6, "Eve", "France", "https://example.com/eve.jpg", 48.8566, 2.3522)
        )
        pupilDao.insertAll(pupils)

        pupilDao.clearAll()
        val pagingSource = pupilDao.pagingSource()
        val snapshot = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(0, snapshot.data.size)
    }
}
