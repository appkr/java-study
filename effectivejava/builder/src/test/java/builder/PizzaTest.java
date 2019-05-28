package builder;

import org.junit.*;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class PizzaTest {

    @Test
    public void canCreateNyPizza() {
        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
            .addTopping(NyPizza.Topping.SAUSAGE)
            .addTopping(NyPizza.Topping.ONION)
            .build();

        assertTrue(pizza instanceof NyPizza);
        assertTrue(pizza.getSize().equals(NyPizza.Size.SMALL));
        assertTrue(pizza.getToppings().size() == 2);
    }

    @Test
    public void canCreateCalzonePizza() {
        Calzone calzone = new Calzone.Builder()
            .addTopping(Calzone.Topping.HAM)
            .sauceInside()
            .build();

        assertTrue(calzone instanceof Calzone);
        assertTrue(calzone.getToppings().size() == 1);
        assertTrue(calzone.isSauceInside() == true);
    }
}
