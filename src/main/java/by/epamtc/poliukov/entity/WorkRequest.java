package by.epamtc.poliukov.entity;

import java.io.Serializable;
import java.util.List;

public class WorkRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int requestID;
    private String fillingDate;
    private String plannedDate;
    private int tenantUserId;
    private String requestStatus;
    private List<Subquery> subqueryList;

    public WorkRequest() {
    }

    public List<Subquery> getSubqueryList() {
        return subqueryList;
    }

    public void setSubqueryList(List<Subquery> subqueryList) {
        this.subqueryList = subqueryList;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getFillingDate() {
        return fillingDate;
    }

    public void setFillingDate(String fillingDate) {
        this.fillingDate = fillingDate;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public int getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(int tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkRequest other = (WorkRequest) o;
        if (requestID != other.requestID) {
            return false;
        }
        if (tenantUserId != other.tenantUserId) {
            return false;
        }
        if (fillingDate == null) {
            if (other.fillingDate != null) {
                return false;
            }
        } else if (!fillingDate.equals(other.fillingDate)) {
            return false;
        }
        if (plannedDate == null) {
            if (other.plannedDate != null) {
                return false;
            }
        } else if (!plannedDate.equals(other.plannedDate)) {
            return false;
        }
        if (requestStatus == null) {
            if (other.requestStatus != null) {
                return false;
            }
        } else if (!requestStatus.equals(other.requestStatus)) {
            return false;
        }
        if (subqueryList == null) {
            return other.subqueryList == null;
        } else return subqueryList.equals(other.subqueryList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + requestID;
        result = prime * result + (fillingDate == null ? 0 : fillingDate.hashCode());
        result = prime * result + (plannedDate == null ? 0 : plannedDate.hashCode());
        result = prime * result + tenantUserId;
        result = prime * result + (requestStatus == null ? 0 : requestStatus.hashCode());
        result = prime * result + (subqueryList == null ? 0 : subqueryList.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName())
                .append("[requestID = ")
                .append(requestID)
                .append(", fillingDate = ")
                .append(fillingDate)
                .append(", plannedDate = ")
                .append(plannedDate)
                .append(", tenantUserId = ")
                .append(tenantUserId)
                .append(", requestStatus = ")
                .append(requestStatus)
                .append(", subqueryList = ")
                .append(subqueryList)
                .append("]");
        return builder.toString();
    }
}
