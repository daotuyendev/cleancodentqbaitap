package view;

import controller.UserController;

import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserController controller = new UserController();

        String choice;

        while(true){
            System.out.println("|=========================|");
            System.out.println("| 1. Add user_____________|");
            System.out.println("| 2. Edit user____________|");
            System.out.println("| 3. Delete user__________|");
            System.out.println("| 4. List user____________|");
            System.out.println("| 5. Exit program_________|");
            System.out.println("|=========================|");
            System.out.print("Your choice: ");
            choice = sc.nextLine();
            switch (choice){
                case "1":
                    controller.createUser();
                    break;
                case "2":
                    controller.editUser();
                    break;
                case "3":
                    controller.deleteUser();
                    break;
                case "4":
                    controller.listUser();
                    break;
                case "5":
                    System.exit(0);
                    break;
                default:
                    System.out.println("1-4");
            }
        }
    }
}
