package org.firstinspires.ftc.teamcode.razvan

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.firstinspires.ftc.teamcode.R
import org.firstinspires.ftc.teamcode.motors.WheelMotors

@Autonomous(name = "SpeechRecognition")
class SpeechRecognition : LinearOpMode() {

    companion object {
        private const val LOG_TAG = "Music"
        private var speechDone = false
        private var speechSuccessful = false
    }

    private lateinit var wheelMotors: WheelMotors

    override fun runOpMode() {
        wheelMotors = WheelMotors(hardwareMap.dcMotor)

        waitForStart()

        speechDone = false
        speechSuccessful = false
        lateinit var words: List<String>

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
                Log.i(LOG_TAG, "onPartialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.i(LOG_TAG, "onEvent $eventType")
            }

            override fun onBeginningOfSpeech() {
                Log.i(LOG_TAG, "onBeginningOfSpeech")
                telemetry.addData(LOG_TAG, "onBeginningOfSpeech")
            }

            override fun onEndOfSpeech() {
                Log.i(LOG_TAG, "onEndOfSpeech")
                telemetry.addData(LOG_TAG, "onEndOfSpeech")
                telemetry.update()
                speechDone = true
            }

            override fun onError(error: Int) {
                Log.e(LOG_TAG, "onError: $error")
                telemetry.addData(LOG_TAG, "onError: $error")
                telemetry.update()
                speechDone = true
            }

            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches.isNullOrEmpty()) {
                    Log.i(LOG_TAG, "Results are empty")
                    telemetry.addData(LOG_TAG, "Results are empty")
                    telemetry.update()
                    return
                }

                speechSuccessful = true

                words = matches.filterNotNull()
            }
        }

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
        }

        // Block Current Thread until the SpeechRecognizer is started on the Main Thread
        runBlocking {
            withContext(Dispatchers.Main) {
                speech.startListening(intent)
            }
        }

        while (!speechDone)
            Thread.sleep(50)

        speech.stopListening()

        if (speechSuccessful) {
            if (words.any { it.contains("music", true) }) {
                val mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.bond)
                mediaPlayer.prepare()
                mediaPlayer.start()

                mediaPlayer.setOnCompletionListener {
                    it.release()
                }
            }
        }

        Thread.sleep(20000L)
    }
}