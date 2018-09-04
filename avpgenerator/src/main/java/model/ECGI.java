package model;

public class ECGI {

    private String ECGI_HEX;

    private String MCC;

    private String MNC;

    private int ECI;

    private String ECI_HEX;

    private int  ENB;

    private String ENB_HEX;

    private int CellId;

    private String CellId_HEX;

    public String getECGI_HEX() {
        return ECGI_HEX;
    }

    public void setECGI_HEX(String ECGI_HEX) {
        this.ECGI_HEX = ECGI_HEX;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String MNC) {
        this.MNC = MNC;
    }

    public int getECI() {
        return ECI;
    }

    public void setECI(int ECI) {
        this.ECI = ECI;
    }

    public String getECI_HEX() {
        return ECI_HEX;
    }

    public void setECI_HEX(String ECI_HEX) {
        this.ECI_HEX = ECI_HEX;
    }

    public int getENB() {
        return ENB;
    }

    public void setENB(int ENB) {
        this.ENB = ENB;
    }

    public String getENB_HEX() {
        return ENB_HEX;
    }

    public void setENB_HEX(String ENB_HEX) {
        this.ENB_HEX = ENB_HEX;
    }

    public int getCellId() {
        return CellId;
    }

    public void setCellId(int cellId) {
        CellId = cellId;
    }

    public String getCellId_HEX() {
        return CellId_HEX;
    }

    public void setCellId_HEX(String cellId_HEX) {
        CellId_HEX = cellId_HEX;
    }
}
