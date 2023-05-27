package cn.design;

public class ZhPizza extends Pizza {

    PizzaIngredientFactory ingredientFactory;

    public ZhPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void papare() {
        System.out.println("准备："+ name);
        cheese = ingredientFactory.createCheese();

    }

}
