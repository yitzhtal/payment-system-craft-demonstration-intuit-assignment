package unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utils.CreditCardValidation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreditCardValidation.class)
public class CreditCardValidationTests{
    @Autowired
    CreditCardValidation creditCardValidation;

    @Test
    public void testCreditCardOnSuccess() throws Exception {
        Assert.assertTrue(creditCardValidation.isValid(Long.valueOf("5196081888500645")));
        Assert.assertTrue(creditCardValidation.isValid(Long.valueOf("371263696359848")));
        Assert.assertTrue(creditCardValidation.isValid(Long.valueOf("4716281326187468")));
    }

    @Test
    public void testCreditCardOnFailure() throws Exception {
        Assert.assertFalse(creditCardValidation.isValid(Long.valueOf("1234123412341234")));
        Assert.assertFalse(creditCardValidation.isValid(Long.valueOf("0000000000000000")));
        Assert.assertFalse(creditCardValidation.isValid(Long.valueOf("1212121212121212")));
    }
}
