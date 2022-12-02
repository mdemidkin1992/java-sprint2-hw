import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YearlyReport {

    ReadFile readFile = new ReadFile();

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

    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}
