import java.util.Scanner;
public class Main {
    public static void main (String args[]) {
        Scanner A = new Scanner(System.in);
            System.out.print("player 1 choses column");
            int c = A.nextInt();
            System.out.print("player 1 choses row");
            int r = A.nextInt();
            String[] box = {" "," "," "," "," "," "," "," "," "};
            box [                    ]
        System.out.println("    column 1 column 2 column 3");
        System.out.println("            |       |      ");
        System.out.println("row 1   "+box[0]+"   |   "+box[1]+"   |   "+box[2]+"  ");
        System.out.println("     _______|_______|______");
        System.out.println("            |       |      ");
        System.out.println("row 2   "+box[3]+"   |   "+box[4]+"   |   "+box[5]+"  ");
        System.out.println("     _______|_______|______");
        System.out.println("            |       |      ");
        System.out.println("row 3   "+box[6]+"   |   "+box[7]+"   |   "+box[8]+"  ");
        System.out.println("            |       |      ");
    }
}


