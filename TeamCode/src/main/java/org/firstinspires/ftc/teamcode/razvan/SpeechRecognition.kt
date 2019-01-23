package org.firstinspires.ftc.teamcode.razvan

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.firstinspires.ftc.teamcode.R
import org.firstinspires.ftc.teamcode.motors.WheelMotors

@Autonomous(name = "SpeechRecognition", group = "Autonomous")
open class SpeechRecognition : LinearOpMode() {

    companion object {
        private const val LOG_TAG = "Music"
        private var speechDone = false
        private var speechSuccessful = false
    }

    private lateinit var wheelMotors: WheelMotors

    override fun runOpMode() = runBlocking {
        wheelMotors = WheelMotors(hardwareMap.dcMotor)

        Looper.prepare()
        waitForStart()

        speechDone = false
        speechSuccessful = false

        val listener = object : RecognitionListener {
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

                if (matches?.filterNotNull()?.any { it.contains("music", true) } == true) {
                    speechDone = true
                    speechSuccessful = true
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
                //speechDone = true
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

                if (matches.isNullOrEmpty()) {
                    Log.i(LOG_TAG, "Results are empty")
                    telemetry.addData(LOG_TAG, "Results are empty")
                    telemetry.update()
                    return
                }

                if (matches.filterNotNull().any { it.contains("music", true) }) {
                    speechSuccessful = true
                }
            }
        }

        try {
            withContext(Dispatchers.Main) {
                if (!SpeechRecognizer.isRecognitionAvailable(hardwareMap.appContext))
                    throw IllegalArgumentException("No Recognizer")
                val speech = SpeechRecognizer.createSpeechRecognizer(hardwareMap.appContext)
                speech.setRecognitionListener(listener)

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
                val mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.bond)
                mediaPlayer.start()

                mediaPlayer.setOnCompletionListener {
                    it.release()
                }
            }
        } catch (e: Exception) {
            val builder = StringBuilder(e.message + '\n')
            e.stackTrace.forEach { builder.append(it).append('\n') }
            telemetry.addData(LOG_TAG, builder.toString())
            telemetry.update()
        }

        delay(50000L)
    }
}