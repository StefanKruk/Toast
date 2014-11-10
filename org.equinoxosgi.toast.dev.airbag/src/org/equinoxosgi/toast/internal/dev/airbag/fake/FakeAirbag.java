package org.equinoxosgi.toast.internal.dev.airbag.fake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.airbag.IAirbagInterface;

public class FakeAirbag implements IAirbag {

    private List<IAirbagInterface> listeners;
    private Job job;
    private boolean isRunning;

    public FakeAirbag() {
	super();
	listeners = new ArrayList<IAirbagInterface>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.equinoxosgi.toast.dev.airbag.IAirbag#addListener(org.equinoxosgi.
     * toast.dev.airbag.IAirbagInterface)
     */
    @Override
    public synchronized void addListener(IAirbagInterface listener) {
	listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.equinoxosgi.toast.dev.airbag.IAirbag#removeListener(org.equinoxosgi
     * .toast.dev.airbag.IAirbagInterface)
     */
    @Override
    public synchronized void removeListener(IAirbagInterface listener) {
	listeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.equinoxosgi.toast.dev.airbag.IAirbag#deployed()
     */
    public void deployed() {
	for (Iterator<IAirbagInterface> i = listeners.iterator(); i.hasNext();) {
	    i.next().deployed();
	}
    }

    public synchronized void shutdown() {
	isRunning = false;
	job.cancel();
	try {
	    job.join();
	} catch (InterruptedException e) {
	    // shutting down, safe to ignore
	}
    }

    public synchronized void startup() {
	isRunning = true;
	job = new Job("FakeAirbag") {
	    @Override
	    protected IStatus run(IProgressMonitor monitor) {
		deployed();
		if (isRunning)
		    schedule(5000);
		return Status.OK_STATUS;
	    }
	};
	job.schedule(5000);
    }

}
