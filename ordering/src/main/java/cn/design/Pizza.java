package cn.design;

public abstract class Pizza {

    String name;
    Cheese cheese;

    int price;

    public void bake(){
        System.out.println(" 烤pizza ");
    }

    public void cut (){
        System.out.println(" 切pizza ");
    }

    abstract void papare();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
