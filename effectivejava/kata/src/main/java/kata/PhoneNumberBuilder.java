package kata;

public class PhoneNumberBuilder {
    private int areaCode;
    private int number;

    public PhoneNumberBuilder areaCode(int areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public PhoneNumberBuilder number(int number) {
        this.number = number;
        return this;
    }

    public PhoneNumber build() {
        return PhoneNumber.of(areaCode, number);
    }
}