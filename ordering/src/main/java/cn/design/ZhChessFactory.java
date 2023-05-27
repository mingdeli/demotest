package cn.design;

public class ZhChessFactory implements PizzaIngredientFactory {

    @Override
    public Cheese createCheese() {
        Cheese cheese = new Cheese();
        return cheese;
    }
    @Override
    public Cheese createCheese(int i) {
        return null;
    }
    @Override
    public void createDought() {

    }
}
