package cn.design;

public class Store {

    public Pizza createPizza(String item){
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYChessFactory();

        if(item.equalsIgnoreCase("cheese")){
            pizza = new ChessPizza(ingredientFactory);
            pizza.setName("NY pizza,这个是 ");
        }else if (item.equals("hz")){
            pizza = new ZhPizza(new ZhChessFactory());
            pizza.setName("zh,在这个是 中国");
        }
        return pizza;
    }
}
