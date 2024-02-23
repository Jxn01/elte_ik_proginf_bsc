package hu.elte.haladojava.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SensitiveInfoUtilTest {

    @Test
    void removeSensitiveInfo_password() {
        Assertions.assertEquals("password: ***",
                SensitiveInfoUtil.hidePassword("password: gaEvJwpeC2a7SEzD"));
    }

    @Test
    void removeSensitiveInfo_email() {
        Assertions.assertEquals("n***@a*****.me; z***@l**************.info and r************@a***.icu",
                SensitiveInfoUtil.hideEmail("ndan@adrmwn.me; zsur@lifetimefriends.info and rbea.lima.na3@aweo.icu"));
    }
}
