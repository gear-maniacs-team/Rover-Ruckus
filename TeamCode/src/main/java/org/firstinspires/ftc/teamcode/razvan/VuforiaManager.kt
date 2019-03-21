package org.firstinspires.ftc.teamcode.razvan

import android.content.Context
import kotlinx.coroutines.*
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector

class VuforiaManager {

    @Volatile
    private var initializing = false
    private var job: Job? = null
    private var vuforia: VuforiaLocalizer? = null
    private var tfod: TFObjectDetector? = null

    fun startDetectorAsync(context: Context) {
        if (initializing) return

        job = GlobalScope.launch(Dispatchers.Default) {
            initializing = true
            initVuforia()

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod(context)
            } else {
                throw IllegalStateException("This device is not compatible with TFOD")
            }

            initializing = false
        }
    }

    fun stopCamera() {
        job?.cancel()
        tfod?.shutdown()
    }

    private fun initVuforia() {
        val parameters = VuforiaLocalizer.Parameters().apply {
            vuforiaLicenseKey = VUFORIA_KEY
            cameraDirection = VuforiaLocalizer.CameraDirection.BACK
            //cameraName = WebcamNameImpl.forSerialNumber(SerialNumber.fromString(""))
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
        tfod?.let { tfObjectDetector ->
            tfObjectDetector.activate()

            val updatedRecognitions = tfObjectDetector.updatedRecognitions

            if (updatedRecognitions != null)
                return updatedRecognitions.any { it.label == LABEL_GOLD_MINERAL }
        }

        return false
    }

    fun waitForDetector() = runBlocking {
        try {
            job?.join()
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
