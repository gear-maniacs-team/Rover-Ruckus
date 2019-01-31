package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class GoldDetectorManager {

    private GoldAlignDetector detector;

    public void startDetector(HardwareMap hardwareMap) {
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
    }

    public void setListener(GoldAlignDetector.GoldAlignListener listener) {
        detector.setListener(listener);
    }

    public Pos getLastGoldPosition() {
        final double y = detector.getYPosition(); // from 0 to 480

        if (!detector.isFound() && y == 0)
            return Pos.NOT_FOUND;

        Pos goldPos = Pos.MIDDLE;

        if (y > 0 && y < 160)
            goldPos = Pos.LEFT;
        else if (y > 320)
            goldPos = Pos.RIGHT;

        return goldPos;
    }

    public void stopDetector() {
        detector.disable();
        detector = null;
    }

    public enum Pos {
        NOT_FOUND,
        LEFT,
        MIDDLE,
        RIGHT
    }
}
