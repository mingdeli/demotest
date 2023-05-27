package cn.design;

import lombok.Data;

@Data
public class Cheese {
    private String name;
    private String color;
    private int heveay;

    public Cheese( ) {
    }
    public Cheese(String name,int heveay,String color) {
        this.name = name;
        this.heveay = heveay;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cheese{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", heveay=" + heveay +
                '}';
    }
}
