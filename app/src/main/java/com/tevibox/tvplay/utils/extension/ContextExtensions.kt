package com.tevibox.tvplay.utils.extension

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import java.io.File

inline val Context.inputMethodManager: InputMethodManager?
    get() = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.getVolume(): Int {
    val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    return audioService.getStreamVolume(AudioManager.STREAM_MUSIC)
}

fun Context.volumeUp() {
    val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioService.adjustStreamVolume(
        AudioManager.STREAM_MUSIC,
        AudioManager.ADJUST_RAISE,
        AudioManager.FLAG_SHOW_UI
    )
}

fun Context.volumeDown() {
    val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    try {
        audioService.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_LOWER,
            AudioManager.FLAG_SHOW_UI
        )
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}

fun Context.getUriForString(uri: String): Uri =
    FileProvider.getUriForFile(
        this,
        applicationContext.packageName + ".provider",
        File(uri)
    )