package me.spoony.autoggplus.tasks.data;

import me.spoony.autoggplus.detectors.IDetector;
import me.spoony.autoggplus.detectors.branding.ServerBrandingDetector;
import me.spoony.autoggplus.detectors.ip.ServerIPDetector;

public enum DetectorHandler {
    SERVER_BRANDING(new ServerBrandingDetector()),
    SERVER_IP(new ServerIPDetector());

    private final IDetector detector;

    DetectorHandler(IDetector detector) {
        this.detector = detector;
    }

    public IDetector getDetector() {
        return detector;
    }
}
