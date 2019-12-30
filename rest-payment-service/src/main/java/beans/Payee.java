package beans;

import java.io.Serializable;

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
}
