package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClassTest {

    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<Integer> integerClass = Integer.class;
        Class<List> listClass = List.class;
        Class<Gender> enumClass = Gender.class;

        p(integerClass); // class java.lang.Integer
        p(listClass); // interface java.util.List
        p(enumClass); // class springstudy.apachecommons.Gender

        p(integerClass.toGenericString()); // public final class java.lang.Integer
        p(listClass.toGenericString()); // public abstract interface java.util.List<E>
        p(enumClass.toGenericString()); // public final enum springstudy.apachecommons.Gender

        Class<?> aClass = Class.forName("java.lang.Integer");
        p(aClass); // class java.lang.Integer

        /**
         * NOTE. newInstance required Empty Constructor
         */
        Name name = Name.class.newInstance();
        p(name); // Name{firstName='null', lastName='null'}

        p(Object.class.isInstance(name)); // true
        p(Number.class.isInstance(new Integer(1))); // true
        p(String.class.isInstance(new Integer(1))); // false

        p(Number.class.isAssignableFrom(Integer.class)); // true
        p(Integer.class.isAssignableFrom(Number.class)); // false

        p(listClass.isInterface()); // true

        int[] intArray = {1, 2, 3};
        p(intArray.getClass().isArray()); // true
        p(intArray.getClass().isPrimitive()); // false

        Integer i = 1;
        p(i.getClass().isPrimitive()); // false

        Class<Required> requiredClass = Required.class;
        p(requiredClass.isAnnotation()); // true
        p(enumClass.isAnnotation()); // false

        p(integerClass.getName()); // java.lang.Integer
        p(listClass.getName()); // java.util.List
        p(enumClass.getName()); // springstudy.apachecommons.Gender
        p(requiredClass.getName()); // springstudy.apachecommons.Required

        Class<Formatter> formatterClass = Formatter.class;
        pa(formatterClass.getTypeParameters()); // {T}
        pa(enumClass.getTypeParameters()); // {}

        p(Integer.class.getSuperclass()); // class java.lang.Number
        p(Integer.class.getGenericSuperclass()); // class java.lang.Number

        p(Integer.class.getPackage()); // package java.lang, Java Platform API Specification, version 1.8

        Class<? extends ArrayList> concreteClass = new ArrayList<>().getClass();
        pa(concreteClass.getInterfaces()); // {interface java.util.List,interface java.util.RandomAccess,interface java.lang.Cloneable,interface java.io.Serializable}
        pa(concreteClass.getGenericInterfaces()); // {java.util.List<E>,interface java.util.RandomAccess,interface java.lang.Cloneable,interface java.io.Serializable}

        p(listClass.getEnclosingMethod()); // null
        p(listClass.getEnclosingConstructor()); // null
        p(name.getClass().getEnclosingConstructor()); // null

        p(concreteClass.getDeclaringClass()); // null

        pa(enumClass.getEnumConstants()); // {MALE,FEMALE}

        pa(concreteClass.getFields()); // {}

        pa(name.getClass().getMethods()); // {public java.lang.String springstudy.apachecommons.Name.toString(), ...
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }
}
