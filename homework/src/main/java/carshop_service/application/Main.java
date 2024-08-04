package carshop_service.application;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationFacade facade = new ApplicationFacade();
        facade.initialize();
    }
}
