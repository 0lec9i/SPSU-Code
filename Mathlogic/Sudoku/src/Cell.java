class Cell {

    private int row;
    private int column;
    private int digit;

    Cell(int row, int column, int digit) {
        this.row = row;
        this.column = column;
        this.digit = digit;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    int getDigit() {
        return digit;
    }

    static int encode(int row, int column, int digit) {
        return row * 9 * 9 + column * 9 + (digit - 1) + 1;
    }

    static Cell decode(int data) {
        if (data > 0) {
            data--;
            return new Cell(data / 81, (data % 81) / 9, (data % 9) + 1);
        } else {
            return null;
        }
    }
}
