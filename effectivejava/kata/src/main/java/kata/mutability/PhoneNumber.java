package kata.mutability;

import com.google.common.base.MoreObjects;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class PhoneNumber implements Formattable, Comparable<PhoneNumber> {

    private final static PhoneNumber COMMON =
        new PhoneNumber(123, 1234);

    private static final Comparator<PhoneNumber> COMPARATOR =
        Comparator
            .comparingInt((PhoneNumber p) -> p.areaCode)
            .thenComparingInt(p -> p.number);

    private final int areaCode;

    private final int number;

    // Lazy initialization
    private int hashCode;

    // Eager initialization
    // private final int hashCode;

    private PhoneNumber(int areaCode, int number) {
        this.areaCode = areaCode;
        this.number = number;

        // Eager implementation
        // this.hashCode = Objects.hash(this.areaCode, this.number);
    }

    static PhoneNumber of(int areaCode, int number) {
        // Benefit of Factory Method
        //   Can have name
        //   Can cache instance in member field, if it is expensive
        //   Can return different instance(e.g. SubType) other than PhoneNumber
        checkArgument(areaCode > 100);
        checkArgument(number > 1000);
        if (areaCode == 123 && number == 1234) {
            return COMMON;
        }
        return new PhoneNumber(areaCode, number);
    }

    public static PhoneNumberBuilder builder() {
        return new PhoneNumberBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof PhoneNumber) {
            PhoneNumber that = (PhoneNumber) o;
            return Objects.equals(this.areaCode, that.areaCode)
                && Objects.equals(this.number, that.number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // Lazy initialization
        if (this.hashCode == 0) {
            this.hashCode = Objects.hash(this.areaCode, this.number);
        }

        // Eager initialization
        return this.hashCode;

        // Standard implementation
        // return Objects.hash(this.areaCode, this.number);
    }

    @Override
    public String toString() {
        // Using Guava
        // PhoneNumber{areaCode=123, number=1234}
        return MoreObjects.toStringHelper(this)
            .add("areaCode", this.areaCode)
            .add("number", this.number)
            .toString();
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        formatter.format("%d-%d", this.areaCode, this.number);
    }

    @Override
    public int compareTo(PhoneNumber o) {
        return COMPARATOR.compare(this, o);

        // Cache
        // return Comparator
        //     .comparingInt((PhoneNumber p) -> p.areaCode)
        //     .thenComparingInt(p -> p.number)
        //     .compare(this, o);
    }
}
