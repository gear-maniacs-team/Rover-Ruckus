package org.firstinspires.ftc.teamcode.razvan

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.firstinspires.ftc.teamcode.R
import java.util.*

@Disabled
@Autonomous(name = "SpeechRecognition")
class SpeechRecognition : LinearOpMode(), RecognitionListener {

    companion object {
        private const val LOG_TAG = "Music/Speech"

        private const val RESULT_MUSIC = "music"
        private const val RESULT_NAME = "name"

        private var speechDone = false
        private var speechSuccessful = false
        private var speechResult = ""
    }

    //private lateinit var wheelMotors: WheelMotors

    override fun runOpMode() = runBlocking {
        //wheelMotors = WheelMotors(hardwareMap.dcMotor)

        waitForStart()

        speechDone = false
        speechSuccessful = false
        speechResult = ""

        try {
            withContext(Dispatchers.Main) {
                if (!SpeechRecognizer.isRecognitionAvailable(hardwareMap.appContext))
                    throw IllegalArgumentException("No Recognizer Found")
                val speech = SpeechRecognizer.createSpeechRecognizer(hardwareMap.appContext)
                speech.setRecognitionListener(this@SpeechRecognition)

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                    putExtra(
                            RecognizerIntent.EXTRA_CALLING_PACKAGE, hardwareMap.appContext.packageName
                    )
                    putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                }

                speech.startListening(intent)

                while (!speechDone)
                    delay(100)

                speech.stopListening()
            }

            if (speechSuccessful) {
                handleResult()
            }
        } catch (e: Exception) {
            val builder = StringBuilder(e.message + '\n')
            e.stackTrace.forEach { builder.append(it).append('\n') }

            telemetry.addData(LOG_TAG, builder.toString())
            telemetry.update()
        }

        delay(50000L)
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.i(LOG_TAG, "onReadyForSpeech")
        telemetry.addData(LOG_TAG, "onReadyForSpeech")
        telemetry.update()
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i(LOG_TAG, "onRmsChanged: $rmsdB")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i(LOG_TAG, "onBufferReceived: $buffer")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val matches = partialResults?.getStringArrayList(RecognizerIntent.EXTRA_PARTIAL_RESULTS)
                ?.filterNotNull() ?: return

        if (matches.any { it.contains(RESULT_MUSIC, true) }) {
            speechDone = true
            speechSuccessful = true
            speechResult = RESULT_MUSIC
        } else if (matches.any { it.contains(RESULT_NAME, true) }) {
            speechDone = true
            speechSuccessful = true
            speechResult = RESULT_NAME
        }

        Log.i(LOG_TAG, "onPartialResults")
        telemetry.addData(LOG_TAG, "onPartialResults")
        telemetry.update()
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i(LOG_TAG, "onEvent $eventType")
    }

    override fun onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech")
        telemetry.addData(LOG_TAG, "onBeginningOfSpeech")
        telemetry.update()
    }

    override fun onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech")
        telemetry.addData(LOG_TAG, "onEndOfSpeech")
        telemetry.update()
    }

    override fun onError(error: Int) {
        Log.e(LOG_TAG, "onError: $error")
        telemetry.addData(LOG_TAG, "onError: $error")
        telemetry.update()
        speechDone = true
    }

    override fun onResults(results: Bundle) {
        speechDone = true
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.filterNotNull() ?: return

        if (matches.isNullOrEmpty()) {
            Log.i(LOG_TAG, "Results are empty")
            telemetry.addData(LOG_TAG, "Results are empty")
            telemetry.update()
            return
        }

        if (matches.any { it.contains(RESULT_MUSIC, true) }) {
            speechSuccessful = true
            speechResult = RESULT_MUSIC
        } else if (matches.any { it.contains(RESULT_NAME, true) }) {
            speechSuccessful = true
            speechResult = RESULT_NAME
        }
    }

    private fun handleResult() {
        when (speechResult) {
            RESULT_MUSIC -> {
                val mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.bond)
                mediaPlayer.start()

                mediaPlayer.setOnCompletionListener {
                    it.release()
                }
            }
            RESULT_NAME -> {
                val textToSpeech = TextToSpeech(hardwareMap.appContext) { status ->

                }
                textToSpeech.language = Locale.ENGLISH

                val text = "Name's Bond. James Bond."
                if (Build.VERSION.SDK_INT > 21) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, RESULT_NAME)

                    textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onDone(utteranceId: String) {
                            textToSpeech.shutdown()
                            telemetry.addData(LOG_TAG, "onDone")
                            telemetry.update()
                        }

                        override fun onError(utteranceId: String?) = Unit

                        override fun onStart(utteranceId: String?) {
                            telemetry.addData(LOG_TAG, "onStart")
                            telemetry.update()
                        }

                        override fun onError(utteranceId: String, errorCode: Int) {
                            super.onError(utteranceId, errorCode)
                            telemetry.addData(LOG_TAG, "onError $errorCode")
                            telemetry.update()
                        }
                    })
                } else {
                    @Suppress("DEPRECATION")
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                }
            }
            else -> throw IllegalArgumentException("No Valid Result")
        }
    }
}
