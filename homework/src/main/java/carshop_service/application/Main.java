package carshop_service.application;

public class Main {

    /**
     * Класс, который отвечает за запуск приложения
     * @param args
     */
    public static void main(String[] args){
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.initialize();
    }
}
