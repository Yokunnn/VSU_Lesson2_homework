package ru.zakablukov.vsu_lesson2

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri

class ShareSecretKeyContentResolver(
    private val contentResolver: ContentResolver
) {
    @SuppressLint("Range")
    fun fetchSecretKey(): String {
        val uri = Uri.parse("content://dev.surf.android.provider/text")
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        var msg = "Key not received"

        cursor?.use {
            if (it.moveToFirst()) {
                msg = it.getString(it.getColumnIndex("text"))
            }
        }

        cursor?.close()
        return msg
    }
}