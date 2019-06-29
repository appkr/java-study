package kata.mutability;

import static com.google.common.base.Preconditions.checkState;

public class Person {

    private String title;
    private final String name;
    private final String surname;
    private String prefix;

    private Person(String title, String name, String surname, String prefix) {
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.prefix = prefix;
    }

    public static Builder builder(String name, String surname) {
        return new Builder(name, surname);
    }

    static class Builder {

        private String title;
        private final String name;
        private final String surname;
        private String prefix;

        public Builder(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public Person build() {
            checkState(this.name != null && this.surname != null);
            return new Person(this.title, this.name, this.surname, this.prefix);
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }
    }
}
