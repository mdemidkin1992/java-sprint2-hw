import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReportManager reportManager = new ReportManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                reportManager.addMonthData();
                reportManager.changeMonthDataFormat();
            } else if (command == 2) {
                reportManager.addYearData();
            } else if (command == 3) {
                reportManager.compareMonthToYear(reportManager.monthData, reportManager.yearData);
            } else if (command == 4) {
                reportManager.showMonthStats(reportManager.allMonthData);
            } else if (command == 5) {
                reportManager.showYearStats(reportManager.yearData);
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