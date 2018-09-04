package model;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gede on 10/04/18.
 */
public class LocationEstimate {

 private int typeOfshape;

 private int sign;

 private int latitudeTotalInt;

 private int longitudeTotalInt;

 private double latitudeTotalDegress;


 private double longitudeTotalDegress;

 private int uncertaintyCode;

 private double uncertaintyMeter;


 private int unCertaintyCodeSemiMajor;

 private double unCertaintyCodeSemiMajorMeter;

 private int unCertaintyCodeSemiMinor;

 private double unCertaintyCodeSemiMinorMeter;

  private  int orientationMajorAxis;
  private int confidence;


    public List<Integer> signList = new ArrayList<Integer>();

    public List<Integer> latitudeTotalIntList = new ArrayList<Integer>();

    public List<Double> latitudeTotalDegressList = new ArrayList<Double>();

    public List<Integer> longitudeTotalIntList = new ArrayList<Integer>();

    public List<Double> longitudeTotalDegressList = new ArrayList<Double>();



    private int directionAltitude;

    private int altitude;


    private int unCertaintyAltitude;




    private int innerRadius;
    private int unCertaintyInnerRadius;
    private double unCertaintyInnerRadiusMeter;



    private int offsetAngle;

    private int includedAngle;

    public int getOffsetAngle() {
        return offsetAngle;
    }

    public void setOffsetAngle(int offsetAngle) {
        this.offsetAngle = offsetAngle;
    }

    public int getIncludedAngle() {
        return includedAngle;
    }

    public void setIncludedAngle(int includedAngle) {
        this.includedAngle = includedAngle;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
    }

    public int getUnCertaintyInnerRadius() {
        return unCertaintyInnerRadius;
    }

    public void setUnCertaintyInnerRadius(int unCertaintyInnerRadius) {
        this.unCertaintyInnerRadius = unCertaintyInnerRadius;
    }

    public double getUnCertaintyInnerRadiusMeter() {
        return unCertaintyInnerRadiusMeter;
    }

    public void setUnCertaintyInnerRadiusMeter(double unCertaintyInnerRadiusMeter) {
        this.unCertaintyInnerRadiusMeter = unCertaintyInnerRadiusMeter;
    }

    public int getUnCertaintyAltitude() {
        return unCertaintyAltitude;
    }

    public void setUnCertaintyAltitude(int unCertaintyAltitude) {
        this.unCertaintyAltitude = unCertaintyAltitude;
    }

    public double getUnCertaintyAltitudeMeter() {
        return unCertaintyAltitudeMeter;
    }

    public void setUnCertaintyAltitudeMeter(double unCertaintyAltitudeMeter) {
        this.unCertaintyAltitudeMeter = unCertaintyAltitudeMeter;
    }

    private double unCertaintyAltitudeMeter;




    public int getDirectionAltitude() {
        return directionAltitude;
    }

    public void setDirectionAltitude(int directionAltitude) {
        this.directionAltitude = directionAltitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getOrientationMajorAxis() {
        return orientationMajorAxis;
    }

    public void setOrientationMajorAxis(int orientationMajorAxis) {
        this.orientationMajorAxis = orientationMajorAxis;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getUnCertaintyCodeSemiMajor() {
        return unCertaintyCodeSemiMajor;
    }

    public void setUnCertaintyCodeSemiMajor(int unCertaintyCodeSemiMajor) {
        this.unCertaintyCodeSemiMajor = unCertaintyCodeSemiMajor;
    }

    public double getUnCertaintyCodeSemiMajorMeter() {
        return unCertaintyCodeSemiMajorMeter;
    }

    public void setUnCertaintyCodeSemiMajorMeter(double unCertaintyCodeSemiMajorMeter) {
        this.unCertaintyCodeSemiMajorMeter = unCertaintyCodeSemiMajorMeter;
    }

    public int getUnCertaintyCodeSemiMinor() {
        return unCertaintyCodeSemiMinor;
    }

    public void setUnCertaintyCodeSemiMinor(int unCertaintyCodeSemiMinor) {
        this.unCertaintyCodeSemiMinor = unCertaintyCodeSemiMinor;
    }

    public double getUnCertaintyCodeSemiMinorMeter() {
        return unCertaintyCodeSemiMinorMeter;
    }

    public void setUnCertaintyCodeSemiMinorMeter(double unCertaintyCodeSemiMinorMeter) {
        this.unCertaintyCodeSemiMinorMeter = unCertaintyCodeSemiMinorMeter;
    }

    public double getUncertaintyMeter() {
        return uncertaintyMeter;
    }

    public void setUncertaintyMeter(double uncertaintyMeter) {
        this.uncertaintyMeter = uncertaintyMeter;
    }

    public int getUncertaintyCode() {
        return uncertaintyCode;
    }

    public void setUncertaintyCode(int uncertaintyCode) {
        this.uncertaintyCode = uncertaintyCode;
    }

    public double getLongitudeTotalDegress() {
        return longitudeTotalDegress;
    }

    public void setLongitudeTotalDegress(double longitudeTotalDegress) {
        this.longitudeTotalDegress = longitudeTotalDegress;
    }

    public int getLongitudeTotalInt() {
        return longitudeTotalInt;
    }

    public void setLongitudeTotalInt(int longitudeTotalInt) {
        this.longitudeTotalInt = longitudeTotalInt;
    }

    public double getLatitudeTotalDegress() {
        return latitudeTotalDegress;
    }

    public void setLatitudeTotalDegress(double latitudeTotalDegress) {
        this.latitudeTotalDegress = latitudeTotalDegress;
    }

    public int getLatitudeTotalInt() {
        return latitudeTotalInt;
    }

    public void setLatitudeTotalInt(int latitudeTotalInt) {
        this.latitudeTotalInt = latitudeTotalInt;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getTypeOfshape() {
        return typeOfshape;
    }

    public void setTypeOfshape(int typeOfshape) {
        this.typeOfshape = typeOfshape;
    }
}
