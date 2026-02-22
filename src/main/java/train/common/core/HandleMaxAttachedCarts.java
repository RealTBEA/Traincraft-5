package train.common.core;

import train.common.api.AbstractTrains;
import train.common.api.Locomotive;

public class HandleMaxAttachedCarts {

    public HandleMaxAttachedCarts() {}

    /**
     * Handles the debuffs from attached stocks to a Locomotive's speed, brake, accel and fuel rate
     */
    public static void PullPhysic(Locomotive Loco) {
        if (Loco.train == null) // Exit if Loco isn't fully init on level start
            return;

        // Guarantee non-zero values
        Loco.currentMassPulled = Math.max(Loco.weightKg(),1);
        double totalMhp = Loco.transportMetricHorsePower() > 0 ? Loco.transportMetricHorsePower() : 100;

        // Append attached stock mass and passive locos Mhp
        for (AbstractTrains stock : Loco.train.getTrains()) {
            if (stock.uniqueID != Loco.uniqueID) {
                Loco.currentMassPulled += stock.weightKg();
                if (stock instanceof Locomotive) {
                    totalMhp += stock.transportMetricHorsePower();
                }
            }
        }
        Loco.currentMassPulled *= 0.07457;

        // Debuffs
        Loco.currentSpeedSlowDown = Loco.currentMassPulled / totalMhp * 74.57;
        Loco.currentBrakeSlowDown = Math.pow(Loco.currentMassPulled,2) / totalMhp * 0.7457 * 0.8;
        Loco.currentAccelSlowDown = Loco.currentBrakeSlowDown * 1.13;
        Loco.currentFuelConsumptionChange = Loco.currentBrakeSlowDown * 100;

        // Get the defaults, then scale them
        Loco.setCustomSpeed(        Loco.getMaxSpeed()          - Loco.currentSpeedSlowDown);
        Loco.setBrake(Math.min(     Loco.setBrake(0)            + Loco.currentBrakeSlowDown,0.998));  // Avoid Brake > 1 (acceleration)
        Loco.setAccel(              Loco.setAccel(0)            - Loco.currentAccelSlowDown);
        Loco.setFuelConsumption(    Loco.setFuelConsumption(0)  - (int)Loco.currentFuelConsumptionChange);
    }
}