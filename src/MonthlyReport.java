import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class MonthlyReport {
    ReadFile readFile = new ReadFile();
    String[] months = {"Январь", "Февраль", "Март"};

    public HashMap<String, ArrayList<MonthRowData>> addMonths() {
        HashMap<String, ArrayList<MonthRowData>> allMonthData = new HashMap<>();
        for (int monthIndex = 1; monthIndex <= 3; monthIndex++) {
            List<String> lines = readFileContents(readFile.readMonthFile(monthIndex));
            ArrayList<MonthRowData> data = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] rowData = lines.get(i).split(",");
                MonthRowData monthRowData = new MonthRowData(
                        rowData[0],
                        Boolean.parseBoolean(rowData[1]),
                        Integer.parseInt(rowData[2]),
                        Integer.parseInt(rowData[3])
                );
                data.add(monthRowData);
            }
            allMonthData.put(months[monthIndex - 1], data);
        }
        System.out.println("Месячные данные загружены.\n");
        return allMonthData;
    }

    public ArrayList<YearRowData> changeMonthFormat (HashMap<String, ArrayList<MonthRowData>> allMonthData) {
        ArrayList<YearRowData> monthAdjustedReport = new ArrayList<>();
        if (allMonthData == null) {
            System.out.println("Отчеты не были загружены");
        } else {
            for (int i = 0; i < months.length; i ++) {
                ArrayList<MonthRowData> data = allMonthData.get(months[i]);
                int sumExpense = 0;
                int sumIncome = 0;
                for (MonthRowData line : data) {
                    if (line.is_expense) {
                        sumExpense += (line.sum_of_one * line.quantity);
                    } else {
                        sumIncome += (line.sum_of_one * line.quantity);
                    }
                }
                YearRowData expenseRowData = new YearRowData(i+1, sumExpense, true);
                monthAdjustedReport.add(expenseRowData);
                YearRowData incomeRowData = new YearRowData(i+1, sumIncome, false);
                monthAdjustedReport.add(incomeRowData);
            }

            for (int i = 0; i < monthAdjustedReport.size(); i++) {
                if (monthAdjustedReport.get(i).is_expense) {
                    System.out.println("Расходы за месяц " +
                            monthAdjustedReport.get(i).month + ": " +
                            monthAdjustedReport.get(i).amount);
                } else {
                    System.out.println("Доходы за месяц " +
                            monthAdjustedReport.get(i).month + ": " +
                            monthAdjustedReport.get(i).amount);
                }
            }
        }
        return monthAdjustedReport;
    }

    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}