package by.epamtc.poliukov.entity;

import java.io.Serializable;
import java.util.List;

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
        User other = (User) o;
        if (userId != other.userId) {
            return false;
        }
        if (valuePersonHour != other.valuePersonHour) {
            return false;
        }
        if (isBlocked != other.isBlocked) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (secondName == null) {
            if (other.secondName != null) {
                return false;
            }
        } else if (!secondName.equals(other.secondName)) {
            return false;
        }
        if (surname == null) {
            if (other.surname != null) {
                return false;
            }
        } else if (!surname.equals(other.surname)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (phone == null) {
            if (other.phone != null) {
                return false;
            }
        } else if (!phone.equals(other.phone)) {
            return false;
        }
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        if (information == null) {
            if (other.information != null) {
                return false;
            }
        } else if (!information.equals(other.information)) {
            return false;
        }
        if (employeeWorkTypeName == null) {
            return other.employeeWorkTypeName == null;
        } else return employeeWorkTypeName.equals(other.employeeWorkTypeName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userId;
        result = prime * result + (login == null ? 0 : login.hashCode());
        result = prime * result + (password == null ? 0 : password.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (secondName == null ? 0 : secondName.hashCode());
        result = prime * result + (surname == null ? 0 : surname.hashCode());
        result = prime * result + (email == null ? 0 : email.hashCode());
        result = prime * result + (phone == null ? 0 : phone.hashCode());
        result = prime * result + (role == null ? 0 : role.hashCode());
        result = prime * result + (city == null ? 0 : city.hashCode());
        result = prime * result + (address == null ? 0 : address.hashCode());
        result = prime * result + valuePersonHour;
        result = prime * result + (information == null ? 0 : information.hashCode());
        result = prime * result + (isBlocked  ? 1231 : 1237);
        result = prime * result + (employeeWorkTypeName == null ? 0 : employeeWorkTypeName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName())
                .append("[userId = ")
                .append(userId)
                .append(", login = ")
                .append(login)
                .append(", password = ")
                .append(password)
                .append(", secondName = ")
                .append(secondName)
                .append(", surname = ")
                .append(surname)
                .append(", email = ")
                .append(email)
                .append(", phone = ")
                .append(phone)
                .append(", role = ")
                .append(role)
                .append(", city = ")
                .append(city)
                .append(", address = ")
                .append(address)
                .append(", valuePersonHour = ")
                .append(valuePersonHour)
                .append(", information = ")
                .append(information)
                .append(", isBlocked = ")
                .append(isBlocked)
                .append(", employeeWorkTypeName = ")
                .append(employeeWorkTypeName)
                .append("]");
        return builder.toString();
    }
}
