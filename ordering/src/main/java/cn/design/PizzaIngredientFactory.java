package cn.design;

public interface PizzaIngredientFactory {

    Cheese createCheese();
    Cheese createCheese(int i) ;
    void createDought();
}
