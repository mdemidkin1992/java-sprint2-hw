import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        Scanner scanner = new Scanner(System.in);
        HashMap<String, ArrayList<MonthRowData>> allMonthData = null;
        ArrayList<YearRowData> yearData = null;
        ArrayList<YearRowData> monthData = null;

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                allMonthData = monthlyReport.addMonths();
                monthData = monthlyReport.calculateTotals(allMonthData);
            } else if (command == 2) {
                yearData = yearlyReport.addYear();
            } else if (command == 3) {
                monthlyReport.compareMonthToYear(monthData, yearData);
            } else if (command == 4) {
                monthlyReport.showMonthStats(allMonthData);
            } else if (command == 5) {
                yearlyReport.showYearStats(yearData);
            } else if (command == 0) {
                System.out.println("Выход из приложения");
                break;
            } else {
                System.out.println("Такой команды еще не существует");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчеты");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Сверить отчеты");
        System.out.println("4 - Вывести информацию о всех месячных отчетах");
        System.out.println("5 - Вывести информацию о годовом отчете");
        System.out.println("0 - Выход");
    }
}