package beans;

import java.io.Serializable;
import java.util.Objects;

public class Payee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String name;

    public Payee(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payee payee = (Payee) o;
        return Objects.equals(email, payee.email) &&
                Objects.equals(name, payee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }

    @Override
    public String toString() {
        return "Payee{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
