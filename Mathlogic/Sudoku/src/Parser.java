import java.io.*;
import java.util.LinkedList;
import java.util.List;

class Parser {

    private static List<Cell> res;

    Parser() {
        res = new LinkedList<>();
    }

    List<Cell> getInput(String fileIn) {
        int row = 0;
        int column = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                for (int i = 0; i < inputLine.length(); i++) {
                    if (inputLine.charAt(i) != '.') {
                        res.add(new Cell(row, column, inputLine.charAt(i) - '0'));
                    } else {
                        column++;
                    }
                }
                column = 0;
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    static void print(int[] res, String fileOut) {
        int[][] table = new int[9][9];
        for (int i = 0; i < res.length; i++) {
            Cell cur = Cell.decode(res[i]);
            if (cur != null) {
                if (cur.getDigit() == 0) table[cur.getRow()][cur.getColumn()] = 9;
                else table[cur.getRow()][cur.getColumn()] = cur.getDigit();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    writer.write(table[i][j] + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}
