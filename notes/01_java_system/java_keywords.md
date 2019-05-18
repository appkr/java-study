## Java Keywords

#### `native`
- The native keyword is applied to a method to indicates that the method is implemented in native code using JNI (Java Native Interface)
- [https://www.geeksforgeeks.org/native-keyword-java/](https://www.geeksforgeeks.org/native-keyword-java/)

#### `volatile`
- Java volatile 키워드는 자바 변수를 “메인 메모리에 저장 할” 표식으로 사용합니다. 좀 더 정확하게 말하자면 모든 volatile 변수를 읽어 들일 때 CPU 캐시가 아니라 컴퓨터의 메인 메모리로 부터 읽어들입니다. 그리고 volatile 변수를 쓸 때에도(write) CPU 캐시가 아닌 메인 메모리에 기록합니다.
- [http://thswave.github.io/java/2015/03/08/java-volatile.html](http://thswave.github.io/java/2015/03/08/java-volatile.html)

#### `synchronized`
- 방법 1) synchronized 함수를 만들어 사용한다
- 방법 2) synchronized 블록을 사용한다
- [https://tourspace.tistory.com/54](https://tourspace.tistory.com/54) 

#### Inner Class
- [https://programmingsummaries.tistory.com/63](https://programmingsummaries.tistory.com/63)
```java
class Outer
{
    int value=10;
        
    class Inner
    {
        int value = 20;
        
        public void bb()
        {
            int value = 30;
            
            System.out.println(value); //30
            System.out.println(this.value); //20
            System.out.println(Outer.this.value); //10
        }
    }
}

public class InnerClassExam
{
    public static void main(String[] args)
    {
        Inner i = new Outer().new Inner();
        i.bb();
    }
}
```

#### Nested Class
- Inner Class에 `static` 키워드가 선언된 클래스
- [http://ccm3.net/archives/4679](http://ccm3.net/archives/4679)
```java
public class Test{

    public static void main(String[] args) {
        Outer o = new Outer();
        Outer.Nested on = new Outer.Nested(); // 외부 참조방법
        on.print();
    }
}

class Outer{

    Outer(){
        Nested n = new Nested(); // Outer 클래스에서 객체생성가능
        n.print();
    }
    
    static class Nested{
        public void print(){
            System.out.println("nested");
        }
    }
}
```

#### Local Class
- 메서드 내에 정의된 클래
```java
class Outer{
    public Printable create(final String print){ // final 선언된 변수만 Local 클래스내에서 접근이 가능하다.
        class Local implements Printable{
            @Override
            public void print() {
                System.out.println(print);
            }
        }
        return new Local();
    }
}
```

#### Anonymous
- 이름이 정의되지 않은 클래스
```java
class Outer{
    public Printable create(final String print){
        return new Printable() {
            @Override
            public void print() {  
                System.out.println(print);
            }
        };
    }
}
```
