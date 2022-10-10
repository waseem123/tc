import java.util.Scanner;
public class Submenus {
    public boolean login() {
        return true;
    }

    public void getPropertyMenu() {
        while (true) {
            System.out.println("1. ADD PROPERTY DETAILS");
            System.out.println("2. CALCULATE PROPERTY TAX");
            System.out.println("3. DISPLAY ALL PROPERTIES");
            System.out.println("4. BACK TO MAIN MENU");
            Scanner sc = new Scanner(System.in);
            Operations o = new Operations();
            switch (sc.nextInt()) {
                case 1:
                    o.addProperty();
                    break;
                case 2:
                    o.getPropertyTax();
                    break;
                case 3:
                    o.getAllProperties();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("ERROR : INVALID CHOICE. PLEASE SELECT THE OPTION BETWEEN 1 TO 4.");
            }
        }
    }

    public void getVehicleMenu() {
        while (true) {
            System.out.println("1. ADD VEHICLE DETAILS");
            System.out.println("2. CALCULATE VEHICLE TAX");
            System.out.println("3. DISPLAY ALL VEHICLES");
            System.out.println("4. BACK TO MAIN MENU");
            Scanner sc = new Scanner(System.in);
            Operations o = new Operations();
            switch (sc.nextInt()) {
                case 1:
                    o.addVehicle();
                    break;
                case 2:
                    o.getVehicleTax();
                    break;
                case 3:
                    o.getAllVehicles();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("ERROR : INVALID CHOICE. PLEASE SELECT THE OPTION BETWEEN 1 TO 4.");
            }
        }

    }
}