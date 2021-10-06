package by.epamtc.poliukov.entity;

import java.util.Objects;

public class Tenant extends User {
    private String city;
    private String address;

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

    public Tenant() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(city, tenant.city) &&
                Objects.equals(address, tenant.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city, address);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                super.toString()+
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}'+ System.lineSeparator();
    }
}
