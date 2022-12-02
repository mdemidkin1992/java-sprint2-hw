import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
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

    // Приводим месячную статистику в похожий вид с годовым отчетом
    public ArrayList<YearRowData> calculateTotals (HashMap<String, ArrayList<MonthRowData>> allMonthData) {
        ArrayList<YearRowData> monthAdjustedReport = new ArrayList<>();
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
        return monthAdjustedReport;
    }

    public void compareMonthToYear (ArrayList<YearRowData> monthAdjustedReport, ArrayList<YearRowData> yearReport) {

        if ((monthAdjustedReport == null) || (yearReport == null)) {
            System.out.println("Отчеты не были загружены");
        } else {
            boolean error = false;
            System.out.println("Проверка баланса по месяцам.");
            for (int i = 0; i < yearReport.size(); i++) {
                for (int j = 0; j < monthAdjustedReport.size(); j++) {
                    if ((yearReport.get(i).month == monthAdjustedReport.get(j).month) &&
                            (yearReport.get(i).is_expense == monthAdjustedReport.get(j).is_expense)) {
                        if (!(yearReport.get(i).amount == monthAdjustedReport.get(j).amount)) {
                            error = true;
                            System.out.println("Баланс за месяц " + yearReport.get(i).month + " не сошелся");
                        }
                    }
                }
            }
            if (!error) {
                System.out.println("Баланс сошелся по всем месяцам.");
            }
        }
    }

    public void showMonthStats(HashMap<String, ArrayList<MonthRowData>> allMonthData) {

        if (allMonthData == null) {
            System.out.println("Отчеты не были загружены");
        } else {
            for (String month : months) {
                System.out.println("Статистика за " + month);
                int maxProfit = 0;
                String maxProfitCategory = "";
                int maxExpense = 0;
                String maxExpenseCategory = "";
                ArrayList<MonthRowData> monthData = allMonthData.get(month);
                for (MonthRowData line : monthData) {
                    if (!line.is_expense) {
                        if ((line.sum_of_one * line.quantity) > maxProfit) {
                            maxProfit = line.sum_of_one * line.quantity;
                            maxProfitCategory = line.int_name;
                        }
                    } else {
                        if ((line.sum_of_one * line.quantity) > maxExpense) {
                            maxExpense = (line.sum_of_one * line.quantity);
                            maxExpenseCategory = line.int_name;
                        }
                    }
                }
                System.out.println("Самый прибыльный товар " + maxProfitCategory + " на сумму: " + maxProfit);
                System.out.println("Самая большая трата " + maxExpenseCategory + " на сумму: " + maxExpense + "\n");
            }
        }
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