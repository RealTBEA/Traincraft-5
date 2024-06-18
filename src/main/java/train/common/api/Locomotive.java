package train.common.api;

import com.google.gson.JsonObject;
import com.jcirmodelsquad.tcjcir.extras.PeachyUtil;
import com.jcirmodelsquad.tcjcir.extras.packets.RemoteControlKeyPacket;
import com.jcirmodelsquad.tcjcir.features.autotrain.AutoTrain2;
import com.jcirmodelsquad.tcjcir.features.autotrain.AutoTrain2Handler;
import com.jcirmodelsquad.tcjcir.features.autotrain.IAT2Compatible;
import com.jcirmodelsquad.tcjcir.features.signal.dynamic.LocoTransceiver;
import com.jcirmodelsquad.tcjcir.features.signal.dynamic.Message;
import com.jcirmodelsquad.tcjcir.vehicles.locomotives.PCH100H;
import com.jcirmodelsquad.tcjcir.vehicles.locomotives.PCH120Commute;
import com.jcirmodelsquad.tcjcir.vehicles.locomotives.PCH130Commute2;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.input.Keyboard;
import train.client.MovingTrainSound;
import train.client.core.handlers.TCKeyHandler;
import train.common.Traincraft;
import train.common.adminbook.ServerLogger;
import train.common.core.HandleMaxAttachedCarts;
import train.common.core.handlers.ConfigHandler;
import train.common.core.network.PacketKeyPress;
import train.common.core.network.PacketParkingBrake;
import train.common.core.network.PacketSlotsFilled;
import train.common.entity.rollingStock.*;
import train.common.items.ItemATOCard;
import train.common.items.ItemRemoteController;
import train.common.items.ItemRemoteControllerModule;
import train.common.items.ItemWirelessTransmitter;
import train.common.library.BetterEnumSounds;
import train.common.library.EnumSounds;
import train.common.library.Info;
import train.common.mtc.network.*;

import java.util.*;


public abstract class Locomotive extends EntityRollingStock implements IInventory {
    public boolean lampOn;
    public boolean dothelightthing;
    public boolean bellPressed;
    public int inventorySize;
    public boolean parkingBrake = false;
    public int numCargoSlots;
    public int numCargoSlots1;
    public int numCargoSlots2;
    public boolean isLocoTurnedOn = false;
    public boolean forwardPressed = false;
    public boolean brakePressed = false;
    public TileEntity[] blocksToCheck;
    //Minecraft Train Control
    public int speedLimit, nextSpeedLimit, trainLevel, mtcStatus, mtcType, atoStatus = 0;
    public Vec3 stopPoint3 = Vec3.createVectorHelper(0, 0, 0);
    public double distanceFromStopPoint = 0.0;
    public Vec3 stationStop3 = Vec3.createVectorHelper(0, 0, 0);
    public double distanceFromStationStop = 0.0;
    public Vec3 speedChange3 = Vec3.createVectorHelper(0, 0, 0);
    public Double distanceFromSpeedChange = 0.0;
    public boolean isDriverOverspeed = false;
    public boolean overspeedBrakingInProgress = false;
    public Boolean mtcOverridePressed = false;
    public Boolean overspeedOveridePressed = false;
    public boolean enforceSpeedLimits = true;
    /*public String serverUUID = "";*/
    public LocoTransceiver ttTransceiver;
    public String trainID = "";
    public String currentSignalBlock = "";
    public boolean speedGoingDown = false;
    public boolean stationStop = false;
    public boolean isConnected = false;
    public boolean isConnecting = false;
    public int connectionAttempts = 0;
    public boolean atoAllowed = true;
    public int blinkMode = 0; // 0 = Off | 1 = Commander | 2 = Amazon Prime
    /**
     * These variables are used to display changes in the GUI
     */
    public int currentNumCartsPulled = 0;
    public double currentMassPulled = 0;
    public double currentSpeedSlowDown = 0;
    public double currentAccelSlowDown = 0;
    public double currentBrakeSlowDown = 0;
    public double currentFuelConsumptionChange = 0;
    /**
     * This is for the "can pull" feature It is used to avoid conflict with
     * isCartLockDown @see EntityRollingStock line 422 This is set in @see
     * TrainsOnClick
     */
    public boolean canBePulled = false;
    //ETI Type Beat.
    public String operatorID = ""; //Example: PR for PeachRail, or TXCN for Texas Central
    public String trainName = ""; //May not be used very often, but just in case, include it.
    public String trainNumber = "";
    public ArrayList<String> stations = new ArrayList<String>();
    protected ItemStack locoInvent[];
    protected boolean canCheckInvent = true;
    //public static int lightsOn = 0;
    /**
     * false if linked carts have no effect on the velocity of this cart. Use
     * carefully, if you link two carts that can't be adjusted, it will behave
     * as if they are not linked.
     */
    protected boolean canBeAdjusted = false;
    /**
     * used internally inside each loco to set the fuel consumption
     */
    protected int fuelRate;
    private int soundPosition = 0;
    private double soundPosition2 = 0;
    private int whistleDelay = 0;
    private int bellCount = 0;
    private int blowUpDelay = 0;
    private String lastRider = "";
    private Entity lastEntityRider;
    private boolean hasDrowned = false;
    private int slotsFilled = 0;
    private int fuelUpdateTicks = 0;
    private boolean backwardPressed = false;
    /**
     * state of the loco
     */
    private String locoState = "";


    public Locomotive(World world) {
        super(world);
        setFuelConsumption(0);
        inventorySize = numCargoSlots + numCargoSlots2 + numCargoSlots1;
        dataWatcher.addObject(2, 0);
        this.setDefaultMass(0);
        this.setCustomSpeed(getMaxSpeed());
        dataWatcher.addObject(3, destination);
        dataWatcher.addObject(5, trainID);
        dataWatcher.addObject(22, locoState);
        dataWatcher.addObject(24, fuelTrain);
        dataWatcher.addObject(25, (int) convertSpeed(Math.sqrt(Math.abs(motionX * motionX) + Math.abs(motionZ * motionZ))));//convertSpeed((Math.abs(this.motionX) + Math.abs(this.motionZ))
        dataWatcher.addObject(26, guiDetailsJSON());
        dataWatcher.addObject(27, renderRefs.toString());
        dataWatcher.addObject(15, (float) Math.round((getCustomSpeed() * 3.6f)));
        dataWatcher.addObject(28, String.valueOf(canBePulled));
        //dataWatcher.addObject(32, lineWaypoints);
        setAccel(0);
        setBrake(0);
        this.entityCollisionReduction = 0.99F;
        if (this instanceof SteamTrain) isLocoTurnedOn = true;
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        trainID = sb.toString();
        //TODO: Re-implement MTC-2
      /*  if (!serverUUID.equals("")) {
            attemptConnection(serverUUID);
        }*/
    }

    public static boolean isBetween(double x, double min, double max) {
        return x > min && x < max;
    }

    public String guiDetailsJSON() {
        JsonObject gui = new JsonObject();
        gui.addProperty("cartsPulled", currentNumCartsPulled);
        gui.addProperty("massPulled", currentMassPulled);
        gui.addProperty("slowDown", currentSpeedSlowDown);
        gui.addProperty("accelSlowDown", currentAccelSlowDown);
        gui.addProperty("brakeSlowDown", currentBrakeSlowDown);
        gui.addProperty("fuelUseChange", currentFuelConsumptionChange);
        return gui.toString();
    }

    public String guiDetailsDW() {
        return dataWatcher.getWatchableObjectString(26);
    }

    /**
     * this is basically NBT for entity spawn, to keep data between client and server in sync because some data is not automatically shared.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        super.readSpawnData(additionalData);
        isLocoTurnedOn = additionalData.readBoolean();
        parkingBrake = additionalData.readBoolean();
        if (additionalData.readBoolean()) {
            int selectedCargo = additionalData.readInt();
            if (selectedCargo < getCargoManager().getCargoSpecificationList().length + 1)
                getCargoManager().setSelectedCargo(selectedCargo);
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeBoolean(isLocoTurnedOn);
        buffer.writeBoolean(parkingBrake);
        buffer.writeBoolean(getCargoManager() != null);
        if (getCargoManager() != null) {
            buffer.writeInt(getCargoManager().getSelectedCargo());
        }
    }

    private String castToString(double str) {
        return "" + str;
    }

    @Override
    public boolean isPoweredCart() {
        return true;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    /**
     * To disable linking altogether, return false here.
     *
     * @return True if this cart is linkable.
     */
    @Override
    public boolean isLinkable() {
        return false;
    }

