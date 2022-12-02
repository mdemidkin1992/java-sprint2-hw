import java.util.ArrayList;
import java.util.HashMap;

public class ReportManager {

    String[] months = {"Январь", "Февраль", "Март"};

    public void showMonthStats (HashMap<String, ArrayList<MonthRowData>> allMonthData) {
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

    public void showYearStats (ArrayList<YearRowData> yearData) {
        if (yearData == null) {
            System.out.println("Отчеты не были загружены");
        } else {
            System.out.println("Рассматриваемый год: 2021");
            for (int i = 0; i < months.length; i++) {
                int expense = 0;
                int income = 0;
                for (YearRowData line : yearData) {
                    if ((line.month == (i + 1)) && (line.is_expense)) {
                        expense = line.amount;
                    } else if ((line.month == (i + 1)) && (!line.is_expense)) {
                        income = line.amount;
                    }
                }
                int profit = income - expense;
                System.out.println("В месяце " + months[i] + " прибыль: " + profit);
            }

            int sumExpense = 0;
            int sumIncome = 0;
            for (YearRowData line : yearData) {
                if (line.is_expense) {
                    sumExpense += line.amount;
                } else {
                    sumIncome += line.amount;
                }
            }

            int averageExpense = sumExpense / months.length;
            int averageIncome = sumIncome / months.length;

            System.out.println("Средний расход по месяцам: " + averageExpense);
            System.out.println("Средний доход по месяцам: " + averageIncome);
        }
    }

}
