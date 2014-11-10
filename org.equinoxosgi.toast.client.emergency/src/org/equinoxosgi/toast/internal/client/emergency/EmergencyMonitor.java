package org.equinoxosgi.toast.internal.client.emergency;

import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.airbag.IAirbagInterface;
import org.equinoxosgi.toast.dev.gps.IGps;

public class EmergencyMonitor implements IAirbagInterface {
    private IAirbag airbag;
    private IGps gps;

    @Override
    public void deployed() {
	System.out.println("Emergency occurred at lat=" + gps.getLatitude()
		+ " lon=" + gps.getLongitude() + " heading=" + gps.getHeading()
		+ " speed=" + gps.getSpeed());
    }

    public void setAirbag(IAirbag value) {
	airbag = value;
    }

    public void setGps(IGps value) {
	gps = value;
    }

    public void shutdown() {
	airbag.removeListener(this);
    }

    public void startup() {
	airbag.addListener(this);
    }
}