    /**
     * Returns true if this cart is a storage cart Some carts may have
     * inventories but not be storage carts and some carts without inventories
     * may be storage carts.
     *
     * @return True if this cart should be classified as a storage cart.
     */
    @Override
    public boolean isStorageCart() {
        return false;
    }

    protected int getCurrentMaxSpeed() {
        return (dataWatcher.getWatchableObjectInt(2));
    }

    protected void setCurrentMaxSpeed(int maxSpeed) {
        if (!worldObj.isRemote) {
            dataWatcher.updateObject(2, maxSpeed);
        }
    }



    /**
     * returns the absolute maximum speed of the given locomotive (speed in
     * km/h) divided by 3.6 to get ms
     *
     * @return double
     */
    public float getMaxSpeed() {
        if (trainSpec != null) {
            if (currentMassPulled > 1) {
                float power = (float) currentMassPulled / (((float) trainSpec.getMHP()) * 0.37f);
                if (power > 1) {
                    return trainSpec.getMaxSpeed() / (power);
                }
            }
            return trainSpec.getMaxSpeed();
        }
        return 50;
    }

    /**
     * returns the current maximum speed of the given locomotive (speed in km/h)
     * divided by 3.6 to get ms
     *
     * @return double
     */
    public float getCustomSpeed() {
        return getCurrentMaxSpeed() / 3.6f;
    }

    /**
     * set the max speed in km/h if the param is 0 then the default speed is
     * used
     * <p>
     * //@param speed //this is for making documentation of some sort via javadoc, shouldn't be relevant to the operation of the mod
     */
    public void setCustomSpeed(double m) {
        if (m != 0) {
            setCurrentMaxSpeed((int) m);
            return;
        }
        setCurrentMaxSpeed((int) this.getMaxSpeed());
    }

    @Override
    public boolean canOverheat() {
        return getOverheatTime() > 0;
    }

    @Override
    public int getOverheatTime() {
        if (trainSpec != null) {
            return trainSpec.getHeatingTime();
        }
        return 0;
    }

    @Override
    public void limitSpeedOnTCRail() {
        if (!canBePulled) {
            maxSpeed = SpeedHandler.handleSpeed(getMaxSpeed(), maxSpeed, this);
            //System.out.println(maxSpeed);
            if (this.speedLimiter != 0 && speedWasSet) {
                //maxSpeed *= this.speedLimiter;
                adjustSpeed(maxSpeed, speedLimiter);
            }
            if (motionX < -maxSpeed) {
                motionX = -maxSpeed;
            }
            if (motionX > maxSpeed) {
                motionX = maxSpeed;
            }
            if (motionZ < -maxSpeed) {
                motionZ = -maxSpeed;
            }
            if (motionZ > maxSpeed) {
                motionZ = maxSpeed;
            }
        }
    }

    /**
     * set the fuel consumption rate for each loco if i is 0 then default
     * consumption is used
     * <p>
     * //@param i //this is for making documentation of some sort via javadoc, shouldn't be relevant to the operation of the mod
     *
     * @return
     */
    public int setFuelConsumption(int c) {
        if (c != 0) {
            return fuelRate = c;
        }
        if (trainSpec != null) {
            return fuelRate = trainSpec.getFuelConsumption();
        }
        return 0;

    }

    /**
     * returns the fuel consumption rate for each loco
     *
     * @return int
     */
    public int getFuelConsumption() {
        return fuelRate == 0 ? trainSpec.getFuelConsumption() : fuelRate;
    }

    /**
     * Return the power of the loco, used for cart pulling
     *
     * @see HandleMaxAttachedCarts for calculations
     */
    public int getPower() {
        if (trainSpec != null) {
            return trainSpec.getMHP();
        }
        return 0;
    }

    /**
     * Set acceleration rate if rate = 0, default value is used
     *
     * @param rate
     */
    public double setAccel(double rate) {
        if (rate != 0) {
            return accelerate = rate;
        } else {
            if (trainSpec != null) {
                return accelerate = trainSpec.getAccelerationRate();
            }
            return 0.45;
        }
    }

    /**
     * Set brake rate if rate = 0, default value is used
     *
     * @param rate
     */
    public double setBrake(double rate) {
        if (rate != 0) {
            return brake = rate;
        } else {
            if (trainSpec != null) {
                return brake = trainSpec.getBrakeRate();
            }
            return 0.98;
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("canBeAdjusted", canBeAdjusted);
        nbttagcompound.setBoolean("canBePulled", canBePulled);
        nbttagcompound.setInteger("overheatLevel", getOverheatLevel());
        nbttagcompound.setString("lastRider", lastRider);
        nbttagcompound.setString("destination", destination);
        nbttagcompound.setBoolean("parkingBrake", parkingBrake);
        if (!(this instanceof SteamTrain)) {
            nbttagcompound.setBoolean("isLocoTurnedOn", isLocoTurnedOn);
        }
        nbttagcompound.setString("trainID", trainID);
        nbttagcompound.setInteger("speedLimit", speedLimit);
        nbttagcompound.setInteger("trainLevel", trainLevel);
        nbttagcompound.setInteger("mtcStatus", mtcStatus);
        nbttagcompound.setInteger("mtcType", mtcType);
        nbttagcompound.setInteger("atoStatus", atoStatus);
        nbttagcompound.setDouble("xFromStop", stopPoint3.xCoord);
        nbttagcompound.setDouble("yFromStop", stopPoint3.yCoord);
        nbttagcompound.setDouble("zFromStop", stopPoint3.zCoord);
        nbttagcompound.setDouble("xFromStationStop", stationStop3.xCoord);
        nbttagcompound.setDouble("yFromStationStop", stationStop3.yCoord);
        nbttagcompound.setDouble("zFromStationStop", stationStop3.zCoord);
        nbttagcompound.setInteger("nextSpeedLimit", nextSpeedLimit);
        nbttagcompound.setDouble("xSpeedChange", speedChange3.xCoord);
        nbttagcompound.setDouble("ySpeedChange", speedChange3.yCoord);
        nbttagcompound.setDouble("zSpeedChange", speedChange3.zCoord);
        nbttagcompound.setBoolean("mtcOverridePressed", mtcOverridePressed);
        nbttagcompound.setBoolean("overspeedOverridePressed", overspeedOveridePressed);
        //nbttagcompound.setString("serverUUID", serverUUID);
        nbttagcompound.setString("currentSignalBlock", currentSignalBlock);
        nbttagcompound.setBoolean("isConnected", isConnected);
        nbttagcompound.setBoolean("stationStop", stationStop);
        nbttagcompound.setBoolean("lampOn", lampOn);


    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound ntc) {
        super.readEntityFromNBT(ntc);
        canBeAdjusted = ntc.getBoolean("canBeAdjusted");
        canBePulled = ntc.getBoolean("canBePulled");
        setOverheatLevel(ntc.getInteger("overheatLevel"));
        lastRider = ntc.getString("lastRider");
        destination = ntc.getString("destination");
        this.parkingBrake = ntc.getBoolean("parkingBrake");
        if (!(this instanceof SteamTrain)) {
            isLocoTurnedOn = ntc.getBoolean("isLocoTurnedOn");
        }
        trainID = ntc.getString("trainID");
        speedLimit = ntc.getInteger("speedLimit");
        trainLevel = ntc.getInteger("trainLevel");
        mtcStatus = ntc.getInteger("mtcStatus");
        mtcType = ntc.getInteger("mtcType");
        atoStatus = ntc.getInteger("atoStatus");

        stopPoint3 = Vec3.createVectorHelper(ntc.getDouble("xFromStop"), ntc.getDouble("yFromStop"), ntc.getDouble("zFromStop"));
        stationStop3 = Vec3.createVectorHelper(ntc.getDouble("xFromStationStop"), ntc.getDouble("yFromStationStop"), ntc.getDouble("zFromStationStop"));
        speedChange3 = Vec3.createVectorHelper(ntc.getDouble("xSpeedChange"), ntc.getDouble("ySpeedChange"), ntc.getDouble("zSpeedChange"));


        mtcOverridePressed = ntc.getBoolean("mtcOverridePressed");
        overspeedOveridePressed = ntc.getBoolean("overspeedOverridePressed");
        //serverUUID = ntc.getString("serverUUID");
        currentSignalBlock = ntc.getString("currentSignalBlock");
        isConnected = ntc.getBoolean("isConnected");
        stationStop = ntc.getBoolean("stationStop");
        dataWatcher.updateObject(5, trainID);
        dataWatcher.updateObject(28, String.valueOf(canBePulled));
    }

    /**
     * Returns true if this entity should push and be pushed by other entities
     * when colliding.
     */
    @Override
    public boolean canBePushed() {
        return false;
    }

    public void setCanBeAdjusted(boolean canBeAdj) {
        this.canBeAdjusted = canBeAdj;
    }

    /**
     * gets packet from server and distribute for GUI handles motion
     *
     * @param i
     */
    @Override
    public void keyHandlerFromPacket(int i) {
        if (this.getTrainLockedFromPacket()) {
            // Allow a player to operate locomotive if they are the owner if they are trusted.
            if (this.riddenByEntity instanceof EntityPlayer
                    && !((EntityPlayer) this.riddenByEntity).getDisplayName()
                    .equalsIgnoreCase(this.getTrainOwner()) && !this.isPlayerTrusted(((EntityPlayer) riddenByEntity).getDisplayName())) {
                return;
            }
        }
        pressKey(i);
        if (i == 8 && ConfigHandler.SOUNDS) {
            soundHorn();
        }
        if (i == 4) {
            forwardPressed = true;
        }
        if (i == 5) {
            backwardPressed = true;
        }
        if (i == 12) {
            brakePressed = true;
        }
        if (i == 13) {
            forwardPressed = false;
        }
        if (i == 14) {
            backwardPressed = false;
        }
        if (i == 15) {
            brakePressed = false;
        }

        if (i == 16) {
            if (mtcStatus != 0 && this.mtcType != 1) {
                if (this instanceof IAT2Compatible) {
                    AutoTrain2 autoTrain2 = ((IAT2Compatible)this).getDriver();
                    autoTrain2.start();
                } else {
                    setMTCStatus(1, mtcType, atoStatus == 0 ? 1 : 0);
                }
            }
        }

        if (i == 17) {

            if (mtcOverridePressed) {
                mtcOverridePressed = false;
            } else {
                mtcOverridePressed = true;
                this.mtcStatus = 0;
                this.speedLimit = 0;
                this.nextSpeedLimit = 0;
                this.trainLevel = 0;
                disconnectFromServer();
            }


        }
        if (i == 18) {
            if (mtcStatus != 0) {
                if (overspeedOveridePressed) {
                    overspeedOveridePressed = false;
                } else {
                    overspeedOveridePressed = true;
                }
            }
        }
        if (i == 19) {
            /*if (lampOn == false) {//if lampon is EQUAL TO false
                lampOn = true;// make lampon EQUAL true
            } else if (lampOn == true) {//if lampon is EQUAL TO true
                lampOn = false; //make lampon EQUAL false
            }*/
            lampOn = !lampOn;
            if (lampOn) {
                this.dothelightthing = true;
            }
            if (!lampOn) {
                dothelightthing = false;
            }

            if (lampOn) {
                System.out.println(lampOn + " loco.java");
            }
            if (!lampOn) {
                System.out.println(lampOn + " loco.java");
            }
        }

        /*if (i == 48){
            soundBell();
        }*/

        if (i == 20) {
            cycleThroughBeacons();
        }

        if (i == 10) {//BELLPRESSED NEESD TO BE TRUEE
            bellPressed = !bellPressed;
            if (bellPressed) {
                soundBell3();
            }
        }
    }

    public Float getCustomSpeedGUI() {
        return (this.dataWatcher.getWatchableObjectFloat(15));
    }

    public String getDestinationGUI() {
        if (worldObj.isRemote) {
            return (this.dataWatcher.getWatchableObjectString(3));
        }
        return destination;
    }

    private double convertSpeed(double speed) {
        //System.out.println("X "+motionX +" Z "+motionZ);
        if (ConfigHandler.REAL_TRAIN_SPEED) {
            speed *= 2;// applying ratio
        } else {
            speed *= 6;
        }
        speed *= 36;
        //speed *= 10;// convert in ms
        //speed *= 6;// applying ratio
        //speed *= 3.6;// convert in km/h
        return speed;
    }

    /*public void soundBell() {
        worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + "sounds/bell/test.ogg", 1F, 1.0F);
    }*/
    public void soundBell2AndaHalf() {
        worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + "bell_test", 1.0F, 1.0F);

    }

