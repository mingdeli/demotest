package cn.design;

public class ChessPizza extends Pizza {

    PizzaIngredientFactory ingredientFactory;

    public ChessPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void papare() {
        System.out.println("准备："+ name);
        cheese = ingredientFactory.createCheese();

    }

}
