package cn.design;

public class NYChessFactory implements PizzaIngredientFactory {

    @Override
    public Cheese createCheese() {
        Cheese cheese = new Cheese();
        return cheese;
    }
    @Override
    public Cheese createCheese(int i) {
        Cheese cheese = new Cheese("wo", i,"sdf");
        return cheese;
    }
    @Override
    public void createDought() {

    }
}
