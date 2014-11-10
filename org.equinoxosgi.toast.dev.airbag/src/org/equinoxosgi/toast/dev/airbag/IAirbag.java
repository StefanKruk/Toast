package org.equinoxosgi.toast.dev.airbag;

public interface IAirbag {

    public abstract void addListener(IAirbagInterface listener);

    public abstract void removeListener(IAirbagInterface listener);

}