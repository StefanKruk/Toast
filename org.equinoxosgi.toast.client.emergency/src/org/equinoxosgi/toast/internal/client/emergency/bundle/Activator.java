package org.equinoxosgi.toast.internal.client.emergency.bundle;

import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.gps.IGps;
import org.equinoxosgi.toast.internal.client.emergency.EmergencyMonitor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private IAirbag airbag;
    private ServiceTracker airbagTracker;
    private IGps gps;
    private ServiceTracker gpsTracker;
    private EmergencyMonitor monitor;

    static BundleContext getContext() {
	return context;
    }

    private void bind() {
	if (gps == null) {
	    gps = (IGps) gpsTracker.getService();
	    if (gps == null)
		return;
	}
	if (airbag == null) {
	    airbag = (IAirbag) airbagTracker.getService();
	    if (airbag == null)
		return;
	}
	monitor.setGps(gps);
	monitor.setAirbag(airbag);
	monitor.startup();
    }

    private void unbind() {
	if (gps == null || airbag == null) {
	    return;
	}
	monitor.shutdown();
	gps = null;
	airbag = null;
    }

    private ServiceTrackerCustomizer createAirbagCustomizer() {
	return new ServiceTrackerCustomizer() {
	    public Object addingService(ServiceReference reference) {
		Object service = context.getService(reference);
		synchronized (Activator.this) {
		    if (Activator.this.airbag == null) {
			Activator.this.airbag = (IAirbag) service;
			Activator.this.bind();
		    }
		}
		return service;
	    }

	    @Override
	    public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void removedService(ServiceReference reference, Object service) {
		synchronized (Activator.this) {
		    if (service != Activator.this.airbag)
			return;
		    Activator.this.unbind();
		    Activator.this.bind();
		}
	    }
	};
    }

    private ServiceTrackerCustomizer createGpsCustomizer() {
	return new ServiceTrackerCustomizer() {

	    @Override
	    public Object addingService(ServiceReference reference) {
		Object service = context.getService(reference);
		synchronized (Activator.this) {
		    if (Activator.this.gps == null) {
			Activator.this.gps = (IGps) service;
			Activator.this.bind();
		    }
		}
		return service;
	    }

	    @Override
	    public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void removedService(ServiceReference reference, Object service) {
		synchronized (Activator.this) {
		    if (service != Activator.this.gps)
			return;
		    Activator.this.unbind();
		    Activator.this.bind();
		}
	    }
	};
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
	monitor = new EmergencyMonitor();
	ServiceTrackerCustomizer gpsCustomizer = createGpsCustomizer();
	gpsTracker = new ServiceTracker(bundleContext, IGps.class.getName(), gpsCustomizer);
	ServiceTrackerCustomizer airbCustomizer = createAirbagCustomizer();
	airbagTracker = new ServiceTracker(bundleContext, IAirbag.class.getName(), airbCustomizer);
	gpsTracker.open();
	airbagTracker.open();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
	airbagTracker.close();
	gpsTracker.close();
    }

}
