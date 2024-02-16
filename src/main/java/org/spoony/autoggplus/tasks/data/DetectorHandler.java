package org.spoony.autoggplus.tasks.data;

import org.spoony.autoggplus.detectors.IDetector;
import org.spoony.autoggplus.detectors.branding.ServerBrandingDetector;
import org.spoony.autoggplus.detectors.ip.ServerIPDetector;

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
