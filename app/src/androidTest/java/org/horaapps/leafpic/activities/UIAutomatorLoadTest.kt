package org.horaapps.leafpic.activities

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiDevice.getInstance
import android.support.test.uiautomator.Until
import org.horaapps.leafpic.Constants.TEST_TARGET_PACKAGE
import org.horaapps.leafpic.assertThatFirstAlbumHasNameAndCount
import org.horaapps.leafpic.grantPermissionIfNeeded
import org.horaapps.leafpic.sortAlbumsBy
import org.horaapps.leafpic.startApp
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UIAutomatorLoadTest {
    private lateinit var uiDevice: UiDevice

    @Before
    @Throws(Exception::class)
    fun setUp() {
        uiDevice = getInstance(getInstrumentation())
    }

    @Test
    fun should_sort_albums_20_times_by_different_mode() {
        uiDevice.startApp(TEST_TARGET_PACKAGE)
        uiDevice.grantPermissionIfNeeded()

        uiDevice.wait(Until.hasObject(By.text("LeafPic (debug)")), 5000)

        repeat(20) {
            uiDevice.sortAlbumsBy("Date")
            uiDevice.assertThatFirstAlbumHasNameAndCount("Camera", "283")

            uiDevice.sortAlbumsBy("Name")
            uiDevice.assertThatFirstAlbumHasNameAndCount("网易云音乐相册", "1")
        }
    }

}