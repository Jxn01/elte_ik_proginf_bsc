package hu.elte.haladojava.regex;

import java.util.Objects;

public class Address {

    private final String city;
    private final String street;
    private final Integer streetNumber;

    public static Address parse(String address) {
        return null; // TODO
    }

    public static Address of(String city, String street, Integer streetNumber) {
        return new Address(city, street, streetNumber);
    }

    private Address(String city, String street, Integer streetNumber) {
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(streetNumber, address.streetNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, streetNumber);
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                '}';
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }
}
