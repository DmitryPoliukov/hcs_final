package by.epamtc.poliukov.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String login;
    private String password;
    private String name;
    private String secondName;
    private String surname;
    private String email;
    private String phone;
    private String role;



    private String city;
    private String address;

    private int valuePersonHour;
    private String information;
    private boolean isBlocked;
    private List<String> employeeWorkTypeName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getEmployeeWorkTypeName() {
        return employeeWorkTypeName;
    }

    public void setEmployeeWorkTypeName(List<String> employeeWorkTypeName) {
        this.employeeWorkTypeName = employeeWorkTypeName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                valuePersonHour == user.valuePersonHour &&
                isBlocked == user.isBlocked &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(secondName, user.secondName) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(role, user.role) &&
                Objects.equals(city, user.city) &&
                Objects.equals(address, user.address) &&
                Objects.equals(information, user.information) &&
                Objects.equals(employeeWorkTypeName, user.employeeWorkTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, name, secondName, surname, email, phone, role, city, address, valuePersonHour, information, isBlocked, employeeWorkTypeName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", valuePersonHour=" + valuePersonHour +
                ", information='" + information + '\'' +
                ", isBlocked=" + isBlocked +
                ", employeeWorkTypeName=" + employeeWorkTypeName +
                '}' + System.lineSeparator();
    }
}
