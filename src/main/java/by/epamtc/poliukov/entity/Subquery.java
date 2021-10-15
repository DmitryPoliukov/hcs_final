package by.epamtc.poliukov.entity;

import java.util.Objects;

public class Subquery {
    private int subId;
    private int amountOfWorkInHours;
    private String information;
    private int mainRequestId;
    private String workType;

    public int getMainRequestId() {
        return mainRequestId;
    }

    public void setMainRequestId(int mainRequestId) {
        this.mainRequestId = mainRequestId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getAmountOfWorkInHours() {
        return amountOfWorkInHours;
    }

    public void setAmountOfWorkInHours(int amountOfWorkInHours) {
        this.amountOfWorkInHours = amountOfWorkInHours;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subquery subquery = (Subquery) o;
        return subId == subquery.subId &&
                amountOfWorkInHours == subquery.amountOfWorkInHours &&
                mainRequestId == subquery.mainRequestId &&
                Objects.equals(information, subquery.information) &&
                Objects.equals(workType, subquery.workType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subId, amountOfWorkInHours, information, mainRequestId, workType);
    }

    @Override
    public String toString() {
        return "Subquery{" +
                "subId=" + subId +
                ", amountOfWorkInHours=" + amountOfWorkInHours +
                ", information='" + information + '\'' +
                ", mainRequestId=" + mainRequestId +
                ", workType='" + workType + '\'' +
                '}' + System.lineSeparator();
    }
}
