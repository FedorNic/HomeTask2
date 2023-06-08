/**
 * 2.* Переработать метод проверки победы, логика проверки победы должна работать для поля 5х5 и
 * количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
 * возможных ситуаций! Используйте вспомогательные методы, используйте циклы!
 * <p>
 * 3.**** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.
 */


import java.util.Random;
import java.util.Scanner;

public class HomeTask2 {

    private static final int WIN_COUNT;
    private static final char DOT_HUMAN;
    private static final char DOT_AI;
    private static final char DOT_EMPTY;
    private static final Scanner SCANNER;
    private static final Random random;
    private static char[][] field;
    private static int fieldSizeX, fieldSizeY;

    private static final StringBuilder firstLine;
    private static final StringBuilder footer;
    private static int count;

    static {
        WIN_COUNT = 4;
        DOT_HUMAN = 'X';
        DOT_AI = 'O';
        DOT_EMPTY = ' ';
        SCANNER = new Scanner(System.in);
        random = new Random();
        firstLine = new StringBuilder();
        footer = new StringBuilder();
        count = 0;
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру 'Крестики-нолики!'");
        do {
            initialize();
            firstLineGenerate(firstLine);   //Инициализируем первую строку (неизменяемая)
            footerGenerate(footer); //Инициализируем разделитель (неизменяемый)
            printField();
            while (true) {
                humanTurn();
                count++;
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                count++;
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
        } while (SCANNER.next().equalsIgnoreCase("Y"));
    }

    private static void initialize() {
        System.out.println("Введите размер поля по оси X: ");
        if (SCANNER.hasNextInt()) {
            fieldSizeX = SCANNER.nextInt();
        }
        System.out.println("Введите размер поля по оси Y: ");
        if (SCANNER.hasNextInt()) {
            fieldSizeY = SCANNER.nextInt();
        }
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     */
    private static void printField() {
        System.out.println(firstLine);
        //#region Тело поля
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeY; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }
        //#endregion
        System.out.println(footer);
    }

    private static void firstLineGenerate(StringBuilder firstLine) {
        firstLine.append("* ");
        for (int i = 1; i <= fieldSizeY; i++) {
            firstLine.append(i).append(" ");
        }
    }

    private static void footerGenerate(StringBuilder footer) {
        for (int i = 1; i <= fieldSizeY * 2; i++) {
            footer.append("-");
        }
    }

    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, ячейка является пустой
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность массива, игрового поля)
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера
     */
    private static void aiTurn() {
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Метод проверки состояния игры
     */
    static boolean gameCheck(char c, String str) {
        if (checkWin(c)) {
            System.out.println(str);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }

        return false; // Игра продолжается
    }

    /**
     * Проверка победы
     */
    static boolean checkWin(char c) {
        // for (int i = 0; i < fieldSizeX; i++) {
        //     for (int j = 0; j < fieldSizeY; j++)
        //         // Проверка по WIN_COUNT горизонталям
        //         if (field[i][j] == c && fieldSizeY - i >= WIN_COUNT)
        //             for (int k = i + 1; k < WIN_COUNT + 1; k++) {
        //                 int win = 1;

        //             }
        // }

       // Проверка по трем горизонталям
       if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
       if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
       if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

       // Проверка по диагоналям
       if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
       if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

       // Проверка по трем вертикалям
       if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
       if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
       if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        return false;
    }

    /**
     * Проверка на ничью
     */
    static boolean checkDraw() {    // Проверка по количеству ходов (без двойного цикла)
        return count == fieldSizeY * fieldSizeY;
    }
}