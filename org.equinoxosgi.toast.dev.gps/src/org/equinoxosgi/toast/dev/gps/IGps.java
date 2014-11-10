package org.equinoxosgi.toast.dev.gps;

public interface IGps {

    public abstract int getHeading();

    public abstract int getLatitude();

    public abstract int getLongitude();

    public abstract int getSpeed();

}