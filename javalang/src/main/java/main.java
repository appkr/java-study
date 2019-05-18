// src/main/java/main.java

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class main {

    static Scanner sc = new Scanner(System.in);
    final double SHORPI = 3.14159;
    public enum Day {MONDAY, TUESDAY, WEDNESDAY};

    public static void main(String[] args) {
        System.out.println("Hello World");
        pd(main.class); // class main
        pd("Foo".getClass()); // class java.lang.String

        /*Variables*/

        int var1 = 100;
        int v2, v3;

        /*Data Types*/

        // Primitive: byte, short, char, boolean, int, float, double, long
        // Boxed: Byte, Short, Character, Boolean, Integer, Float, Double, Long
        pt("Byte MIN and MAX:");
        pd(Byte.MIN_VALUE + "," + Byte.MAX_VALUE); // -128,127

        boolean happy = true;
        char a = 'a';
        float fNum = 1.11111111F;
        pt("Float Precision:");
        pd(fNum + 0.0123456789F); // 1.1234568

        double dblNum = 0.12345678901234567890;
        double thousand = 1e+3;
        pt("Double Precision:");
        pd(dblNum + thousand); // 1000.1234567890124

        long bigNum = 123_456_789;

        /*Type Casting*/

        int smallInt = 10;
        long smallLong = smallInt;
        pt("Small to Large Casting has no issue:");
        pd(smallLong); // 10

        double cDbl = 1.234;
        int cInt = (int) cDbl;
        long bLong = 1247381240L;
        int sInt = (int) bLong;
        pt("Large to Small makes issue:");
        pd(cInt); // 1
        pd(sInt); // 1247381240

        String favNum = Double.toString(1.5);
        int strInt = Integer.parseInt("10");
        pt("Parsed from string to int:");
        pd(strInt); // 10

        pt("Implicit casting:");
        pd("K" + 9); // K9

        // Byte.parseByte() Short.parseShort() Boolean.parseBoolean() Integer.parseInt()
        // Float.parseFloat() Double.parseDouble() Long.parseLong()

        // Integer.toString() Integer.toUnsignedLong() Integer.toBinaryString()
        // Integer.toHexString() Integer.toOctalString() Integer.toUnsignedString()

        pd(Integer.valueOf(1)); // 1
        pd(Integer.valueOf("1")); // 1
        pd(Long.valueOf(1)); // 1
        pd(Long.valueOf("1")); // 1

        /*Math*/

        pt("Math:");
        pd(Math.floor(4.25)); // 4.0
        pd(Math.PI); // 3.141592653589793

        int min = 5;
        int max = 20;
        int rand = (int) (Math.random() * ((max - min) + 1));
        int randNum = min + rand;
        pt("Random:");
        pd(randNum); // 20

        /*String*/
        pt("Strings:");
        pd("Foo".charAt(0)); // F
        pd("Foo". contains("F")); // true
        pd("Foo".indexOf("o")); // 1

        pt("For String comparison, ALWAYS USE `equals()`, and NEVER USE `==`:");
        pd("Foo" == "Foo"); // true ???
        pd("Foo".equals("Foo")); // true
        pd("fOo".equalsIgnoreCase("FoO")); // true
        pd("Foo".compareTo("Foo")); // 0
        pd("Foo".compareTo("Boo")); // 4
        pd("Foo".replace("Fo", "Bo")); // Boo
        pd("Foo".substring(0, 1)); // F

        for (String t: "Foo".split("")) {
            pd(t); // F o o
        }
        // trin(), toUpperCase, toLoserCase...

        /*String Builder*/
        pt("String Builder:");
        StringBuilder sb = new StringBuilder("Foo");
        pd(sb.length()); // 3
        pd(sb.capacity()); // 19
        pd(sb.append(" Yeah!")); // Foo Yeah!
        pd(sb.insert(6, "^^")); // Foo Ye^^ah!
        pd(sb.replace(6, 8, "^/")); // Foo Ye^/ah!
        pd(sb.substring(6, 8)); // ^/
        pd(sb.delete(6, 8)); // Foo Yeah!
        pd(sb.charAt(0)); // F
        pd(sb.indexOf("F")); // 0

        /*Arrays*/

        pt("Arrays");
        Integer[] arr = new Integer[3];
        arr[0] = 1;
        pArr(arr); // 1
        Arrays.fill(arr, 2);
        pArr(arr); // 2 2 2

        String[] arr2 = {"Foo", "Bar"};
        Arrays.sort(arr2);
        pArr(arr2); // Bar Foo

        long[] oneToTen = IntStream.rangeClosed(1, 3).mapToLong(i -> i).toArray();

        pt("ArrayUtil for Type Juggling:");
        pArr(ArrayUtils.toObject(oneToTen)); // 1 2 3

        pt("Multi-dimensional Array:");
        int[][] arr3 = new int[2][2];
        String[][] arr4 = {
            {"Foo", "Bar"},
            {"Baz", "Qux"},
        };
        pd(arr4[1][1]); // Qux

        pt("Copy Array:");
        int[] arr5 = {1, 2, 3};
        int[] arr6 = Arrays.copyOf(arr5, 3);
        pd(Arrays.equals(arr5, arr6)); // true
        pd(Arrays.toString(arr6)); // [1, 2, 3]

        /*Array List*/

        pt("ArrayList:");
        ArrayList<String> al1 = new ArrayList<>(3);
        al1.add("Foo");
        pd(al1.toString()); // [Foo]
        ArrayList<Integer> al2 = new ArrayList<>(Arrays.asList(1,2,3));
        pd(al2.toString()); // [1, 2, 3]
        al2.remove(2);
        al2.set(0, 5);
        pd(al2.get(0)); // 5
        al2.clear();
        pd(al2.toString()); // []

        pt("Iterator:");
        al1.add("Bar");
        Iterator it = al1.iterator();
        // al1.add("Bar"); // ConcurrentModificationException
        while(it.hasNext()) {
            pd(it.next()); // Foo Bar
        }

        pt("Multiple Types:");
        List<Object> al3 = Arrays.asList("Foo", 11);
        pd(al3.toString()); // [Foo, 11]

        /*Linked List*/

        LinkedList<Integer> il1 = new LinkedList<Integer>();
        il1.add(1);
        il1.add(2);
        il1.add(3);
        il1.addAll(Arrays.asList(4,5,6));
        il1.addFirst(0);

        pt("Linked List:");
        pd(il1.toString()); // [0, 1, 2, 3, 4, 5, 6]
        pd(il1.contains(3)); // true
        pd(il1.indexOf(1)); // 1
        il1.set(0, 10);
        pd(il1.get(0)); // 10
        // pd(il1.get(100)); // IndexOutOfBoundsException
        pd(il1.getFirst()); // 10
        pd(il1.getLast()); // 6
        il1.remove(0);
        pd(il1.get(0)); // 1
        pd(il1.toString()); // [1, 2, 3, 4, 5, 6]
        pd(il1.size()); // 6

        Object[] ar1 = il1.toArray();
        pArr(ar1); // 1 2 3 4 5 6

        /*User Input*/

//        userInput();

        /*Enum*/

        pt("Enum:");
        Day favDay = Day.MONDAY;
        pd(favDay); // MONDAY

        /*Stream*/

        List<Integer> oneToThree = IntStream.rangeClosed(1,3)
            .boxed()
            .collect(Collectors.toList());
        pt("Stream:");
        pd(oneToThree.toString()); // [1, 2, 3]

        List<Integer> squares = oneToThree.stream()
            .map(x -> x * x).collect(Collectors.toList());
        pd(squares.toString()); // [1, 4, 9]

        List<Integer> evens = oneToThree.stream()
            .filter(x -> x % 2 == 0)
            .collect(Collectors.toList());
        pd(evens.toString()); // [2]

        IntStream limitToThree = IntStream.range(1, 10).limit(3);
        limitToThree.forEach(System.out::println); // 1 2 3

        // Note range(int startInclusive, int endExclusive)
        int sumAll = IntStream.range(1, 10).reduce(0, (x, y) -> x + y);
        pd(sumAll); // 45

        DoubleStream dblStream = IntStream.range(1, 5).mapToDouble(x -> x);
        pd(dblStream.boxed().collect(Collectors.toList()).toString()); // [1.0, 2.0, 3.0, 4.0]

        List<Integer> oneToFour = new ArrayList<>(Arrays.asList(1,2,3,4));
        oneToFour.forEach(x -> pd(x * 2)); // 2 4 6 8

        /*File IO*/

//        handleFileIO();
//        handleBinaryFile();

        /*Generics*/

        String[] ga1 = {"Foo", "Bar"};
        Integer[] ga2 = {1,2,3};
//        public static <E> void printStuff(E[] arr) {
//            for(E x : arr) System.out.println(x);
//        }

//        public static void printAL(List<?> al) {
//            for(Object x : al) System.out.println(x);
//        }

        pt("Generic Class:");
        MyGeneric myGG = new MyGeneric();
        myGG.setVal("Foo");
        pd(myGG.getVal()); // Foo
        myGG.setVal(1);
        pd(myGG.getVal()); // 1
        MyGeneric<Integer> myGI= new MyGeneric<Integer>();
        myGI.setVal(1);
        pd(myGI.getVal()); // 1

        /*Thread*/

        runThreads();
        runBank();
    }

    private static void userInput() {
        System.out.print("Enter a name:");
        if (sc.hasNextLine()) {
            String userName = sc.nextLine();
            pd(userName);
        }

        String jopName = JOptionPane.showInputDialog("Enter a name:");
        pd(jopName);
    }

    private static void handleFileIO() {
        pt("Create File:");
        File f1 = new File("f1.log");
        try {
            if (f1.createNewFile()) {
                File f2 = new File("f1.log.bak");
                f1.renameTo(f2);
                f1.delete(); f2.delete();
            }
        } catch (IOException ex) {
            // handle
        }

        pt("Create Directory:");
        File d1 = new File(".");
        if (d1.isDirectory()) {
            File[] files = d1.listFiles();
            pd(files.toString());
            pArr(files);
        }

        pt("Write content to a File:");
        File f3 = new File("f3.txt");
        try {
            FileWriter fw = new FileWriter(f3);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("This is a sample text!");
            pw.close();
        } catch (IOException ex) {
            // handle
        }

        pt("Read content from a File:");
        f3 = new File("f3.txt");
        try {
            FileReader fr = new FileReader(f3);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine(); // Read fist line
            while (text != null) {
                pd(text);
                text = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            // handle
        } finally {
            f3.delete();
        }
    }

    private static void handleBinaryFile() {
        pt("Create binary data to a File:");
        File f1 = new File("f1.dat");
        FileOutputStream fos;
        BufferedOutputStream bos;
        DataOutputStream dos;
        try {
            fos = new FileOutputStream(f1);
            bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);

            String name = "Foo";
            Integer age = 11;
            Double balance = 100.50;

            dos.writeUTF(name);
            dos.writeInt(age);
            dos.writeDouble(balance);
            dos.close();
        } catch (IOException ex) {
            // handle
        }

        pt("Read binary data from a File:");
        f1 = new File("f1.dat");
        try {
            FileInputStream fis = new FileInputStream(f1);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            pd(dis.readUTF());
            pd(dis.readInt());
            pd(dis.readDouble());
            dis.close();
        } catch (IOException ex) {
            //
        }
    }

    private static void runThreads() {
        pt("Run threads:");
        Thread t1 = new Thread(new MyThread(), "Thread 1");
        Thread t2 = new Thread(new MyThread(), "Thread 2");
        Thread t3 = new Thread(new MyThread(), "Thread 3");
        t1.start();
        t2.start();

        Thread tL = new Thread(new MyThread(), "Thread C");
        try {
            t3.start();
            t3.join();
        } catch (InterruptedException ex) {
            //
        }
        pt("Run threads with temporal coupling:");
        tL.start();

        // Run threads: -------------------------------------------------------------------
        // # of Active Threads: 4
        //     Start Thread: Thread 1
        // # of Active Threads: 4
        //     Start Thread: Thread 2
        // # of Active Threads: 4
        //     Start Thread: Thread 3
        //     End Thread: Thread 3
        //     End Thread: Thread 1
        //     End Thread: Thread 2
        // Run threads with temporal coupling: --------------------------------------------
        // # of Active Threads: 2
        //     Start Thread: Thread C
        //     End Thread: Thread C
    }

    private static void runBank() {
        pt("Run a Bank:");
        Thread bo1 = new Thread(new BankOperation(), "ATM1");
        Thread bo2 = new Thread(new BankOperation(), "ATM2");
        Thread bo3 = new Thread(new BankOperation(), "ATM3");
        bo1.start();
        bo2.start();
        bo3.start();

        // Run a Bank: --------------------------------------------------------------------
        // A customer arrived at the #ATM1
        // A customer arrived at the #ATM3
        // A customer arrived at the #ATM2
        // Customer requested $10
        // Current bank balance $90
        // Customer requested $10
        // Current bank balance $80
        // Customer requested $10
        // Current bank balance $70
        // A customer arrived at the #ATM1
        // Customer requested $10
        // Current bank balance $60
        // A customer arrived at the #ATM3
        // Customer requested $10
        // Current bank balance $50
        // A customer arrived at the #ATM2
        // Customer requested $10
        // Current bank balance $40
        // A customer arrived at the #ATM3
        // Customer requested $10
        // Current bank balance $30
        // A customer arrived at the #ATM1
        // Customer requested $10
        // Current bank balance $20
        // A customer arrived at the #ATM2
        // Customer requested $10
        // Current bank balance $10
    }

    private static void pt(String title) {
        System.out.println(title +
            " " +
            StringUtils.repeat("-", (80 - 1 - title.length())));
    }

    private static void pd(Object data) {
        System.out.println(data.toString());
    }

    private static void pArr(Object[] array) {
        for (Object e : array) {
            if (e == null) {
                continue;
            }
            pd(e.toString());
        }
    }

}

