package train.common.core;

import train.common.api.AbstractTrains;
import train.common.api.Locomotive;

public class HandleMaxAttachedCarts {

    public HandleMaxAttachedCarts() {}

    /**
     * Handles the debuffs from attached stocks to a Locomotive's speed, brake, accel and fuel rate
     */
    public static void PullPhysic(Locomotive Loco) {

        // Guarantee non-zero values
        Loco.currentMassPulled = (Loco.pullingWeight > 0 ? Loco.pullingWeight : 1) * 0.07457;
        double totalMhp = Loco.transportMetricHorsePower() > 0 ? Loco.transportMetricHorsePower() : 100;

        // Append passive locos Mhp
        for (AbstractTrains stock : Loco.consist) {
            if (stock instanceof Locomotive && stock.uniqueID != Loco.uniqueID) {
                totalMhp += stock.transportMetricHorsePower();
            }
        }

        // Debuffs
        Loco.currentSpeedSlowDown = Loco.currentMassPulled / totalMhp * 74.57;
        Loco.currentBrakeSlowDown = Math.pow(Loco.currentMassPulled,2) / totalMhp * 0.7457 * 0.8;
        Loco.currentAccelSlowDown = Loco.currentBrakeSlowDown * 1.13;
        Loco.currentFuelConsumptionChange = Loco.currentBrakeSlowDown * 100;

        // Get the defaults, then scale them
        Loco.setCustomSpeed(Math.max(   Loco.getMaxSpeed()              - Loco.currentSpeedSlowDown,0));        // Avoid Speed < 0
        Loco.setBrake(Math.min(         Loco.getSpecBrake()             + Loco.currentBrakeSlowDown,0.998));    // Avoid Brake > 1 (acceleration)
        Loco.setAccel(Math.max(         Loco.getSpecAccel()             - Loco.currentAccelSlowDown,0));        // Avoid Accel < 0 (braking)
        Loco.setFuelConsumption(        Loco.getSpecFuelConsumption()   - (int)Loco.currentFuelConsumptionChange);
    }
}