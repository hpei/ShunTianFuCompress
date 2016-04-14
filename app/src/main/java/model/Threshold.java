package model;

/**
 * Created by haopei on 2016/4/1.
 */
public class Threshold {
    //{"id":1,"dianyaThreshold":5.0,"dianliuThreshold":8.0,"wenduThreshold":1.0,"gaoyaThreshold":1.0,"diyaThreshold":5.0}}

    private String id;
    private String dianyaLower;
    private String dianyaUpper;
    private String dianliuLower;
    private String dianliuUpper;
    private String wenduUpper;
    private String wenduLower;
    private String gaoyaLower;
    private String gaoyaUpper;
    private String diyaLower;
    private String diyaUpper;

    public String getDianliuLower() {
        return dianliuLower;
    }

    public void setDianliuLower(String dianliuLower) {
        this.dianliuLower = dianliuLower;
    }

    public String getDianliuUpper() {
        return dianliuUpper;
    }

    public void setDianliuUpper(String dianliuUpper) {
        this.dianliuUpper = dianliuUpper;
    }

    public String getDianyaLower() {
        return dianyaLower;
    }

    public void setDianyaLower(String dianyaLower) {
        this.dianyaLower = dianyaLower;
    }

    public String getDianyaUpper() {
        return dianyaUpper;
    }

    public void setDianyaUpper(String dianyaUpper) {
        this.dianyaUpper = dianyaUpper;
    }

    public String getDiyaLower() {
        return diyaLower;
    }

    public void setDiyaLower(String diyaLower) {
        this.diyaLower = diyaLower;
    }

    public String getDiyaUpper() {
        return diyaUpper;
    }

    public void setDiyaUpper(String diyaUpper) {
        this.diyaUpper = diyaUpper;
    }

    public String getGaoyaLower() {
        return gaoyaLower;
    }

    public void setGaoyaLower(String gaoyaLower) {
        this.gaoyaLower = gaoyaLower;
    }

    public String getGaoyaUpper() {
        return gaoyaUpper;
    }

    public void setGaoyaUpper(String gaoyaUpper) {
        this.gaoyaUpper = gaoyaUpper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWenduLower() {
        return wenduLower;
    }

    public void setWenduLower(String wenduLower) {
        this.wenduLower = wenduLower;
    }

    public String getWenduUpper() {
        return wenduUpper;
    }

    public void setWenduUpper(String wenduUpper) {
        this.wenduUpper = wenduUpper;
    }

    @Override
    public String toString() {
        return "Threshold{" +
                "dianliuLower='" + dianliuLower + '\'' +
                ", id='" + id + '\'' +
                ", dianyaLower='" + dianyaLower + '\'' +
                ", dianyaUpper='" + dianyaUpper + '\'' +
                ", dianliuUpper='" + dianliuUpper + '\'' +
                ", wenduUpper='" + wenduUpper + '\'' +
                ", wenduLower='" + wenduLower + '\'' +
                ", gaoyaLower='" + gaoyaLower + '\'' +
                ", gaoyaUpper='" + gaoyaUpper + '\'' +
                ", diyaLower='" + diyaLower + '\'' +
                ", diyaUpper='" + diyaUpper + '\'' +
                '}';
    }
}
