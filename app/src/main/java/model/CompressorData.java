package model;

/**
 * Created by haopei on 2016/4/1.
 */
public class CompressorData {

    private String compressorName;
    private String createTime;
    private String dianliu;
    private String dianya;
    private String diya;
    private String gaoya;
    private String id;
    private String sequence;
    private String status;
    private String defrosterStatus;
    private String wendu;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDianliu() {
        return dianliu;
    }

    public void setDianliu(String dianliu) {
        this.dianliu = dianliu;
    }

    public String getDianya() {
        return dianya;
    }

    public void setDianya(String dianya) {
        this.dianya = dianya;
    }

    public String getDiya() {
        return diya;
    }

    public void setDiya(String diya) {
        this.diya = diya;
    }

    public String getGaoya() {
        return gaoya;
    }

    public void setGaoya(String gaoya) {
        this.gaoya = gaoya;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getCompressorName() {
        return compressorName;
    }

    public void setCompressorName(String compressorName) {
        this.compressorName = compressorName;
    }

    public String getDefrosterStatus() {
        return defrosterStatus;
    }

    public void setDefrosterStatus(String defrosterStatus) {
        this.defrosterStatus = defrosterStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompressorData{" +
                "compressorName='" + compressorName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", dianliu='" + dianliu + '\'' +
                ", dianya='" + dianya + '\'' +
                ", diya='" + diya + '\'' +
                ", gaoya='" + gaoya + '\'' +
                ", id='" + id + '\'' +
                ", sequence='" + sequence + '\'' +
                ", sequence='" + sequence + '\'' +
                ", status='" + status + '\'' +
                ", defrosterStatus='" + defrosterStatus + '\'' +
                ", wendu='" + wendu + '\'' +
                '}';
    }
}
