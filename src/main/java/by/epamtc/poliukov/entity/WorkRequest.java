package by.epamtc.poliukov.entity;

import java.util.List;
import java.util.Objects;

public class WorkRequest {
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
        WorkRequest that = (WorkRequest) o;
        return requestID == that.requestID &&
                tenantUserId == that.tenantUserId &&
                Objects.equals(fillingDate, that.fillingDate) &&
                Objects.equals(plannedDate, that.plannedDate) &&
                Objects.equals(requestStatus, that.requestStatus) &&
                Objects.equals(subqueryList, that.subqueryList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID, fillingDate, plannedDate, tenantUserId, requestStatus, subqueryList);
    }

    @Override
    public String toString() {
        return "WorkRequest{" +
                "requestID=" + requestID +
                ", fillingDate=" + fillingDate +
                ", plannedDate=" + plannedDate +
                ", tenantUserId=" + tenantUserId +
                ", requestStatus='" + requestStatus +
                ", subqueriesList=" + subqueryList +
                '}';
    }
}
