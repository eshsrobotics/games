import java.util.Scanner;
public class Main {
    public static void main (String args[]) {
        Boolean gameOver = false;
        int playerTurn = 1;
        Scanner A = new Scanner(System.in);
        String[][] box = {{"1","2","3"}, {"4","5","6"},{"7","8","9"}};
        while (!gameOver){
            System.out.print("player "+playerTurn+" choses column");
            int c = A.nextInt();
            System.out.print("player "+playerTurn+" choses row");
            int r = A.nextInt();
            if (!box[r-1][c-1].equals("x") && !box[r-1][c-1].equals("o")){
                if (playerTurn == 1) {
                    box[r-1][c-1]= "x";
                    playerTurn = 2;
                } else {
                    box[r-1][c-1]= "o";
                    playerTurn = 1;
                }
                System.out.println("    column 1 column 2 column 3");
                System.out.println("            |       |      ");
                System.out.println("row 1   "+box[0][0]+"   |   "+box[0][1]+"   |   "+box[0][2]+"  ");
                System.out.println("     _______|_______|______");
                System.out.println("            |       |      ");
                System.out.println("row 2   "+box[1][0]+"   |   "+box[1][1]+"   |   "+box[1][2]+"  ");
                System.out.println("     _______|_______|______");
                System.out.println("            |       |      ");
                System.out.println("row 3   "+box[2][0]+"   |   "+box[2][1]+"   |   "+box[2][2]+"  ");
                System.out.println("            |       |      ");
            } else System.out.println("Space has been used, try again");
        }
    }
}
