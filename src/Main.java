import FacadePattern.*;

public class Main {
    public static void main(String[] args) {
        OrderExecuteFacade facade = new OrderExecuteFacade();
        facade.execute();
    }
}