package org.equinoxosgi.toast.dev.gps.fake;

import org.equinoxosgi.toast.dev.gps.IGps;

public class FakeGps implements IGps {

    /* (non-Javadoc)
     * @see org.equinoxosgi.toast.dev.gps.IGps#getHeading()
     */
    @Override
    public int getHeading() {
	return 90;
    }

    /* (non-Javadoc)
     * @see org.equinoxosgi.toast.dev.gps.IGps#getLatitude()
     */
    @Override
    public int getLatitude() {
	return 3776999;
    }

    /* (non-Javadoc)
     * @see org.equinoxosgi.toast.dev.gps.IGps#getLongitude()
     */
    @Override
    public int getLongitude() {
	return -12244694;
    }

    /* (non-Javadoc)
     * @see org.equinoxosgi.toast.dev.gps.IGps#getSpeed()
     */
    @Override
    public int getSpeed() {
	return 50;
    }
}
