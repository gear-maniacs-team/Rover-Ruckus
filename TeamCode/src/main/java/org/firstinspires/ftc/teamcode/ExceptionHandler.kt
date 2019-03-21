package org.firstinspires.ftc.teamcode

import android.os.Environment
import android.text.format.DateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class ExceptionHandler {

    private val content = StringBuilder()

    fun addLine(text: CharSequence) {
        content.append(text).append('\n')
    }

    fun addData(text: String, vararg data: Any?) {
        content.append(String.format(text, data))
    }

    fun parseException(e: Exception) {
        content.append("Message: ")
            .append(e.message)
            .append("\n\n")
            .append("Stack Trace: ")

        e.stackTrace?.forEach {
            addLine(it.toString())
        }

        content.append('\n')
    }

    @JvmOverloads
    fun writeToFile(async: Boolean, tag: CharSequence? = null) {
        if (async) {
            writeLogFileAsync(content.toString(), tag)
        } else {
            writeLogFile(content.toString(), tag)
        }
    }

    fun clear() {
        content.clear()
    }

    companion object {
        private const val FOLDER_NAME = "FTC: Crash Logs/"

        @JvmStatic
        fun writeLogFileAsync(content: String, tag: CharSequence?) =
            GlobalScope.launch(Dispatchers.IO) { writeLogFile(content, tag) }

        @JvmStatic
        fun writeLogFile(content: String, tag: CharSequence?) {
            val folder = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)
            folder.mkdirs()

            val date: CharSequence = DateFormat.format("m:k_dd_MM", Calendar.getInstance())
            val name = StringBuilder(date)

            if (!tag.isNullOrBlank())
                name.append('_').append(tag)
            name.append(".txt")

            File(folder, name.toString()).writeText(content)
        }
    }
}