class MyGeneric<T> {

    private T val;

    public void setVal(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("# of Active Threads: " + Thread.activeCount());
        System.out.println("Start Thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000); // milliseconds
        } catch (InterruptedException ex) {
            //
        }
        System.out.println("End Thread: " + Thread.currentThread().getName());
    }

}

class BankOperation implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("A customer arrived at the #" + Thread.currentThread().getName());
            BankAccount bankAccount;
            try {
                bankAccount = BankAccount.getInstance("");
                bankAccount.withdraw(10);
                Thread.sleep(1000);
            } catch (Exception ex) {
                //
            }
        }
    }

}

class BankAccount {

    private static BankAccount INSTANCE;
    private static Integer balance = 100;
    private static Object customer;

    public static BankAccount getInstance(Object customer) {
        if (INSTANCE == null) {
            INSTANCE = new BankAccount();
        }
        INSTANCE.customer = customer;
        return INSTANCE;
    }

    public static Integer getBalance() {
        return balance;
    }

    public synchronized void withdraw(Integer money) {
        try {
            if (balance >= money) {
                System.out.println("Customer requested $" + money);
                balance -= money;
                System.out.println("Current bank balance $" + balance);
            } else {
                // Must not reach here
                throw new Exception("Insufficient balance in the Bank");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
