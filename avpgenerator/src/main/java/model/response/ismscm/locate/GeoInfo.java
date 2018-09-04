package model.response.ismscm.locate;

import java.io.Serializable;

public class GeoInfo implements Serializable {
    private int uncertaintyCode;

    private int typeOfShape;

    private double radius;

    private double degreesOfLongitude;

    private double degreesOfLatitude;

    public int getUncertaintyCode() {
        return uncertaintyCode;
    }

    public void setUncertaintyCode(int uncertaintyCode) {
        this.uncertaintyCode = uncertaintyCode;
    }

    public Integer getTypeOfShape() {
        return typeOfShape;
    }

    public void setTypeOfShape(int typeOfShape) {
        this.typeOfShape = typeOfShape;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDegreesOfLongitude() {
        return degreesOfLongitude;
    }

    public void setDegreesOfLongitude(double degreesOfLongitude) {
        this.degreesOfLongitude = degreesOfLongitude;
    }

    public double getDegreesOfLatitude() {
        return degreesOfLatitude;
    }

    public void setDegreesOfLatitude(double degreesOfLatitude) {
        this.degreesOfLatitude = degreesOfLatitude;
    }

    @Override
    public String toString() {
        return "ClassPojo [uncertaintyCode = " + uncertaintyCode + ", typeOfShape = " + typeOfShape + ", radius = " + radius + ", degreesOfLongitude = " + degreesOfLongitude + ", degreesOfLatitude = " + degreesOfLatitude + "]";
    }
}
