package org.horaapps.leafpic.adapters

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.horaapps.leafpic.data.Album
import org.horaapps.leafpic.data.AlbumSettings
import org.horaapps.leafpic.data.sort.SortingMode
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.util.Calendar

import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat


@RunWith(AndroidJUnit4::class)
class AlbumsAdapterTest {

    private lateinit var adapter: AlbumsAdapter
    private lateinit var album_b_20_20010101: Album
    private lateinit var album_c_10_20000102: Album
    private lateinit var album_a_30_20000101: Album

    @Before
    @Throws(Exception::class)
    fun setUp() {
        adapter = AlbumsAdapter(InstrumentationRegistry.getTargetContext(), null)
        val calendar = Calendar.getInstance()
        calendar.set(2000, 1, 1)
        album_a_30_20000101 = Album("/AlbumsA", "A", 10, calendar.timeInMillis)
        album_a_30_20000101.withSettings(AlbumSettings.getDefaults())
        calendar.set(2001, 1, 1)
        album_b_20_20010101 = Album("/AlbumsB", "B", 20, calendar.timeInMillis)
        album_b_20_20010101.withSettings(AlbumSettings.getDefaults())
        calendar.set(2000, 1, 2)
        album_c_10_20000102 = Album("/AlbumsC", "C", 30, calendar.timeInMillis)
        album_c_10_20000102.withSettings(AlbumSettings.getDefaults())

        adapter.add(album_a_30_20000101)
        adapter.add(album_b_20_20010101)
        adapter.add(album_c_10_20000102)
    }

    @Test
    fun should_sort_by_name() {
        with(adapter) {
            changeSortingMode(SortingMode.NAME)

            assertThat(get(0), `is`(album_c_10_20000102))
            assertThat(get(1), `is`(album_b_20_20010101))
            assertThat(get(2), `is`(album_a_30_20000101))
        }
    }

    @Test
    fun should_sort_by_date() {
        with(adapter) {
            changeSortingMode(SortingMode.DATE)

            assertThat(get(0), `is`(album_b_20_20010101))
            assertThat(get(1), `is`(album_c_10_20000102))
            assertThat(get(2), `is`(album_a_30_20000101))
        }
    }

    @Test
    fun should_sort_by_size() {
        with(adapter) {
            changeSortingMode(SortingMode.SIZE)

            assertThat(get(0), `is`(album_c_10_20000102))
            assertThat(get(1), `is`(album_b_20_20010101))
            assertThat(get(2), `is`(album_a_30_20000101))
        }
    }
}