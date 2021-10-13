package by.epamtc.poliukov.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Employee extends User {
    private int valuePersonHour;
    private String information;

    private boolean isBlocked;
    private List<String> employeeWorkTypeName;

    public List<String> getEmployeeWorkTypeName() {
        return employeeWorkTypeName;
    }

    public void setEmployeeWorkTypeName(List<String> employeeWorkTypeName) {
        this.employeeWorkTypeName = employeeWorkTypeName;
    }

    public int getValuePersonHour() {
        return valuePersonHour;
    }

    public void setValuePersonHour(int valuePersonHour) {
        this.valuePersonHour = valuePersonHour;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return valuePersonHour == employee.valuePersonHour &&
                isBlocked == employee.isBlocked &&
                Objects.equals(information, employee.information);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), valuePersonHour, information, isBlocked);
        return result;
    }
/*
    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                "valuePersonHour=" + valuePersonHour +
                ", information='" + information + '\'' +
                ", isBlocked=" + isBlocked +
                ", employeeWorkTypeName=" + employeeWorkTypeName.toString() +
                '}'+ System.lineSeparator();
    }

 */
}
