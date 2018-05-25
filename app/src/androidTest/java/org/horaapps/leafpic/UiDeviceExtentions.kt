package org.horaapps.leafpic

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject2
import android.support.test.uiautomator.Until
import android.support.v7.widget.RecyclerView
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert


fun UiDevice.startLauncher() {
    // Start from the home screen
    pressHome()

    // Wait for launcher
    val launcherPackage = launcherPackageName
    assertThat(launcherPackage, notNullValue())
    wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
            5000)
}

fun UiDevice.sortAlbumsBy(type: String) {
    findObject(By.descContains("Sort")).click()
    wait(Until.hasObject(By.text(type)), 5000)
    findObject(By.text(type)).click()
}

fun UiDevice.startApp(packageName: String) {
    // Launch the app
    val context = InstrumentationRegistry.getContext()
    val intent = context.packageManager
            .getLaunchIntentForPackage(packageName)
    // Clear out any previous instances
    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)

    // Wait for the app to appear
    wait(Until.hasObject(By.pkg(packageName).depth(0)),
            5000)
}

fun UiDevice.assertThatFirstAlbumHasNameAndCount(name: String, count: String) {
    val firstCard = findObject(By.clazz(RecyclerView::class.java)).children[0]
    Assert.assertThat<UiObject2>(firstCard.findObject(By.text(name)), notNullValue())
    Assert.assertThat<UiObject2>(firstCard.findObject(By.text(count)), notNullValue())
}

fun UiDevice.grantPermissionIfNeeded() {
    val allowButton = findObject(By.text("Allow"))
    if (allowButton != null && allowButton.isClickable) {
        allowButton.click()
    }
}