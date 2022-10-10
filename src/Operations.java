import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Operations {
    String file_properties = "src/properties.txt";
    String file_vehicles = "src/vehicles.txt";

    public List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();
        File file = new File(file_properties);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNext()) {
            String[] lines = sc.nextLine().split(",");
            for (int i = 0; i < lines.length; i++) {
                String[] content = lines[i].split(":");
                properties.add(new Property(
                        Integer.parseInt(content[0]),
                        Integer.parseInt(content[1]),
                        Integer.parseInt(content[2]),
                        Integer.parseInt(content[3]),
                        (Objects.equals(content[4], "Y")) ? true : false,
                        Double.parseDouble(content[5]))
                );
            }
        }
        return properties;
    }

    public void addProperty() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER THE PROPERTY DETAILS - ");
        System.out.print("ENTER THE BASE VALUE OF LAND - ");
        int base_value_of_land = sc.nextInt();
        System.out.print("ENTER THE BUILT-UP AREA OF LAND - ");
        int built_up_area = sc.nextInt();
        System.out.print("ENTER THE AGE OF LAND IN YEARS - ");
        int age_of_land = sc.nextInt();
        System.out.print("IS THE LAND LOCATED IN CITY?(1. YES, 2.NO) - ");
        int city = sc.nextInt();
        List<Property> properties = getProperties();
        Property p;
        int id = 0;
        if (properties.size() > 0)
            id = properties.get(properties.size() - 1).getProperty_id();
        p = new Property(id + 1, base_value_of_land, built_up_area, age_of_land, (city == 1) ? true : false, 0);

        properties.add(p);

        File file = new File(file_properties);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write(p.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getPropertyTax() {
        Scanner sc = new Scanner(System.in);
        List<Property> properties = getProperties();
        if (properties.size() > 0) {
            displayProperties(properties);
            System.out.print("ENTER THE PROPERTY ID TO CALCULATE THE TAX - ");
            int id = sc.nextInt() - 1;

            if (isPropertyPresent((id + 1))) {
                Property p = properties.get(id);
                int base_price = p.getBase_price();
                int built_up_area = p.getBuilt_up_area();
                int age = p.getProperty_age();
                double tax;

                if (p.isLocated_in_city()) {
                    tax = (built_up_area * age * base_price) + (0.5 * built_up_area);
                } else {
                    tax = built_up_area * age * base_price;
                }
                p.setProperty_tax(tax);

                File file = new File(file_properties);
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file);

                    for (Property property : properties) {
                        fw.write(property.toString());
                    }
                    fw.close();
                    System.out.println("PROPERTY TAX FOR PROPERTY ID - " + (id + 1) + " IS " + tax);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("ERROR : PROPERTY ID YOU ARE LOOKING FOR IS NOT PRESENT.");
            }
        } else {
            System.out.println("ERROR : NO PROPERTY PRESENT AT THIS MOMENT.");
        }
    }

    private boolean isPropertyPresent(int property_id) {
        List<Property> properties = getProperties();
        boolean flag = false;
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getProperty_id() == property_id) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void getAllProperties() {
        displayProperties(getProperties());
    }

    private void displayProperties(List<Property> properties) {
        if (properties.size() > 0) {
            System.out.println("===================================================================================================");
            System.out.printf("%5s %17s %17s %17s %17s %17s", "ID", "BUILT-UP AREA", "BASE PRICE", "AGE(YEARS)", "IN CITY", "PROPERTY TAX");
            System.out.println();
            System.out.println("===================================================================================================");
            for (Property p : properties) {
                System.out.format("%5s %17s %17s %17s %17s %17s", p.getProperty_id(), p.getBuilt_up_area(), p.getBase_price(), p.getProperty_age(), p.isLocated_in_city() == true ? "Y" : "N", p.getProperty_tax());
                System.out.println();
            }
            System.out.println("===================================================================================================");
        } else {
            System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");
        }
    }

    public void addVehicle() {
        List<Vehicle> vehicles = getVehicles();
        Scanner sc = new Scanner(System.in);
        String brand;
        int max_velocity;
        int capacity;
        int vehicle_type;
        int purchase_cost;

        System.out.print("ENTER THE VEHICLE REGISTRATION NUMBER - ");
        String reg_no = sc.next();
        Validator validator = new Validator();
        if (validator.isValidRegistrationNumber(reg_no)) {
            if (isVehiclePresent(reg_no)) {
                System.out.print("VEHICLE WITH SAME REGISTRATION NUMBER ALREADY EXISTS.");
            } else {
                System.out.print("ENTER BRAND OF THE VEHICLE - ");
                brand = sc.next();
                System.out.print("ENTER THE MAXIMUM VELOCITY OF THE VEHICLE(KMPH) - ");
                max_velocity = sc.nextInt();
                System.out.print("ENTER CAPACITY(NUMBER OF SEATS) OF THE VEHICLE - ");
                capacity = sc.nextInt();
                System.out.println("CHOOSE THE TYPE OF THE VEHICLE - ");
                System.out.println("1. PETROL DRIVEN");
                System.out.println("2. DIESEL DRIVEN");
                System.out.println("3. CNG/LPG DRIVEN");
                vehicle_type = sc.nextInt();
                System.out.print("ENTER THE PURCHASE COST OF THE VEHICLE - ");
                purchase_cost = sc.nextInt();

                Vehicle v = new Vehicle(reg_no, brand, max_velocity, capacity, vehicle_type, purchase_cost, 0.0);
                vehicles.add(v);
                File file = new File(file_vehicles);
                try {
                    FileWriter fw = new FileWriter(file, true);
                    fw.write(v.toString());
                    fw.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("INVALID REGISTRATION NUMBER");
        }
    }

    private boolean isVehiclePresent(String reg_no) {
        List<Vehicle> vehicles = getVehicles();
        boolean flag = false;
        for (Vehicle v : vehicles) {
            if (v.getRegistration_number().equals(reg_no)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void getVehicleTax() {
        Scanner sc = new Scanner(System.in);
        List<Vehicle> vehicles = getVehicles();
        if (vehicles.size() > 0) {
            displayVehicles(vehicles);
            System.out.print("ENTER THE REGISTRATION NO OF VEHICLE TO CALCULATE THE TAX - ");
            String id = sc.next();

            if (isVehiclePresent(id)) {
                int vehicleIndex = getVehicleIndex(id);
                Vehicle v = vehicles.get(vehicleIndex);
                int vehicle_type = v.getVehicle_type();
                int max_velocity = v.getMax_velocity();
                int capacity = v.getCapacity();
                int purchase_cost = v.getPurchase_cost();
                double tax = v.getVehicle_tax();

                switch (vehicle_type) {
                    case 1:
                        tax = max_velocity + capacity + ((purchase_cost * 10) / 100);
                        break;
                    case 2:
                        tax = max_velocity + capacity + ((purchase_cost * 11) / 100);
                        break;
                    case 3:
                        tax = max_velocity + capacity + ((purchase_cost * 12) / 100);
                        break;
                }
                v.setVehicle_tax(tax);
                File file = new File(file_vehicles);
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file);

                    for (Vehicle vehicle : vehicles) {
                        fw.write(vehicle.toString());
                    }
                    fw.close();
                    System.out.println("VEHICLE TAX FOR REGISTRATION NO - " + id + " IS " + tax);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("ERROR : REGISTRATION NO YOU ARE LOOKING FOR IS NOT PRESENT.");
            }
        } else {
            System.out.println("ERROR : NO VEHICLE PRESENT AT THIS MOMENT.");
        }
    }

    private int getVehicleIndex(String id) {
        int index = -1;
        List<Vehicle> vehicles = getVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getRegistration_number().equals(id))
                index = i;
        }
        return index;
    }

    public void getAllVehicles() {
        displayVehicles(getVehicles());
    }

    private List<Vehicle> getVehicles() {
        File file = new File(file_vehicles);
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] lines = sc.nextLine().split(",");
                for (int i = 0; i < lines.length; i++) {
                    String[] content = lines[i].split(":");
                    vehicles.add(new Vehicle(
                            content[0],
                            content[1],
                            Integer.parseInt(content[2]),
                            Integer.parseInt(content[3]),
                            Integer.parseInt(content[4]),
                            Integer.parseInt(content[5]),
                            Double.parseDouble(content[6]))
                    );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.size() > 0) {
            System.out.println("==============================================================================================================================");
            System.out.printf("%17s %17s %17s %17s %17s %17s %17s", "REGISTRATION NO.", "BRAND", "MAX. VELOCITY", "NO. OF SEATS", "VEHICLE TYPE", "PURCHASE COST", "VEHICLE TAX");
            System.out.println();
            System.out.println("==============================================================================================================================");
            for (Vehicle v : vehicles) {
                String vehicle_type = "";
                switch (v.getVehicle_type()) {
                    case 1:
                        vehicle_type = "PETROL";
                        break;
                    case 2:
                        vehicle_type = "DIESEL";
                        break;
                    case 3:
                        vehicle_type = "CNG/LPG";
                        break;
                }
                System.out.format("%17s %17s %17s %17s %17s %17s %17s", v.getRegistration_number(), v.getBrand(), v.getMax_velocity(), v.getCapacity(), vehicle_type, v.getPurchase_cost(), v.getVehicle_tax());
                System.out.println();
            }
            System.out.println("==============================================================================================================================");
        } else {
            System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");
        }
    }

    public void getTotalTax() {
        List<Property> properties = getProperties();
        List<Vehicle> vehicles = getVehicles();

        int total_no_of_properties = properties.size();
        int total_no_of_vehicles = vehicles.size();

        double property_sum = 0.0;
        double vehicle_sum = 0.0;

        for (Property p : properties) {
            property_sum += p.getProperty_tax();
        }
        for (Vehicle v : vehicles) {
            vehicle_sum += v.getVehicle_tax();
        }

        System.out.println("+ ------------------------------------------------------------- +");
        System.out.printf("%1s %7s %17s %17s %17s %1s", "|", "SR. NO.", "PARTICULAR", "QUANTITY", "TAX", "|");
        System.out.println();
        System.out.println("+ ------------------------------------------------------------- +");
        System.out.printf("%1s %7s %17s %17s %17s %1s", "|", "1", "PROPERTIES", total_no_of_properties, property_sum, "|");
        System.out.println();
        System.out.printf("%1s %7s %17s %17s %17s %1s", "|", "2", "VEHICLES", total_no_of_vehicles, vehicle_sum, "|");
        System.out.println();
        System.out.println("+ ------------------------------------------------------------- +");
        System.out.printf("%1s %7s %17s %17s %17s %1s", "|", "TOTAL", "----------", total_no_of_properties + total_no_of_vehicles, property_sum + vehicle_sum, "|");
        System.out.println();
        System.out.println("+ ------------------------------------------------------------- +");
    }
}