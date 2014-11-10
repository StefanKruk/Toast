package org.equinoxosgi.toast.internal.dev.airbag.fake;

import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private static BundleContext context;
    public ServiceRegistration registration;
    private FakeAirbag airbag;

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
	airbag = new FakeAirbag();
	airbag.startup();
	registration = context.registerService(IAirbag.class.getName(), airbag,
		null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
	registration.unregister();
	airbag.shutdown();
	Activator.context = null;
    }
}
