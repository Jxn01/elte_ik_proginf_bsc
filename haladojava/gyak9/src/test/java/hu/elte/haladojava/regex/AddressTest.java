package hu.elte.haladojava.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

    // ######################################
    // Easy
    // ######################################

    @Test
    void parse() {
        Assertions.assertEquals(
                Address.of("Budapest", "Vaci ut", 13),
                Address.parse("Budapest Vaci ut 13"));
        Assertions.assertEquals(
                Address.of("Budapest", "Kos Karoly setany", 1),
                Address.parse("Budapest Kos Karoly setany 1"));
    }

    @Test
    void parse_invalidAddress() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Address.parse(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Address.parse("13"));
    }

    // ######################################
    // Hard
    // ######################################

    @Test
    void parse_partialAddress() {
        Assertions.assertEquals(
                Address.of("Budapest", "Csontvary Kosztka Tivadar utca", null),
                Address.parse("Budapest Csontvary Kosztka Tivadar utca"));
        Assertions.assertEquals(
                Address.of("Budapest", null, null),
                Address.parse("Budapest"));
    }

    @Test
    void parse_punctationAndUnicodeChars() {
        Assertions.assertEquals(
                Address.of("Budapest", "Tersánszky Józsi Jenő sétány", 1),
                Address.parse("Budapest, Tersánszky Józsi Jenő sétány 1."));
    }

    @Test
    void parse_invalidStreetNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Address.parse("Budapest Vaci ut 0"));
    }
}