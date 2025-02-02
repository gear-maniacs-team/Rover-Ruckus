package org.firstinspires.ftc.teamcode.utils

import android.content.Context
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector
import java.util.concurrent.Executors
import java.util.concurrent.Future

class VuforiaManager {

    private lateinit var webcamName: WebcamName
    @Volatile
    private var initializing = false
    private val executor = Executors.newSingleThreadExecutor()
    private var startTask: Future<*>? = null
    private var vuforia: VuforiaLocalizer? = null
    private var tfod: TFObjectDetector? = null

    fun startDetectorAsync(hardwareMap: HardwareMap) {
        if (!this::webcamName.isInitialized)
            webcamName = hardwareMap.get(WebcamName::class.java, "Webcam 1")

        if (initializing) return

        startTask = executor.submit {
            initializing = true
            initVuforia()

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod(hardwareMap.appContext)
            } else {
                throw IllegalStateException("This device is not compatible with TFOD")
            }

            initializing = false
        }
    }

    fun stopCamera() {
        startTask?.cancel(true)
        tfod?.shutdown()
    }

    private fun initVuforia() {
        val parameters = VuforiaLocalizer.Parameters().apply {
            vuforiaLicenseKey = VUFORIA_KEY
            //cameraDirection = VuforiaLocalizer.CameraDirection.BACK
            cameraName = webcamName
        }

        vuforia = ClassFactory.getInstance().createVuforia(parameters)
    }

    private fun initTfod(context: Context) {
        val tfodMonitorViewId =
            context.resources.getIdentifier("tfodMonitorViewId", "id", context.packageName)
        val tfodParams = TFObjectDetector.Parameters(tfodMonitorViewId)

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParams, vuforia).apply {
            loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL)
        }
    }

    fun searchForGold(): Boolean {
        val updatedRecognitions = tfod?.updatedRecognitions

        if (updatedRecognitions != null)
            return updatedRecognitions.any { it.label == LABEL_GOLD_MINERAL }

        return false
    }

    fun waitForDetector() {
        try {
            startTask?.get()
            tfod?.activate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TFOD_MODEL_ASSET = "RoverRuckus.tflite"
        private const val LABEL_GOLD_MINERAL = "Gold Mineral"
        private const val LABEL_SILVER_MINERAL = "Silver Mineral"
        private const val VUFORIA_KEY =
            "AZnVnoj/////AAABmdXzVSC7bkZik9EURkca9g5GwHTQjL0SB5CABkSEajM1oX/nSOWoXxcxH/watnjKf3WlWcGhyPvV0E8eMNZmTbTgrB/8OJhqAflMV+CjgBtERmweuXjLiPcvEgJNrZD7USn+LK53L0VuSYdi4NwJxy7ypbse7jbXlOmJVgogCXbD4+yjYDbnVmBkkMQMhLgIFQZ0wRApvdxc7R/O/rhsQfWrWWekxjIp4wNeYh5JBsCrCRjdPu1P7QLKAMSOpK5lXqJjmD36TPDxqrQEGfdKxkMe2SJta/3tyzc+v/mFRmNDJjqVMYu69eEy6jh7u/KQA2Uj4pdcIfnZhMWwBO58guP2TPl5HCof4weEEUI6ZF8w"
    }
}
