package carshop_service.in;

import java.util.Scanner;

public class ConsoleInfoWriter implements Writer {
    private Scanner scanner;
    public ConsoleInfoWriter(){
        this.scanner = new Scanner(System.in);
    }
    public String getLogin(){ return scanner.next();}
    public String getPassword(){ return scanner.next();}
    public String getRole(){ return scanner.next();}
    public String getModel(){ return scanner.next();}
    public String getBrand(){ return scanner.next();}
    public String getAction(){ return scanner.next();}
    public String getName(){ return scanner.next();}
    public String getSurname(){ return scanner.next();}
    public String getCondition(){ return scanner.next();}
    public String getOrderType(){ return scanner.next();}
    public int getChoose(){ return scanner.nextInt(); }
    public int getId(){ return scanner.nextInt(); }
    public int getCountOfBuy(){ return scanner.nextInt();}
    public int getMonth(){ return scanner.nextInt();}
    public int getDay(){ return scanner.nextInt();}
    public int getHour(){ return scanner.nextInt();}
    public int getYearOfRelease(){ return scanner.nextInt();}
    public double getPrice(){ return scanner.nextDouble();}
}
