import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerLoginTestTest_unittest {

    /**
     * Tests the CustomerLoginTest class by checking the successful scenario
     * where a customer logs in with valid credentials.
     */
    @Test
    public void testCustomerLoginSuccess() {
        // Valid test inputs
        String cardNumber = "1234567812345677";
        int pin = 2002;

        // Authenticate customer using the DAO
        Customer customer = CustomerDAO.authenticate(cardNumber, pin);

        // Assert that the customer object is not null, indicating successful login
        assertNotNull("Customer should not be null on successful login", customer);
    }

    /**
     * Tests the CustomerLoginTest class by ensuring login fails when invalid
     * credentials are supplied.
     */
    @Test
    public void testCustomerLoginFailure() {
        // Invalid test inputs
        String cardNumber = "incorrectCardNumber";
        int pin = 0;

        // Authenticate customer using the DAO
        Customer customer = CustomerDAO.authenticate(cardNumber, pin);

        // Assert that the customer object is null, indicating login failure
        assertNull("Customer should be null on login failure", customer);
    }
}


