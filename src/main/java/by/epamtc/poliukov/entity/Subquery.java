package by.epamtc.poliukov.entity;

import java.io.Serializable;

public class Subquery implements Serializable {
    private static final long serialVersionUID = 1L;

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
        Subquery other = (Subquery) o;
        if (subId != other.subId) {
            return false;
        }
        if (amountOfWorkInHours != other.amountOfWorkInHours) {
            return false;
        }
        if (information == null) {
            if (other.information != null) {
                return false;
            }
        } else if (!information.equals(other.information)) {
            return false;
        }
        if (mainRequestId != other.mainRequestId) {
            return false;
        }
        if (workType == null) {
            return other.workType == null;
        } else return workType.equals(other.workType);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + subId;
        result = prime * result + amountOfWorkInHours;
        result = prime * result + (information == null ? 0 : information.hashCode());
        result = prime * result + mainRequestId;
        result = prime * result + (workType == null ? 0 : workType.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName())
                .append("[subId = ")
                .append(subId)
                .append(", amountOfWorkInHours = ")
                .append(amountOfWorkInHours)
                .append(", information = ")
                .append(information)
                .append(", mainRequestId = ")
                .append(mainRequestId)
                .append(", workType = ")
                .append(workType)
                .append("]");
        return builder.toString();
    }
}
