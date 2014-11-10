package org.equinoxosgi.toast.internal.client.emergency.bundle;

import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.gps.IGps;
import org.equinoxosgi.toast.internal.client.emergency.EmergencyMonitor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private IAirbag airbag;
    private ServiceReference airbagRef;
    private IGps gps;
    private ServiceReference gpsRef;
    private EmergencyMonitor monitor;

    static BundleContext getContext() {
	return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext bundleContext) throws Exception {
	Activator.context = bundleContext;
	System.out.println("Launching");
	monitor = new EmergencyMonitor();
	gpsRef = context.getServiceReference(IGps.class.getName());
	airbagRef = context.getServiceReference(IAirbag.class.getName());
	if (gpsRef == null || airbagRef == null) {
	    System.err.println("Unable to acquire GPS or airbag!");
	    return;
	}
	gps = (IGps) context.getService(gpsRef);
	airbag = (IAirbag) context.getService(airbagRef);
	if (gps == null || airbag == null) {
	    System.err.println("Unable to acquire GPS or airbag!");
	    return;
	}
	monitor.setGps(gps);
	monitor.setAirbag(airbag);
	monitor.startup();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
	monitor.shutdown();
	if (gpsRef != null)
	    context.ungetService(gpsRef);
	if (airbagRef != null)
	    context.ungetService(airbagRef);
	System.out.println("Terminating");
	Activator.context = null;
    }

}
