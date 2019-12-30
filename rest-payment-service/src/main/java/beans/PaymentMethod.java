package beans;

import enums.PaymentMethodType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
public class PaymentMethod implements Serializable {
  
    private static final long serialVersionUID = 1L;

    private PaymentMethodType type;

    private String accountName;

    private String lastFourDigits;

    public PaymentMethod(PaymentMethodType type, String accountName, String lastFourDigits) {
        this.type = type;
        this.accountName = accountName;
        this.lastFourDigits = lastFourDigits;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }
}
