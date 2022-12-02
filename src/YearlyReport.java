import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {

    ReadFile readFile = new ReadFile();
    String[] months = {"Январь", "Февраль", "Март"};


    public ArrayList<YearRowData> addYear() {
        List<String> lines = readFileContents(readFile.readYearFile());
        ArrayList<YearRowData> yearData = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] rowData = lines.get(i).split(",");
            YearRowData yearRowData = new YearRowData(
                    Integer.parseInt(rowData[0]),
                    Integer.parseInt(rowData[1]),
                    Boolean.parseBoolean(rowData[2])
            );
            yearData.add(yearRowData);
        }
        System.out.println("Годовые данные загружены.\n");
        return yearData;
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

    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}
