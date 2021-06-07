import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class TrainBooking extends Application {
    static final int SEATING_CAPACITY = 42;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //inserting the Hashmaps and Scanners to the code
        Scanner sc = new Scanner(System.in);
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();

        String input = "0";
        menu(sc, map, map2, input);
    }

    private void menu(Scanner sc, HashMap<String, String> map, HashMap<String, String> map2, String input) {
        menu:
        while (true) {
            System.out.println(" Enter \"A\" to add a Customer");
            System.out.println(" Enter \"V\" to view all Seats");
            System.out.println(" Enter \"E\" to view Empty Seats");
            System.out.println(" Enter \"D\" to to Delete a booked Seat");
            System.out.println(" Enter \"F\" to Find a seat by Customer Name");
            System.out.println(" Enter \"S\" to Store");
            System.out.println(" Enter \"L\" to to Load");
            System.out.println(" Enter \"O\" to Sort");
            System.out.println(" Enter \"Q\" to Quit");

            String option = sc.next();
            switch (option) {
                case "A":
                case "a":
                    addCustomer(map, sc, input, map2);
                    break;
                case "V":
                case "v":
                    viewSeats(input, sc, map, map2);
                    break;
                case "E":
                case "e":
                    emptySeats(input, sc, map, map2);
                    break;
                case "D":
                case "d":
                    deleteSeat(map, sc, input, map2);
                    break;
                case "F":
                case "f":
                    findSeat(map, sc, input, map2);
                    break;
                case "S":
                case "s":
                    storeDataC2B(map);
                    storeDataB2C(map2);
                    break;
                case "L":
                case "l":
                    loadDataC2B(map);
                    loadDataB2C(map2);
                    break;
                case "O":
                case "o":
                    sort(map, map2);
                    break;
                case "Q":
                case "q":
                    System.out.println("\nProgram is now exiting!!!!");
                    break menu;
            }
        }
    }

    public class readFileB2C {
        private Scanner loadFileB2C;

        public void openFileB2C() {                                     //opens the file
            String FileNameB2C = "StoredDataB2C.txt";
            try {
                loadFileB2C = new Scanner(new File(FileNameB2C));
            } catch (Exception e) {
                System.out.println("\nColombo to Badulla load program Error !!!");
            }
        }

        public void readRecordB2C(HashMap<String, String> map2) {
            ArrayList<String> y = new ArrayList<String>();

            while (loadFileB2C.hasNext()) {                                //load values to a array list
                String a = loadFileB2C.next();
                y.add(a);
            }


            for (int i = 0; i < y.size(); i++) {                           //getting values from arrayList and putting it to the Hashmap
                if (i % 2 == 0) {
                    map2.put(y.get(i), "");
                } else {
                    map2.replace(y.get(i - 1), y.get(i));
                }

            }
        }

        public void closeFileB2C() {
            loadFileB2C.close();
        }                  //close File
    }

    private void loadDataB2C(HashMap<String, String> map2) {
        String FileName = "StoredDataB2C.txt";
        File file = new File(FileName);
        if (file.exists()) {                                                 //check if stored file exist
            map2.clear();
            TrainBooking.readFileB2C loadDataB2C = new TrainBooking.readFileB2C();
            loadDataB2C.openFileB2C();
            loadDataB2C.readRecordB2C(map2);
            loadDataB2C.closeFileB2C();
            System.out.println("\nProgram data loaded from file.");
        } else {
            System.out.println("\nCan\'t load\nNo stored file");
        }
    }

    public static class createFileB2C {
        private Formatter storeFileB2C;

        public void openFileB2C() {
            String FileNameB2C = "StoredDataB2C.txt";
            try {
                storeFileB2C = new Formatter(FileNameB2C);                      //creates a File
            } catch (Exception e) {
                System.out.println("\nBadulla to Colombo store program Error !!!");
            }
        }

        public void addRecordB2C(HashMap<String, String> map2) {

            Set<String> keySet = map2.keySet();                                 //getting all the keys in Hashmap

            ArrayList<String> addBTCKey = new ArrayList<String>(keySet);             //saving keys to a Arraylist

            Collection<String> values = map2.values();                          //getting all the values in Hashmap

            ArrayList<String> addBTCValue = new ArrayList<String>(values);           //saving values to a Arraylist

            for (int indexOfSeat = 0; indexOfSeat < addBTCKey.size(); indexOfSeat++) {
                String storeKey = addBTCKey.get(indexOfSeat);
                String storeValue = addBTCValue.get(indexOfSeat);
                storeFileB2C.format("%s %s ", storeKey, storeValue);
            }
        }

        public void closeFileB2C() {
            storeFileB2C.close();
        }
    }

    public void storeDataB2C(HashMap<String, String> map2) {
        TrainBooking.createFileB2C storeDataB2C = new TrainBooking.createFileB2C();
        storeDataB2C.openFileB2C();
        storeDataB2C.addRecordB2C(map2);
        storeDataB2C.closeFileB2C();
        System.out.println("\nProgram data stored in to file.");
    }

    private void sort(HashMap<String, String> map, HashMap<String, String> map2) {
        ArrayList<String> stringMapVal = new ArrayList<String>();
        ArrayList<String> stringMapKey = new ArrayList<String>();

        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            stringMapKey.add(mapKey);
        }
        for (String mapVal : map.values()) {
            stringMapVal.add(mapVal.replace("_"," "));
        }

        ArrayList<String> stringMap2Val = new ArrayList<String>();
        ArrayList<String> stringMap2Key = new ArrayList<String>();
        for (HashMap.Entry<String, String> entry2 : map2.entrySet()) {
            String map2Key = entry2.getKey();
            stringMap2Key.add(map2Key);
        }

        for (String map2Val : map2.values()) {
            stringMap2Val.add(map2Val.replace("_"," "));
        }


        Scanner sc2 = new Scanner(System.in);

        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        String input2 = sc2.next();


        if (input2.equalsIgnoreCase("1")) {
            for (int traversal = 1; traversal <= stringMapVal.size(); traversal++) {
                for (int fword = 0; fword < stringMapVal.size() - 1; fword++) {
                    int lword = fword + 1;
                    if (stringMapVal.get(lword).compareTo(stringMapVal.get(fword)) < 0) {
                        String rightStringInStrVal = stringMapVal.get(lword);
                        String rightStringInStrKey = stringMapKey.get(lword);

                        stringMapVal.remove(lword);
                        stringMapKey.remove(lword);

                        stringMapVal.add(fword, rightStringInStrVal);
                        stringMapKey.add(fword, rightStringInStrKey);
                    }
                }
                for (int backwardsFword = stringMapVal.size() - 1; backwardsFword > 0; backwardsFword--) {
                    int backwardsLword = backwardsFword - 1;
                    if (stringMapVal.get(backwardsFword).compareTo(stringMapVal.get(backwardsFword - 1)) < 0) {
                        String backwardsRightStringInVal = stringMapVal.get(backwardsFword);
                        String backwardsRightStringInKey = stringMapKey.get(backwardsFword);

                        stringMapVal.remove(backwardsFword);
                        stringMapKey.remove(backwardsFword);

                        stringMapVal.add(backwardsLword, backwardsRightStringInVal);
                        stringMapKey.add(backwardsLword, backwardsRightStringInKey);
                    }
                }
            }
            System.out.println("Colombo ==> Badulla");
            for (int indexOfSeat = 0; indexOfSeat < stringMapVal.size(); indexOfSeat++) {
                System.out.println(stringMapVal.get(indexOfSeat) + "\t=\t" + stringMapKey.get(indexOfSeat));
            }
        } else if (input2.equalsIgnoreCase("2")) {

            for (int traversal = 1; traversal <= stringMap2Val.size(); traversal++) {
                for (int fword = 0; fword < stringMap2Val.size() - 1; fword++) {
                    int lword = fword + 1;
                    if (stringMap2Val.get(lword).compareTo(stringMap2Val.get(fword)) < 0) {
                        String rightStringInStrVal = stringMap2Val.get(lword);
                        String backwardsRightStringInKey = stringMap2Key.get(lword);

                        stringMap2Val.remove(lword);
                        stringMap2Key.remove(lword);

                        stringMap2Val.add(fword, rightStringInStrVal);
                        stringMap2Key.add(fword, backwardsRightStringInKey);
                    }
                }
                for (int backwardsFword = stringMap2Val.size() - 1; backwardsFword > 0; backwardsFword--) {
                    int backwardsLword = backwardsFword - 1;
                    if (stringMap2Val.get(backwardsFword).compareTo(stringMap2Val.get(backwardsFword - 1)) < 0) {
                        String backwardsRightStringInVal = stringMap2Val.get(backwardsFword);
                        String backwardsRightStringInKey = stringMap2Key.get(backwardsFword);

                        stringMap2Val.remove(backwardsFword);
                        stringMap2Key.remove(backwardsFword);

                        stringMap2Val.add(backwardsLword, backwardsRightStringInVal);
                        stringMap2Key.add(backwardsLword, backwardsRightStringInKey);
                    }
                }
            }
            System.out.println("Badulla ==> Colombo");
            for (int indexOfSeat = 0; indexOfSeat < stringMap2Val.size(); indexOfSeat++) {
                System.out.println(stringMap2Val.get(indexOfSeat) + "\t=\t" + stringMap2Key.get(indexOfSeat));
            }
        } else {
            System.out.println("Invalid Input");
        }

    }

    public class readFileC2B {
        private Scanner loadFileC2B;

        public void openFileC2B() {                                                     //opens the file
            String FileNameC2B = "StoredDataC2B.txt";
            try {
                loadFileC2B = new Scanner(new File(FileNameC2B));
            } catch (Exception e) {
                System.out.println("\nColombo to Badulla load program Error !!!");
            }
        }

        public void readRecordC2B(HashMap<String, String> map) {
            ArrayList<String> x = new ArrayList<String>();

            while (loadFileC2B.hasNext()) {                                             //load values to a array list
                String a = loadFileC2B.next();
                x.add(a);
            }


            for (int i = 0; i < x.size(); i++) {                                        //getting values from arrayList and putting it to the Hashmap
                if (i % 2 == 0) {
                    map.put(x.get(i), "");
                } else {
                    map.replace(x.get(i - 1), x.get(i));
                }

            }

        }

        public void closeFileC2B() {
            loadFileC2B.close();
        }                             //close the file
    }

    public void loadDataC2B(HashMap<String, String> map) {
        String FileName = "StoredDataC2B.txt";
        File file = new File(FileName);
        if (file.exists()) {                                                            //check if stored file exist
            map.clear();
            TrainBooking.readFileC2B loadDataC2B = new TrainBooking.readFileC2B();
            loadDataC2B.openFileC2B();
            loadDataC2B.readRecordC2B(map);
            loadDataC2B.closeFileC2B();
            System.out.println("\nProgram data loaded from file!");
        } else {
            System.out.println("\nCan\'t load file!");
        }

    }

    //colombo to badulla
    public static class createFileC2B {
        private Formatter storeFileC2B;

        public void openFileC2B() {
            String FileNameC2B = "StoredDataC2B.txt";
            try {
                storeFileC2B = new Formatter(FileNameC2B);
            } catch (Exception e) {
                System.out.println("\nColombo to Badulla store Error !!!");
            }
        }

        public void addRecordC2B(HashMap<String, String> map) {

            Set<String> keySet = map.keySet();                                      //getting all the keys in Hashmap

            ArrayList<String> C2Bkey = new ArrayList<String>(keySet);                    //saving keys to a Arraylist

            Collection<String> values = map.values();                               //getting all the values in Hashmap

            ArrayList<String> C2Bvalue = new ArrayList<String>(values);                  //saving values to a Arraylist

            for (int seatNum = 0; seatNum < C2Bkey.size(); seatNum++) {
                String storeKey = C2Bkey.get(seatNum);
                String storeValue = C2Bvalue.get(seatNum);
                storeFileC2B.format("%s %s ", storeKey, storeValue);
            }
        }

        public void closeFileC2B() {
            storeFileC2B.close();
        }
    }


    private void storeDataC2B(HashMap<String, String> map) {
        TrainBooking.createFileC2B storeDataC2B = new TrainBooking.createFileC2B();
        storeDataC2B.openFileC2B();
        storeDataC2B.addRecordC2B(map);
        storeDataC2B.closeFileC2B();
        System.out.println("\nProgram data stored in to file.");


    }


    private void addCustomer(HashMap<String, String> map, Scanner sc, String input, HashMap<String, String> map2) {
        System.out.println("--------------------------------");
        System.out.println("----------Add Customer----------");
        System.out.println("--------------------------------\n\n");

        Stage stage = new Stage();
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: black;");

        System.out.println("\"colombo to badulla\" press 1 ");                                                                               //let user to select the destination
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();                                                                                                                  //getting destination

        while (true) {
            if (input.equalsIgnoreCase("1")) {
                for (int i = 1; i <= 42; i++) {

                    if (map.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));                                                                       //if its a booked seat set disables to the button
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        button.setDisable(true);
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color:  #DAA520");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);


                        button.setOnAction(event -> {
                            String bValue = ((Button) event.getSource()).getText();                                                         //getting the button text and putting it to a variable
                            button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3");
                            button.setDisable(true);
                            System.out.println("Enter Your First Name : ");                                                                 //let user to enter their name
                            String fName = sc.next().toUpperCase();
                            System.out.println("Enter Your Last Name : ");
                            String sName = sc.next().toUpperCase();
                            String name = fName + "_" + sName;
                            map.put(bValue, name);                                                                                         //putting seat number as key and name as value

                        });
                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;
            } else if (input.equalsIgnoreCase("2")) {
                for (int i = 1; i <= 42; i++) {

                    if (map2.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color:  #DAA520");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);

                        button.setOnAction(event -> {
                            String bValue = ((Button) event.getSource()).getText();                                                         //getting the button text and putting it to a variable
                            button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3");
                            button.setDisable(true);
                            System.out.println("Enter Your First Name : ");
                            String fName = sc.next().toUpperCase();
                            System.out.println("Enter Your Last Name : ");
                            String sName = sc.next().toUpperCase();
                            String name = fName + "_" + sName;
                            map2.put(bValue, name);                                                                                         //putting seat number as key and name as value

                        });
                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;
            } else {
                System.out.println("\"colombo to badulla\" press 1 ");//if user enter a number out of range again this showsup
                System.out.println("\"badulla to colombo\" press 2 ");
                input = sc.next();
            }

        }
    }


    private void findSeat(HashMap<String, String> map, Scanner sc, String input, HashMap<String, String> map2) {
        System.out.println("----------------------------------");
        System.out.println("----------Find Your Seat----------");
        System.out.println("----------------------------------\n\n");

        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();
        while (true) {
            if (input.equalsIgnoreCase("1")) {
                System.out.println("Enter Your First Name : ");
                String fName = sc.next().toUpperCase();
                System.out.println("Enter Your Last Name : ");
                String sName = sc.next().toUpperCase();
                String cusName = fName + "_" + sName;
                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    if (cusName.equals(entry.getValue())) {                                                                             //checks the entered name to values of the Hashmap
                        System.out.println(entry.getKey());
                    }
                }
                break;
            } else if (input.equalsIgnoreCase("2")) {
                System.out.println("Enter Your First Name : ");
                String fName = sc.next().toUpperCase();
                System.out.println("Enter Your Last Name : ");
                String sName = sc.next().toUpperCase();
                String cusName = fName + "_" + sName;
                for (HashMap.Entry<String, String> entry : map2.entrySet()) {
                    if (cusName.equals(entry.getValue())) {
                        System.out.println(entry.getKey());
                    }
                }
                break;

            } else {
                System.out.println("invalid input");
            }
        }
    }

    private void deleteSeat(HashMap<String, String> map, Scanner sc, String input, HashMap<String, String> map2) {
        System.out.println("--------------------------------");
        System.out.println("----------Delete booked seat----------");
        System.out.println("--------------------------------\n\n");

        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();
        while (true) {
            if (input.equalsIgnoreCase("1")) {
                System.out.println("Enter the seat number : ");
                String seat = sc.next();
                map.remove(seat);                                                                                                      //remove the seat from the map
                System.out.println("seat " + seat + " deleted");
                break;
            } else if (input.equalsIgnoreCase("2")) {
                System.out.println("Enter the seat number : ");
                String seat2 = sc.next();
                map2.remove(seat2);
                System.out.println("seat " + seat2 + " deleted");
                break;
            } else {
                System.out.println("invalid input");
            }
        }
    }

    private void emptySeats(String input, Scanner sc, HashMap<String, String> map, HashMap<String, String> map2) {
        System.out.println("----------------------------------------------");
        System.out.println("----------Here's the Available seats----------");
        System.out.println("----------------------------------------------");


        Stage stage = new Stage();
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: black;");
        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();
        while (true) {
            if (input.equalsIgnoreCase("1")) {
                for (int i = 1; i <= 42; i++) {

                    if (map.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        button.setVisible(false);
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #DAA520 ");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);

                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;


            } else if (input.equalsIgnoreCase("2")) {
                for (int i = 1; i <= 42; i++) {

                    if (map2.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        button.setVisible(false);
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #DAA520 ");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);

                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;
            } else {
                System.out.println("invalid input");
            }
        }
    }

    private void viewSeats(String input, Scanner sc, HashMap<String, String> map, HashMap<String, String> map2) {
        Stage stage = new Stage();
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: black;");
        flowPane.setPadding(new Insets(15));
        flowPane.setHgap(15);
        flowPane.setVgap(15);
        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();
        while (true) {
            if (input.equalsIgnoreCase("1")) {
                for (int i = 1; i <= 42; i++) {

                    if (map.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        button.setDisable(true);
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #DAA520 ");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);

                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;
            } else if (input.equalsIgnoreCase("2")) {
                for (int i = 1; i <= 42; i++) {

                    if (map2.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; ");
                        button.setDisable(true);
                        flowPane.getChildren().add(button);

                    } else {
                        Button button = new Button(String.valueOf(i));
                        button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #DAA520 ");
                        flowPane.setPadding(new Insets(15));
                        flowPane.setHgap(15);
                        flowPane.setVgap(15);
                        flowPane.getChildren().add(button);

                    }

                }
                Scene scene = new Scene(flowPane, 210, 610);
                stage.setScene(scene);
                stage.showAndWait();
                break;

            } else {
                System.out.println("invalid input");
            }
        }
    }
}



