package ru.makarov.springripper.profilirovanie;

/**
 * MBean sample for activ in visual JVM.
 */
public class ProfileController implements ProfileControllerMBean {
    private boolean eneabled;

    public boolean isEneabled() {
        return eneabled;
    }

    public void setEneabled(boolean eneabled) {
        this.eneabled = eneabled;
    }
}
