package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.ContactInfo;

import static org.junit.Assert.assertEquals;

public class ContactInfoTest {

    @Test
    public void emailTests(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getEmail(), "test@test.com");
        ct.setEmail("superTest@test.com");
        assertEquals(ct.getEmail(), "superTest@test.com");
    }

    @Test
    public void phoneTests(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");
        ct.setPhoneNumber("9876543210");
        assertEquals(ct.getPhoneNumber(), "9876543210");
    }

}
