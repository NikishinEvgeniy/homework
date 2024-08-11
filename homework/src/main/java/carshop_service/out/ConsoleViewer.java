package carshop_service.out;

import carshop_service.constant.ViewConstant;
import carshop_service.entity.Car;
import carshop_service.entity.Client;
import carshop_service.entity.Log;
import carshop_service.entity.Order;
import java.util.List;


public class ConsoleViewer implements Viewer{
    public void showMainPage(){
        System.out.println(ViewConstant.BEGIN_STR);
        System.out.println(ViewConstant.MAIN_MENU_CHOOSE);
    }
    public void showRegistrationTitle(){
        System.out.println(ViewConstant.REGISTRATION_STR);
        System.out.println(ViewConstant.ENTER_YOUR_DATA);
    }
    public void showEnterId(){
        System.out.print(ViewConstant.ENTER_ID);
    }
    public void showEnterPassword(){
        System.out.print(ViewConstant.ENTER_PASSWORD);
    }
    public void showEnterLogin(){
        System.out.print(ViewConstant.ENTER_LOGIN);
    }
    public void showEnterRole(){ System.out.print(ViewConstant.ENTER_ROLE); }
    public void showAuthorizationTitle(){
        System.out.println(ViewConstant.AUTHORIZATION_STR);
        System.out.println(ViewConstant.ENTER_YOUR_DATA);
    }
    public void showAdminCommandPage(){
        System.out.println(ViewConstant.CLIENT_COMMAND);
        System.out.println(ViewConstant.MANAGER_COMMAND);
        System.out.println(ViewConstant.ADMIN_COMMAND);
    }
    public void showManagerCommandPage(){
        System.out.println(ViewConstant.CLIENT_COMMAND);
        System.out.println(ViewConstant.MANAGER_COMMAND);
    }
    public void showClientCommandPage(){
        System.out.println(ViewConstant.CLIENT_COMMAND);
    }
    public void showMainMenuTitle(){
        System.out.println(ViewConstant.MAIN_MENU_TITLE);
    }
    public void showAllCarsPage(List<Car> cars){
        cars.stream().forEach(System.out::println);
    }
    public void showAllClientsPage(List<Client> clients){
        clients.stream().forEach(System.out::println);
    }
    public void showAllLogsPage(List<Log> logs){
        logs.stream().forEach(System.out::println);
    }
    public void showAllOrdersPage(List<Order> orders){
        orders.stream().forEach(System.out::println);
    }
    public void showAvaibleCarsTitle(){
        System.out.println(ViewConstant.AVAIBLE_CAR_TITLE);
    }
    public void showAvaibleOrdersTitle(){
        System.out.println(ViewConstant.AVAIBLE_ORDER_TITLE);
    }
    public void showEnterCarBrand(){
        System.out.print(ViewConstant.ENTER_BRAND);
    }
    public void showEnterName(){
        System.out.print(ViewConstant.ENTER_NAME);
    }
    public void showEnterSurname(){
        System.out.print(ViewConstant.ENTER_SURNAME);
    }
    public void showEnterCarModel(){
        System.out.print(ViewConstant.ENTER_MODEL);
    }
    public void showEnterOrderType(){
        System.out.print(ViewConstant.ENTER_ORDER_TYPE);
    }
    public void showEnterCarPrice(){
        System.out.print(ViewConstant.ENTER_PRICE);
    }
    public void showEnterCarYear(){
        System.out.print(ViewConstant.ENTER_YEAR_OF_RELEASE);
    }
    public void showEnterCondition(){
        System.out.print(ViewConstant.ENTER_CONDITION);
    }
    public void showSuccessTitle(){ System.out.println(ViewConstant.SUCCESS_TITLE); }
    public void showEnterData(){ System.out.println(ViewConstant.ENTER_YOUR_DATA); }
    public void showSimpleCar(Car car){ System.out.println(car); }
    public void showSimpleClient(Client client){ System.out.println(client); }
    public void showSimpleOrder(Order order){ System.out.println(order); }
    public void showUpdateTitle(){ System.out.println(ViewConstant.UPDATE); }
    public void showCreateOrderTitle(){ System.out.println(ViewConstant.CREATE_ORDER); }
    public void showEnterCarId(){
        System.out.print(ViewConstant.ENTER_ID_CAR);
    }
    public void showEnterMonth(){
        System.out.print(ViewConstant.ENTER_MONTH);
    }
    public void showEnterDay(){
        System.out.print(ViewConstant.ENTER_DAY);
    }
    public void showEnterAction(){
        System.out.print(ViewConstant.ENTER_ACTION);
    }
    public void showEnterHour(){
        System.out.print(ViewConstant.ENTER_HOUR);
    }
    public void showEnterClientId(){
        System.out.print(ViewConstant.ENTER_ID_CLIENT);
    }
    public void showEnterChoose(){ System.out.print(ViewConstant.ENTER_CHOOSE); }
    public void showEnterCountOfBuy(){ System.out.print(ViewConstant.ENTER_COUNT_OF_BUY); }
    public void showAvaibleClientsTitle(){
        System.out.println(ViewConstant.AVAIBLE_CLIENTS_TITLE);
    }
    public void showMagazineTitle(){
        System.out.println(ViewConstant.MAGAZINE_TITLE);
    }
    public void showAvaibleFiltersTitle(){
        System.out.println(ViewConstant.AVAIBLE_FILTERS_TITLE);
    }
    public void showAvaibleSortsTitle(){
        System.out.println(ViewConstant.AVAIBLE_SORTS_TITLE);
    }
    public void showOutputMenu(){
        System.out.println(ViewConstant.OUTPUT_ALL_MENU_CHOOSE);
    }
    public void showClientOutputVariableMenu(){
        System.out.println(ViewConstant.CLIENT_OUTPUT_VARIABLE);
    }
    public void showLogsOutputVariableMenu(){
        System.out.println(ViewConstant.MAGAZINE_OUTPUT_VARIABLE);
    }
    public void showCarOutputVariableMenu(){
        System.out.println(ViewConstant.CAR_OUTPUT_VARIABLE);
    }
    public void showOrderOutputVariableMenu(){
        System.out.println(ViewConstant.ORDER_OUTPUT_VARIABLE);
    }
    public void showFilterMenu(){
        System.out.println(ViewConstant.OUTPUT_FILTER_MENU_CHOOSE);
    }

}