    public void soundBell3() {
        for (EnumSounds sounds : EnumSounds.values()) {
            if (sounds.getEntityClass() != null && !sounds.getHornString().equals("") && sounds.getEntityClass().equals(this.getClass()) && !sounds.getBellString().equals("")) {

                worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getBellString(), 1f, 1F);
                bellCount = sounds.getBellLength();//default 15 for bronze bell
                //System.out.println(bellCount);

            }
        }
    }

    public void soundHorn() {
        for (EnumSounds sounds : EnumSounds.values()) {
            if (sounds.getEntityClass() != null && !sounds.getHornString().equals("") && sounds.getEntityClass().equals(this.getClass()) && whistleDelay == 0) {
                if (sounds.getHorns() != null) {
                    System.out.println(sounds.getHorns()[this.acceptedColors.indexOf(this.getColor())]);
                    worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getHorns()[this.acceptedColors.indexOf(this.getColor())], sounds.getHornVolume(), 1.0F);
                }
                worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getHornString(), sounds.getHornVolume(), 1.0F);
                whistleDelay = 65;
            }
        }
        List entities = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(
                this.posX - 20, this.posY - 5, this.posZ - 20,
                this.posX + 20, this.posY + 5, this.posZ + 20));

        for (Object e : entities) {
            if (e instanceof EntityAnimal) {
                ((EntityAnimal) e).setTarget(this);
                ((EntityAnimal) e).getNavigator().setPath(null, 0);
            }
        }
    }

    @Override
    public void onUpdate() {
        if (trainID.equals("") && !worldObj.isRemote && ticksExisted % 40 == 0) {
            trainID = RandomStringUtils.randomAlphanumeric(5);
            dataWatcher.updateObject(5, trainID);

        }

        if (worldObj.isRemote && ticksExisted % 2 == 0 && !Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
            if (Keyboard.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindForward.getKeyCode())
                    && !forwardPressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(4));
                forwardPressed = true;
            } else if (!Keyboard
                    .isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindForward.getKeyCode())
                    && forwardPressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(13));
                forwardPressed = false;
            }
            if (Keyboard.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindBack.getKeyCode())
                    && !backwardPressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(5));
                backwardPressed = true;
            } else if (!Keyboard
                    .isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindBack.getKeyCode())
                    && backwardPressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(14));
                backwardPressed = false;
            }
            if (Keyboard.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindJump.getKeyCode())
                    && !brakePressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(12));
                brakePressed = true;
            } else if (!Keyboard
                    .isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindJump.getKeyCode())
                    && brakePressed) {
                Traincraft.keyChannel.sendToServer(new PacketKeyPress(15));
                brakePressed = false;
            }

            Item currentItem = new Item();
            if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
                currentItem = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem();
            }
            boolean hasController = currentItem instanceof ItemRemoteController;
            boolean isConnected = false;

            if (currentItem != null && hasController) {
                isConnected = ((ItemRemoteController) currentItem).attachedLocomotive != null;
            }
            //1: Forward
            //2: Backwards
            //3: Toggle Brake
            //4: Horn

            if (Minecraft.getMinecraft().thePlayer != null && Vec3.createVectorHelper(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ).distanceTo(Vec3.createVectorHelper(this.posX, posY, posZ)) < 200) {
                if (TCKeyHandler.remoteControlForward.getIsKeyPressed() && hasController && isConnected && ((ItemRemoteController) currentItem).attachedLocomotive == this) {
                    Traincraft.remoteControlKey.sendToServer(new RemoteControlKeyPacket(this.getEntityId(), 1));
                }

                if (TCKeyHandler.remoteControlBackwards.getIsKeyPressed() && hasController && isConnected && ((ItemRemoteController) currentItem).attachedLocomotive == this) {
                    Traincraft.remoteControlKey.sendToServer(new RemoteControlKeyPacket(this.getEntityId(), 2));
                }

                if (TCKeyHandler.remoteControlBrake.getIsKeyPressed() && hasController && isConnected && ((ItemRemoteController) currentItem).attachedLocomotive == this) {
                    Traincraft.remoteControlKey.sendToServer(new RemoteControlKeyPacket(this.getEntityId(), 3));
                }

                if (TCKeyHandler.remoteControlHorn.getIsKeyPressed() && hasController && isConnected && ((ItemRemoteController) currentItem).attachedLocomotive == this) {
                    Traincraft.remoteControlKey.sendToServer(new RemoteControlKeyPacket(this.getEntityId(), 4));
                }
            }

        }

        // if (worldObj.isRemote) {
        // if (updateTicks % 50 == 0) {
        // Traincraft.brakeChannel
        // .sendToServer(new PacketParkingBrake(parkingBrake, this.getEntityId()));
        // Traincraft.ignitionChannel.sendToServer(new PacketSetLocoTurnedOn(isLocoTurnedOn));//
        // sending to client
        // updateTicks=0;
        // }
        // }
        if (!worldObj.isRemote) {
            if (parkingBrake) {
                motionX = 0.0;
                motionZ = 0.0;
            }
            if (this.riddenByEntity instanceof EntityLivingBase) {
                //EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;
                if (forwardPressed || backwardPressed) {
                    if (getFuel() > 0 && this.isLocoTurnedOn() && rand.nextInt(4) == 0 && !worldObj.isRemote) {
                        if (this.getTrainLockedFromPacket() && !((EntityPlayer) this.riddenByEntity).getDisplayName().equalsIgnoreCase(this.getTrainOwner())
                                && !isPlayerTrusted(((EntityPlayer) this.riddenByEntity).getDisplayName())) {
                            return;
                        }
                        if (riddenByEntity instanceof EntityPlayer) {
                            int dir = MathHelper
                                    .floor_double((((EntityPlayer) riddenByEntity).rotationYaw * 4F) / 360F + 0.5D) & 3;
                            if (dir == 2) {
                                if (forwardPressed) {
                                    motionZ -= 0.0075 * this.accelerate;
                                } else {
                                    motionZ += 0.0075 * this.accelerate;
                                }
                            } else if (dir == 0) {
                                if (forwardPressed) {
                                    motionZ += 0.0075 * this.accelerate;
                                } else {
                                    motionZ -= 0.0075 * this.accelerate;
                                }
                            } else if (dir == 1) {
                                if (forwardPressed) {
                                    motionX -= 0.0075 * this.accelerate;
                                } else {
                                    motionX += 0.0075 * this.accelerate;
                                }
                            } else {
                                if (forwardPressed) {
                                    motionX += 0.0075 * this.accelerate;
                                } else {
                                    motionX -= 0.0075 * this.accelerate;
                                }
                            }
                        }
                    }
                } else if (brakePressed) {
                    motionX *= brake;
                    motionZ *= brake;
                }
            }


            if (updateTicks % 20 == 0) HandleMaxAttachedCarts.PullPhysic(this);
            //if (updateTicks % 15 == 0) VBCTracking.getInstance().updateFromRS(Vec3.createVectorHelper(Math.floor(posX), Math.floor(posY), Math.floor(posZ)));

            /**
             * Can't use datawatcher here. Locomotives use them all already
             * Check inventory The packet never arrives if it is sent when the
             * entity reads its NBT (player hasn't been initialised probably)
             */
            if (updateTicks % 200 == 0) {
                this.slotsFilled = 0;
                for (int i = 0; i < getSizeInventory(); i++) {
                    ItemStack itemstack = getStackInSlot(i);
                    if (itemstack != null) {
                        slotsFilled++;
                    }
                }

                Traincraft.slotschannel.sendToAllAround(new PacketSlotsFilled(this, slotsFilled), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
            }
            /**
             * Fuel consumption
             */
            //if (this instanceof DieselTrain) consumption /= 5;
            if (fuelUpdateTicks >= 100) {
                fuelUpdateTicks = 0;
                updateFuelTrain(this.getFuelConsumption());
            }
            fuelUpdateTicks++;

        }
        if (whistleDelay > 0) {
            whistleDelay--;
        }
        if (this.riddenByEntity instanceof EntityPlayer) {
            this.lastRider = ((EntityPlayer) this.riddenByEntity).getDisplayName();
            this.lastEntityRider = (this.riddenByEntity);
        }

       /* if (!this.worldObj.isRemote && this.getParkingBrakeFromPacket() && !getState().equals("broken")) {
            motionX *= 0.0;
            motionZ *= 0.0;
        }*/

        //public void soundBell2() {
            /*for (EnumSounds sounds : EnumSounds.values()) {
                if (sounds.getEntityClass() != null && !sounds.getBellString().equals("")&& sounds.getEntityClass().equals(this.getClass()) && bellDelay == 0) {
                    if (bellPressed) {
                        bellCount++;
                        if (bellCount == 10) {
                            worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getBellString(), 1.0F, 1.0F);//first float is volume
                            //bellDelay = 1;
                            bellCount = 0;
                        }
                        //break;

                    }
                }//THIS SHOULD WORK BUT BELLPRESSED IS NEVER SET TO TRUE TO MAKE IT WORK IN THE FIRST PLACE
            }//FIND OUT HOW TO GET BELLPRESSED TO TRUE
        //}
*/
        if (ConfigHandler.SOUNDS) {

            double speed = this.getSpeed();

            if (BetterEnumSounds.trainSounds.containsKey(this.getClass().getName()) && worldObj.isRemote && getFuel() > 0 && this.isLocoTurnedOn()) {
                //Use BetterEnumSounds.
                BetterEnumSounds sounds = BetterEnumSounds.trainSounds.get(this.getClass().getName());
                String sound = "";

                ArrayList<Integer> scaledPercentages = new ArrayList<Integer>();

                for (Integer percent : sounds.getPercentages()) {
                    scaledPercentages.add((int) ((percent * this.getMaxSpeed() - 1) / 100));
                }

                if (soundPosition2 <= 0) {
                    for (int i = 0; i < scaledPercentages.size(); i++) {
                        if (i + 1 < scaledPercentages.size()) {
                            Integer first = scaledPercentages.get(i);
                            Integer second = scaledPercentages.get(i + 1);

                            if (isBetween(speed, first, second)) {
                                sound = sounds.getEngineSounds()[i];
                                soundPosition2 = sounds.getLengths()[i];
                                break;
                            } else if (speed > second) {
                                sound = sounds.getEngineSounds()[scaledPercentages.size() - 1];
                                soundPosition2 = sounds.getLengths()[scaledPercentages.size() - 1];
                            }
                        }
                    }



                    /*if (isBetween(speed, -1, 5)) {
                        //(speed > -0.001D && speed < 0.01D && soundPosition == 0 && speed < (int)(maxspeeed*(11f/100f))) {
                        sound = "tc:GE_D9_7FDL_16_IDLE";
                        soundPosition2 = 36.52;
                        //^^^^^ idle
                    } else if (isBetween(speed, 0.01D, notch1)) {//25f being the percentage of the speed
                        sound = "tc:GE_D9_7FDL_16_N1";
                        soundPosition2 = 19.56;
                        //^^^^^ notch 1
                    } else if (isBetween(speed, notch1, notch2)) {
                        sound = "tc:GE_D9_7FDL_16_N2";
                        soundPosition2 = 49.96;
                    } else if (isBetween(speed, notch2, notch3)) {
                        sound = "tc:GE_D9_7FDL_16_N3";
                        soundPosition2 = 61.1d;
                        //^^^^^ notch 3
                    } else if (isBetween(speed, notch3, notch4)) {
                        sound = "tc:GE_D9_7FDL_16_N4";
                        soundPosition2 = 49.32d;
                        //^^^^^ notch 4
                    } else if (isBetween(speed, notch4, notch5)) {
                        sound = "tc:GE_D9_7FDL_16_N5";
                        soundPosition2 = 49.96d;
                        //^^^^^ notch 5
                    } else if (isBetween(speed, notch5, notch6)) {
                        sound = "tc:GE_D9_7FDL_16_N6";
                        soundPosition2 = 47.48d;
                        //^^^^^ notch 6
                    } else if (isBetween(speed, notch6, notch7)) {
                        sound = "tc:GE_D9_7FDL_16_N7";
                        soundPosition2 = 49.96d;
                        //^^^^^ notch 7
                    } else if (isBetween(speed, notch7, notch8)) {
                        sound = "tc:GE_D9_7FDL_16_N8";
                        soundPosition2 = 48.4d;
                        //^^^^^ notch 8
                    }*/
                }


                if (!sound.isEmpty()) {
                    System.out.println(sound);
                    MovingTrainSound.playSound(new ResourceLocation("tc:" + sound));
                }

                if (soundPosition2 > 0) {
                    soundPosition2--;

                }

            } else {
                    //Use the old sound system.


                EnumSounds[] values = EnumSounds.values();
                for (EnumSounds sounds : values) {
                    if (sounds.getEntityClass() != null && !sounds.getHornString().isEmpty() && sounds.getEntityClass().equals(this.getClass()) && whistleDelay == 0) {
                        if (getFuel() > 0 && this.isLocoTurnedOn()) {
                            if (speed > -0.001D && speed < 0.01D && soundPosition == 0) {
                                worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getIdleString(), sounds.getIdleVolume(), 1F);
                                soundPosition = sounds.getIdleSoundLength();//soundPosition is probably where IN the sound it is currently playing, eg 1 sec int osoudn file
                            }
                            if (sounds.getSoundChangeWithSpeed() && !sounds.getHornString().isEmpty() && sounds.getEntityClass().equals(this.getClass()) && whistleDelay == 0 && !sounds.getBellString().isEmpty()) {
                                if (speed > 0.01D && speed < 0.06D && soundPosition == 0) {
                                    worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getRunString(), sounds.getRunVolume(), 0.1F);
                                    soundPosition = sounds.getRunSoundLength();
                                } else if (speed > 0.06D && speed < 0.2D && soundPosition == 0) {
                                    worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getRunString(), sounds.getRunVolume(), 0.4F);
                                    soundPosition = sounds.getRunSoundLength() / 2;
                                } else if (speed > 0.2D && soundPosition == 0) {
                                    worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getRunString(), sounds.getRunVolume(), 0.5F);
                                    soundPosition = sounds.getRunSoundLength() / 3;
                                }
                            } else {
                                if (speed > 0.01D && soundPosition == 0) {
                                    worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getRunString(), sounds.getRunVolume(), 1F);
                                    soundPosition = sounds.getRunSoundLength();
                                }
                            }
                            if (soundPosition > 0) {
                                soundPosition--;
                            }
                        }

                        break;
                    }

                }
                }
            }


        for (EnumSounds sounds : EnumSounds.values()) {
            if (sounds.getEntityClass() != null && !sounds.getHornString().isEmpty() && sounds.getEntityClass().equals(this.getClass()) && !sounds.getBellString().equals("")) {
                if (bellPressed) {

                    if (bellCount == 0) {
                        soundBell3();
                    }

                    if (bellCount > 0) {
                        bellCount--;
                    }
                } else {
                    bellCount = 0;
                }
                break;
            }
        }

        /*
        * for (EnumSounds sounds : EnumSounds.values()) {
            if (sounds.getEntityClass() != null && !sounds.getHornString().equals("")&& sounds.getEntityClass().equals(this.getClass()) && !sounds.getBellString().equals("")) {
                if (bellPressed) {

                    if (bellCount == 0) {
                        worldObj.playSoundAtEntity(this, Info.resourceLocation + ":" + sounds.getBellString(), 1f, 1F);// 2nd float is pitch, first float is volue
                        bellCount = 15;//default 15 for bronze bell
                        System.out.println(bellCount);
                    }
                    if (bellCount > 0) {
                        bellCount--;
                    }
                }
                else{
                    bellCount = 0;
                }
                break;
            }
        }

        * */

        if (getState().equals("cold") && !canBePulled) {
            this.extinguish();
            if (getCurrentMaxSpeed() >= (getMaxSpeed() * 0.6)) {
                motionX *= 0.0;
                motionZ *= 0.0;
            }
        }
        if (getState().equals("warm")) {
            this.extinguish();
            if (getCurrentMaxSpeed() >= (getMaxSpeed() * 0.7)) {
                motionX *= 0.94;
                motionZ *= 0.94;
            }
        }
        if (getState().equals("hot")) {
            this.extinguish();
        }
        //if (getState().equals("very hot")) {}
        if (getState().equals("too hot")) {
            motionX *= 0.95;
            motionZ *= 0.95;
            worldObj.spawnParticle("largesmoke", posX, posY + 0.3, posZ, 0.0D, 0.0D, 0.0D);
        }
        if (getState().equals("broken")) {
            setFire(8);
            this.setCustomSpeed(0);// set speed to normal
            this.setAccel(0.000001);// simulate a break down
            this.setBrake(1);
            this.motionX *= 0.97;// slowly slows down
            this.motionZ *= 0.97;
            worldObj.spawnParticle("largesmoke", posX, posY + 0.3, posZ, 0.0D, 0.0D, 0.0D);
            worldObj.spawnParticle("largesmoke", posX, posY + 0.3, posZ, 0.0D, 0.0D, 0.0D);
            blowUpDelay++;
            if (blowUpDelay > 80) {
                if (!worldObj.isRemote) {
                    //worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.5F, true);
                    worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.5F, false);
                    this.setDead();
                }
                if (!worldObj.isRemote && FMLCommonHandler.instance().getMinecraftServerInstance() != null && this.lastEntityRider instanceof EntityPlayer) {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(((EntityPlayer) this.lastEntityRider).getDisplayName() + " blew " + this.getTrainOwner() + "'s locomotive"));
                    FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(((EntityPlayer) this.lastEntityRider).getDisplayName() + " blew " + this.getTrainOwner() + "'s locomotive"));
                }
            }
        }
        //Todo: Better packets
        //Minecraft Train Control things.
        if (!worldObj.isRemote) {
            boolean autoTrainOn = false;
            try {
                AutoTrain2Handler handlerField = (AutoTrain2Handler) getClass().getField("autoTrainHandler").get(this);
                autoTrainOn = handlerField.autoTrainActivated;

            } catch (IllegalAccessException | NoSuchFieldException e) {

            }
            if (mtcStatus == 1 | mtcStatus == 2) {
                if ((mtcType == 2 || mtcType == 3) && !isConnected) {
                    //Send updates every few seconds
                    if (this.ticksExisted % 20 == 0) {
                        sendMTCStatusUpdate();
                    }
                }
                if ((mtcType == 2 || mtcType == 3) && !trainIsWMTCSupported()) {
                    //Seems like the MTC card has been removed suddenly. Terminate connections.
                    disconnectFromServer();
                    //serverUUID = "";
                    mtcStatus = 0;
                    Traincraft.mtcChannel.sendToAllAround(new PacketMTC(getEntityId(), mtcStatus, 2), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
                }
                isDriverOverspeed = getSpeed() > speedLimit && speedLimit != 0 && enforceSpeedLimits;
                if (isDriverOverspeed && (ticksExisted % 40 == 0) && atoStatus != 1 && this.riddenByEntity != null) {
                    //Todo Play Sound on Client
                    //Traincraft.playSoundOnClientChannel.sendTo(new PacketPlaySoundOnClient(7, "tc:mtc_overspeed"), (EntityPlayerMP) this.riddenByEntity);
                }
                if (isDriverOverspeed && ticksExisted % 120 == 0 && !overspeedBrakingInProgress && !overspeedOveridePressed && atoStatus != 1) {
                    //Start braking.
                    overspeedBrakingInProgress = true;
                }

                if (isDriverOverspeed && ticksExisted % 120 == 0 && !overspeedBrakingInProgress && !overspeedOveridePressed && atoStatus != 1) {
                    overspeedBrakingInProgress = true;
                }

                if (overspeedBrakingInProgress && atoStatus != 1) {
                    if (getSpeed() < speedLimit) {
                        //Stop overspeed braking.
                        overspeedBrakingInProgress = false;
                        isDriverOverspeed = false;
                    } else {
                        slow(speedLimit);
                    }
                }

                distanceFromStopPoint = stopPoint3.distanceTo(Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
                distanceFromSpeedChange = speedChange3.distanceTo(Vec3.createVectorHelper(this.posX, this.posY, this.posZ));


                if (distanceFromSpeedChange < this.speedLimit && !(distanceFromSpeedChange < this.nextSpeedLimit)) {
                    speedLimit = (int) Math.round(distanceFromSpeedChange);
                    speedGoingDown = true;

                    if (distanceFromSpeedChange <= 5) {
                        speedChange3 = Vec3.createVectorHelper(0, 0, 0);
                        speedLimit = nextSpeedLimit;
                        nextSpeedLimit = 0;
                        speedGoingDown = false;

                        Traincraft.mtcChannel.sendToAllAround(new PacketSpeedLimit(getEntityId(), speedLimit, nextSpeedLimit, 0, 0, 0),
                                new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
                    }

                }

                if (distanceFromStopPoint >= 40 && distanceFromStopPoint < this.speedLimit && !(this.stopPoint3.xCoord == 0.0)) {
                    this.speedLimit = (int) Math.round(distanceFromStopPoint);
                    speedGoingDown = true;
                } else if (distanceFromStopPoint >= 10 && distanceFromStopPoint < this.speedLimit && !(this.stopPoint3.xCoord == 0.0) && (mtcType == 2 || mtcType == 3)) {
                    this.speedLimit = (int) Math.round(distanceFromStopPoint);
                    speedGoingDown = true;
                }
                Traincraft.mtcChannel.sendToAllAround(new PacketSpeedLimit(getEntityId(), speedLimit, nextSpeedLimit),
                        new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));

				/*if (distanceFromStopPoint < this.getSpeed() && !(distanceFromStopPoint < nextSpeedLimit)  && !(this instanceof EntityLocoElectricPeachDriverlessMetro)) {
					speedLimit = (int) Math.round(distanceFromStopPoint);
					Traincraft.itsChannel.sendToAllAround(new PacketSetSpeed(this.speedLimit, (int) this.posX, (int) this.posY, (int) this.posZ, getEntityId()), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D) );
				}*/


                //For Automatic Train Operation

                if (this.atoStatus == 1 && trainIsATOSupported()) {
                    distanceFromStationStop = stationStop3.distanceTo(Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
                    if (this.parkingBrake) {
                        this.parkingBrake = false;
                        //Accelerate to the speed limit
                    }
                    if (!(distanceFromStopPoint < this.getSpeed()) && (!(distanceFromSpeedChange < this.getSpeed())) && !(distanceFromStationStop < this.getSpeed() + 5)) {
                        accel(this.speedLimit);
                    }


                    if (distanceFromStationStop < this.getSpeed()) {
                        stop(stationStop3);
                    }

                    if (distanceFromSpeedChange < this.getSpeed() + 5 && !(this.getSpeed() == this.nextSpeedLimit)) {
                        //Slow it down to the next speed limit
                        slow(this.nextSpeedLimit);
                    }

                    if (isDriverOverspeed) {
                        //The ATO system is speeding somehow, slow it down
                        slow(this.speedLimit);
                    }
                    if (this.distanceFromStopPoint < 2 || this.distanceFromStationStop < 2 && !stationStop) {
                        this.parkingBrake = true;
                    }

                    if (this.distanceFromStationStop < 2 && !stationStop) stationStopComplete();


                }

            }
        }


        super.onUpdate();
        if (!worldObj.isRemote) {
            //System.out.println(motionX +" "+motionZ);
            dataWatcher.updateObject(3, destination);
            dataWatcher.updateObject(5, trainID);
            dataWatcher.updateObject(15, getMaxSpeed());
            dataWatcher.updateObject(20, overheatLevel);
            dataWatcher.updateObject(22, locoState);
            dataWatcher.updateObject(24, fuelTrain);
            dataWatcher.updateObject(25, (int) convertSpeed(Math.sqrt(motionX * motionX + motionZ * motionZ)));
            dataWatcher.updateObject(26, guiDetailsJSON());
            dataWatcher.updateObject(27, renderRefs.toString());
            dataWatcher.updateObject(31, ("1c/" + castToString((int) (currentFuelConsumptionChange)) + " per tick"));
            dataWatcher.updateObject(28, String.valueOf(canBePulled));

            if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.2000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this) && this.updateTicks % 4 == 0) {
                if (!hasDrowned && !worldObj.isRemote && FMLCommonHandler.instance().getMinecraftServerInstance() != null && this.lastEntityRider instanceof EntityPlayer) {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(((EntityPlayer) this.lastEntityRider).getDisplayName() + " drowned " + this.getTrainOwner() + "'s locomotive"));
                    FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(((EntityPlayer) this.lastEntityRider).getDisplayName() + " drowned " + this.getTrainOwner() + "'s locomotive"));
                }
                //this.attackEntityFrom(DamageSource.generic, 100);
                this.setCustomSpeed(0);// set speed to normal
                this.setAccel(0.000001);// simulate a break down
                this.setBrake(1);
                this.motionX *= 0.97;// slowly slows down
                this.motionZ *= 0.97;
                this.fuelTrain = 0;
                this.hasDrowned = true;
                this.canCheckInvent = false;
                blowUpDelay++;
                if (blowUpDelay > 20) {
                    this.attackEntityFrom(DamageSource.drown, 100);
                }
            }/*
             * else{ this.canCheckInvent=true; this.hasDrowned=false; }
             */
        }
    }


    @Override
    protected void applyDragAndPushForces() {
        motionX *= getDragAir();
        motionY *= 0.0D;
        motionZ *= getDragAir();
    }

    /**
     * Carts should return their drag factor here
     *
     * @return The drag rate.
     */
    @Override
    public double getDragAir() {
        return 1D;
    }

    /**
     * Added for SMP
     *
     * @return true if on, false if off
     */
    public boolean getParkingBrakeFromPacket() {
        return parkingBrake;
    }

    /**
     * Added for SMP
     *
     * @param set set 0 if parking break is false, 1 if true
     */
    public void setParkingBrakeFromPacket(boolean set) {
        parkingBrake = set;
    }

    /**
     * added for SMP, used by the HUD
     *
     * @return
     */
    public double getSpeed() {
        return dataWatcher.getWatchableObjectInt(25);
    }

    /**
     * added for SMP, used by the HUD
     *
     * @return
     */
    @Override
    public int getOverheatLevel() {
        return (this.dataWatcher.getWatchableObjectInt(20));
    }

    /**
     * returns the state of the loco state is the consequence of overheating
     *
     * @return cold warm hot very hot too hot broken
     */
    public String getState() {
        return (this.dataWatcher.getWatchableObjectString(22));
    }

    /**
     * set the state of the loco
     *
     * @param state cold warm hot very hot too hot broken
     */
    public void setState(String state) {
        locoState = state;
        this.dataWatcher.updateObject(22, state);
    }

    /**
     * added for SMP, used by the HUD
     *
     * @return
     */
    public int getFuel() {
        if (worldObj.isRemote) {
            return (this.dataWatcher.getWatchableObjectInt(24));
        }
        return fuelTrain;
    }

    /**
     * Is it fuelled? used in GUI
     */
    public boolean getIsFuelled() {
        if (worldObj.isRemote) {
            return (this.dataWatcher.getWatchableObjectInt(24)) > 0;
        }
        return (this.fuelTrain > 0);
    }

    /**
     * Used for the gui
     */
    public int getFuelDiv(int i) {
        if (worldObj.isRemote) {
            return ((this.dataWatcher.getWatchableObjectInt(24) * i) / 1200);
        }
        return (this.fuelTrain * i) / 1200;
    }

    /**
     * This code applies fuel consumption.
     *
     * @param consumption
     */
    protected void updateFuelTrain(int consumption) {
        if (fuelTrain < 0 && !canBePulled) {
            motionX *= 0.8;
            motionZ *= 0.8;
        } else {
            if (this.isLocoTurnedOn()) {
                fuelTrain -= consumption;
                if (fuelTrain < 0) fuelTrain = 0;
            }
        }
    }

    public void setLocoTurnedOnFromPacket(boolean set) {
        isLocoTurnedOn = set;
    }

    public boolean isLocoTurnedOn() {
        return isLocoTurnedOn;
    }

    public boolean CanBePulled()
    {
        return Boolean.valueOf(dataWatcher.getWatchableObjectString(28));
    }

    // private int placeInSpecialInvent(ItemStack itemstack1, int i, boolean doAdd) {
    // if (locoInvent[i] == null) {
    // if (doAdd) locoInvent[i] = itemstack1;
    // return itemstack1.stackSize;
    // }
    // else if (locoInvent[i] != null && locoInvent[i] == itemstack1 && itemstack1.isStackable() &&
    // (!itemstack1.getHasSubtypes() || locoInvent[i].getItemDamage() == itemstack1.getItemDamage())
    // && ItemStack.areItemStackTagsEqual(locoInvent[i], itemstack1)) {
    //
    // int var9 = locoInvent[i].stackSize + itemstack1.stackSize;
    // if (var9 <= itemstack1.getMaxStackSize()) {
    // if (doAdd) locoInvent[i].stackSize = var9;
    // return var9;
    // }
    // else if (locoInvent[i].stackSize < itemstack1.getMaxStackSize()) {
    // if (doAdd) locoInvent[i].stackSize = locoInvent[i].getMaxStackSize();
    // return Math.abs(locoInvent[i].getMaxStackSize() - locoInvent[i].stackSize -
    // itemstack1.stackSize);
    //
    // }
    // }
    // return itemstack1.stackSize;
    //
    // }


    //TODO Fix ISided Inventory buildcraft support
	/*
	/**
	 * Offers an ItemStack for addition to the inventory.
	 *
	 * @param stack
	 *            ItemStack offered for addition. Do not manipulate this!
	 * @param doAdd
	 *            If false no actual addition should take place.
	 * @param from
	 *            Orientation the ItemStack is offered from.
	 * @return Amount of items used from the passed stack.
	 */
	/*
	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
		if (stack == null) { return 0; }
		//FuelHandler.steamFuelLast(itemstack) > 0 || LiquidManager.getInstance().isDieselLocoFuel(itemstack)||(itemstack.getItem().shiftedIndex==Item.redstone.shiftedIndex) || (itemstack.getItem() instanceof IElectricItem)
		//LiquidManager.getInstance().isContainer(itemstack1)&&loco instanceof SteamTrain
		if (this instanceof SteamTrain) {
			//System.out.println("is fuel? "+(FuelHandler.steamFuelLast(stack) > 0) + "return "+placeInSpecialInvent(stack,0,false));
			if (FuelHandler.steamFuelLast(stack) > 0) return placeInSpecialInvent(stack, 0, doAdd);
			if (LiquidManager.getInstance().isContainer(stack)) return placeInSpecialInvent(stack, 1, doAdd);
		}
		if (this instanceof DieselTrain) {
			//System.out.println("is diesel? "+(LiquidManager.getInstance().isDieselLocoFuel(stack)) + "return "+placeInSpecialInvent(stack,0,false));
			if (LiquidManager.getInstance().isDieselLocoFuel(stack)) return placeInSpecialInvent(stack, 0, doAdd);
		}
		if (this instanceof ElectricTrain) {
			if ((stack.getItem() == Item.itemRegistry.getObject("redstone")) || (stack.getItem() instanceof IElectricItem)) return placeInSpecialInvent(stack, 0, doAdd);
		}
		return 0;

	}
	*/

    //  Quoted out as it doesn't seem to have any use nor to be called at all.
    //	/**
    //	 * Requests items to be extracted from the inventory
    //	 *
    //	 * @param doRemove
    //	 *            If false no actual extraction may occur.
    //	 * @param from
    //	 *            Orientation the ItemStack is requested from.
    //	 * @param maxItemCount
    //	 *            Maximum amount of items to extract (spread over all returned
    //	 *            item stacks)
    //	 * @return Array of item stacks extracted from the inventory
    //	 */
    //	@Override
    //	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
    //		return null;
    //	}

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (worldObj.isRemote) {
            return true;
        }
        if (worldObj.isRemote) {

            if (Minecraft.getMinecraft().thePlayer != null) {
                for (int i2 = 0; i2 < Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory(); i2++) {
                    if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i2) != null && Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i2).getItem() instanceof ItemRemoteController && ((ItemRemoteController) Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i2).getItem()).attachedLocomotive == this) {
                        ((ItemRemoteController) Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i2).getItem()).attachedLocomotive = null;
                        break;
                    }
                }
            }
            return true;

        }
        if (canBeDestroyedByPlayer(damagesource)) return true;
        super.attackEntityFrom(damagesource, i);
        setRollingDirection(-getRollingDirection());
        setRollingAmplitude(10);
        setBeenAttacked();
        setDamage(getDamage() + i * 10);
        if (getDamage() > 40) {
            if (riddenByEntity != null) {
                riddenByEntity.mountEntity(this);
            }
            this.setDead();
            disconnectFromServer();

            if (ttTransceiver != null) ttTransceiver.disconnect();
            ServerLogger.deleteWagon(this);


            if (damagesource.getEntity() instanceof EntityPlayer) {
                dropCartAsItem(((EntityPlayer) damagesource.getEntity()).capabilities.isCreativeMode);
            } else {
                dropCartAsItem(false);
            }
        }
        return true;
    }

    @Override
    public void dropCartAsItem(boolean isCreative) {
        if (!itemdropped) {
            super.dropCartAsItem(isCreative);
            for (ItemStack stack : locoInvent) {
                if (stack != null) {
                    entityDropItem(stack, 0);
                }
            }
        }
    }

    /**
     * RC routing integration
     */
    @Override
    public boolean setDestination(ItemStack ticket) {
        if (ticket != null) {
            destination = getTicketDestination(ticket);
            return true;
        }
        return false;
    }

    /* IInventory implements */
    @Override
    public ItemStack getStackInSlot(int i) {
        return locoInvent[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.locoInvent[par1] != null) {
            ItemStack var2 = this.locoInvent[par1];
            this.locoInvent[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (locoInvent[i] != null) {
            if (locoInvent[i].stackSize <= j) {
                ItemStack itemstack = locoInvent[i];
                locoInvent[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = locoInvent[i].splitStack(j);
            if (locoInvent[i].stackSize == 0) {
                locoInvent[i] = null;
            }
            return itemstack1;

        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        locoInvent[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public void markDirty() {

        if (!worldObj.isRemote) {

            this.slotsFilled = 0;

            for (int i = 0; i < getSizeInventory(); i++) {

                ItemStack itemstack = getStackInSlot(i);

                if (itemstack != null) {

                    slotsFilled++;
                }
            }

            Traincraft.slotschannel.sendToAllAround(new PacketSlotsFilled(this, slotsFilled), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
        }
    }

    public int getAmmountOfCargo() {
        return slotsFilled;
    }

    public void recieveSlotsFilled(int amount) {
        this.slotsFilled = amount;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public ItemStack[] getInventory() {
        return locoInvent;
    }


    public void setSpeedLimit(int speedLimit, int nextSpeedLimit, Vec3 speedChange3) {
        this.speedLimit = speedLimit;
        this.nextSpeedLimit = nextSpeedLimit;
        this.speedChange3 = speedChange3;
        Traincraft.mtcChannel.sendToAllAround(new PacketSpeedLimit(getEntityId(), speedLimit, nextSpeedLimit, speedChange3.xCoord, speedChange3.yCoord, speedChange3.zCoord),
                new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 150.0D));
    }

    public void setMTCStatus(int status, int type, int atoStatus) {
        this.mtcStatus = status;
        this.mtcType = type;
        this.atoStatus = atoStatus;
        Traincraft.mtcChannel.sendToAllAround(new PacketMTCStatus(getEntityId(), status, type),
                new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,
                        posX, posY, posZ, 150.0D));
        if (atoStatus != 0) {
            Traincraft.mtcChannel.sendToAllAround(new PacketATO(getEntityId(), 1), new NetworkRegistry.TargetPoint(
                    worldObj.provider.dimensionId, posX, posY, posZ, 150.0D));
        }
    }

    public void setStationStop(Vec3 stationStop) {
        this.stationStop3 = stationStop;
        if (stationStop3.xCoord == 0 && stationStop3.yCoord == 0 && stationStop3.zCoord == 0) this.stationStop = false;
        Traincraft.mtcChannel.sendToAllAround(new PacketStopPoint(getEntityId(), stationStop.xCoord, stationStop.yCoord, stationStop.zCoord, 1),
                new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 150.0D));
    }

    public void setParkingBrake(boolean status) {
        this.parkingBrake = status;
        Traincraft.brakeChannel.sendToAllAround(new PacketParkingBrake(false, getEntityId()),
                new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 150.0D));
    }

    /**
     *
     * For MTC's Automatic Train Operation system
     */



    public void accel(Integer desiredSpeed) {
        if (this.worldObj != null) {

            if (this.getSpeed() != desiredSpeed) {
                if ((int) this.getSpeed() <= this.speedLimit) {
                    if (this.riddenByEntity == null || this.mtcType == 3) {

                        double rotation = this.serverRealRotation;
                        if (rotation == 90.0) {

                            this.motionX -= 0.0020 * this.accelerate;


                        } else if (rotation == -90.0) {

                            this.motionX += 0.0020 * this.accelerate;

                        } else if (rotation == 0.0) {

                            this.motionZ += 0.0020 * this.accelerate;

                        } else if (rotation == -180.0) {

                            this.motionZ -= 0.0020 * this.accelerate;
                        } else {

                        }

                    } else {
                        int dir = MathHelper
                                .floor_double((((EntityPlayer) riddenByEntity).rotationYaw * 4F) / 360F + 0.5D) & 3;
                        if (dir == 2) {

                            this.motionZ -= 0.0020 * this.accelerate;


                        } else if (dir == 0) {

                            this.motionZ += 0.0020 * this.accelerate;

                        } else if (dir == 1) {

                            this.motionX -= 0.0020 * this.accelerate;

                        } else if (dir == 3) {

                            this.motionX += 0.0020 * this.accelerate;

                        }

                    }
                }

            }
        }
    }

    public void slow(Integer desiredSpeed) {
        if (this.getSpeed() >= desiredSpeed) {
            motionX *= brake;
            motionZ *= brake;
        }
    }

    public void stop(Vec3 signalPosition) {
        Vec3 currentPosition = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        double currentDistance = currentPosition.distanceTo(Vec3.createVectorHelper(signalPosition.xCoord, signalPosition.yCoord, signalPosition.zCoord));
        double originalDistance = currentDistance;
        double brakingPercentage = 0.5D; // Adjust this value for stronger deceleration
        double brakingFactor = 0.9D; // Adjust this value for faster deceleration
        double smoothingFactor = 0.8D; // Adjust this value for smoother deceleration

        if (currentDistance != 0.0D && originalDistance != 0.0D) {
            brakingFactor = Math.pow((originalDistance - currentDistance) / originalDistance, 2); // Apply non-linear braking function
            double adjustedBrakingPercentage = 1.0D - (1.0D - brakingPercentage) * brakingFactor;
            brakingPercentage = brakingPercentage + (adjustedBrakingPercentage - brakingPercentage) * smoothingFactor;
        }

        this.motionX *= brakingPercentage;
        this.motionZ *= brakingPercentage;

    }


    public void attemptConnection(int ttId) {
        ttTransceiver = new LocoTransceiver(getEntityId(), this);
        ttTransceiver.connect(ttId);
    }

    public void disconnectFromServer() {
        this.isConnected = false;
        if (ttTransceiver != null) ttTransceiver.disconnect();
    }

    public void remoteControlFromPacket(int key) {

        switch (key) {
            case 1: {
                double rotation = this.serverRealRotation;
                //System.out.println(rotation);
                if (rotation < 90.0 && rotation > 0 || rotation == 90.0) {
                    //System.out.println("Forward 1");
                    this.motionX -= 0.0015 * this.accelerate;


                } else if (rotation == -90.0) {
                    //System.out.println("Forward 2");
                    this.motionX += 0.0015 * this.accelerate;

                } else if (rotation < -90.00 && rotation > -180) {
                    //System.out.println("Forward 3");
                    this.motionZ -= 0.0015 * this.accelerate;
                } else if (rotation == 0) {
                    this.motionZ += 0.0015 * this.accelerate;
                    //System.out.println("Forward 4");
                } else if (rotation < 180.0 && rotation > 90.0 || rotation == 180) {
                    this.motionZ -= 0.0015 * this.accelerate;
                    //System.out.println("Forward 5");
                } else if (rotation > -180 && rotation < -90 || rotation == -180) {
                    this.motionZ -= 0.0015 * this.accelerate;
                    //System.out.println("Forward 6");
                }


                break;
            }

            case 2: {
                double rotation = this.serverRealRotation;
                if (rotation < 90.0 && rotation > 0 || rotation == 90.0) {

                    this.motionX += 0.0015 * this.accelerate;


                } else if (rotation == -90.0) {

                    this.motionX -= 0.0015 * this.accelerate;

                } else if (rotation < -90.00 && rotation > -180) {

                    this.motionZ += 0.0015 * this.accelerate;
                } else if (rotation == 0) {
                    this.motionZ -= 0.0015 * this.accelerate;
                } else if (rotation < 180.0 && rotation > 90.0 || rotation == 180) {
                    this.motionZ += 0.0015 * this.accelerate;
                } else if (rotation > -180 && rotation < -90 || rotation == -180) {
                    this.motionZ += 0.0015 * this.accelerate;
                }

                break;
            }

            case 3: {
                this.parkingBrake = !this.parkingBrake;
                break;
            }

            case 4: {
                soundHorn();
                break;
            }
        }
    }

    public void stationStopComplete() {
        this.isBraking = true;
        this.stationStop3 = Vec3.createVectorHelper(0, 0, 0);

        this.atoStatus = 0;
        this.stationStop = true;
        this.parkingBrake = true;

        Traincraft.mtcChannel.sendToAllAround(new PacketATO(this.getEntityId(), 0), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
        Traincraft.mtcChannel.sendToAllAround(new PacketStopPoint(this.getEntityId(), 0.0, 0.0, 0.0, 1), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
        Traincraft.brakeChannel.sendToAllAround(new PacketParkingBrake(true, this.getEntityId()),
                new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0D));
    }

    public void sendMTCStatusUpdate() {

        Vec3 end = PeachyUtil.xyz((int) posX, (int) posY, (int) posZ);

        if (cartLinked1 != null) {
            Map<Integer, Vec3> distances = new HashMap<>();
            if ((cartLinked1).train != null && (cartLinked1).train.getTrains().size() != 0 && (cartLinked1).train.getTrains().size() > 1) {

                for (int i = 0; i < (cartLinked1).train.getTrains().size(); i++) {
                    EntityRollingStock stock = (cartLinked1).train.getTrains().get(i);
                    distances.put((int) getDistance(stock.posX, stock.posY, stock.posZ), PeachyUtil.xyz((int) stock.posX, (int) stock.posY, (int) stock.posZ));
                }
            }

            List sortedKeys = new ArrayList(distances.keySet());
            Collections.sort(sortedKeys);


            if (!sortedKeys.isEmpty()) {
                end = distances.get(sortedKeys.get(sortedKeys.size() - 1));
            }
        }

        if (ttTransceiver != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("x1", (int) this.posX);
            data.put("y1", (int) this.posY);
            data.put("z1", (int) this.posZ);
            data.put("x2", (int) end.xCoord);
            data.put("y2", (int) end.yCoord);
            data.put("z2", (int) end.zCoord);

            data.put("rp", rotationPitch);
            data.put("ry", rotationYaw);

            ttTransceiver.sendMessage(new Message(-1, getEntityId(), "pos", data));
        }
        //sendMessage(new MTCMessage(this.trainID, this.serverUUID, sendingObj.toString(), 1));

    }

    public boolean trainIsWMTCSupported() {
        boolean support = false;
        int whichOneToCheck = 0;
        if (this instanceof SteamTrain) whichOneToCheck = 2;
        if (!(this instanceof SteamTrain)) whichOneToCheck = 1;
        if (this.getInventory()[whichOneToCheck] != null) {
            // System.out.println(this.getInventory()[whichOneToCheck].getItem().getClass().getName());
            if (this.getInventory()[whichOneToCheck].getItem() instanceof ItemWirelessTransmitter) {
                support = true;
            }
        }
        return this instanceof EntityLocoDieselSD40 || this instanceof EntityLocoElectricBP4 || this instanceof EntityLocoDieselClass66 || this instanceof EntityLocoElectricBR185 || this instanceof EntityLocoElectricCD151 || this instanceof EntityLocoDieselDD35A || this instanceof EntityLocoElectricICE1 || this instanceof EntityLocoElectricHighSpeedZeroED || this instanceof EntityLocoElectricE103 || this instanceof EntityLocoDieselV60_DB || this instanceof EntityLocoDieselCD742 || this instanceof EntityLocoElectricVL10 || this instanceof EntityLocoElectricTramNY || this instanceof EntityLocoDieselIC4_DSB_MG || this instanceof EntityLocoDieselSD70 || this instanceof PCH120Commute || this instanceof PCH130Commute2 || support;


    }

    public Boolean trainIsATOSupported() {
        boolean support = false;
        int whichOneToCheck = 0;
        if (this instanceof SteamTrain) whichOneToCheck = 3;
        if (!(this instanceof SteamTrain)) whichOneToCheck = 2;
        if (this.getInventory()[whichOneToCheck] != null) {
            // System.out.println(this.getInventory()[whichOneToCheck].getItem().getClass().getName());
            if (this.getInventory()[whichOneToCheck].getItem() instanceof ItemATOCard) {
                support = true;
            } else {
                support = false;
            }
        }
        return this instanceof EntityLocoElectricHighSpeedZeroED || this instanceof EntityLocoElectricTramNY || this instanceof EntityLocoElectricICE1 || this instanceof EntityLocoDieselIC4_DSB_MG || this instanceof PCH120Commute || this instanceof PCH100H || this instanceof PCH130Commute2 || support;

    }


    public boolean trainIsRemoteControlSupported() {
        for (ItemStack item : this.getInventory()) {
            if (item != null && item.getItem() != null && item.getItem() instanceof ItemRemoteControllerModule) {
                return true;
            }
        }
        return false;
    }

    public int supportedBlinkModes() {
        return 0;
        //0 = None at all | 1 = Commander | 2 = Prime | 3 = All
    }

    public void cycleThroughBeacons() {
        switch (blinkMode) {
            case 0: {
                if (supportedBlinkModes() == 0) {
                    blinkMode = 0;
                } else {
                    blinkMode = 1;
                }
                break;
            }
            case 1: {
                if (supportedBlinkModes() == 1) {
                    blinkMode = 0;
                } else {
                    blinkMode = 2;
                }
                break;
            }
            case 2: {
                blinkMode = 0;
            }
        }
    }

    // public int blinkMode = 0; // 0 = Off | 1 = Commander | 2 = Amazon Prime
}
