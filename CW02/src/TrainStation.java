import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import static javafx.geometry.Pos.CENTER;

public class TrainStation extends Application {
    static final int SEATING_CAPACITY = 42;
    private Passenger[] waitinRoom = new Passenger[42];
    private PassengerQueue trainQueue = new PassengerQueue();
    private Passenger[] boardedPassengers = new Passenger[21];
    private int waitingRoomNext = 0;
    final static String outputFilePathOne = "C2BtrainQueueDB.txt";
    final static String outputFilePathTwo = "B2CtrainQueueDB.txt";

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner sc = new Scanner(System.in);
        HashMap<String, String> map = new HashMap<>();


        String input = "0";
        File file = null;


        System.out.println("\"colombo to badulla\" press 1 ");
        System.out.println("\"badulla to colombo\" press 2 ");
        input = sc.next();
        if (input.equalsIgnoreCase("1")) {
            file = new File(outputFilePathOne);
            loadDataC2B(map);                                   //Loading from the saved File
        } else if (input.equalsIgnoreCase("2")) {
            file = new File(outputFilePathTwo);
            loadDataB2C(map);                                    //Loading from the saved File
        } else {
            System.out.println("\"colombo to badulla\" press 1 ");
            System.out.println("\"badulla to colombo\" press 2 ");
            input = sc.next();
        }
        menu(sc, input, map, file, trainQueue);                              //Menu

    }


    public class readFileB2C {
        private Scanner loadFileB2C;

        public void openFileB2C() {
            String FileNameB2C = "StoredDataB2C.txt";
            try {
                loadFileB2C = new Scanner(new File(FileNameB2C));
            } catch (Exception e) {
                System.out.println("\nColombo to Badulla load program Error !!!");
            }
        }

        public void readRecordB2C(HashMap<String, String> map2) {
            ArrayList<String> y = new ArrayList<String>();

            while (loadFileB2C.hasNext()) {
                String a = loadFileB2C.next();
                y.add(a);
            }
            for (int i = 0; i < y.size(); i++) {
                if (i % 2 == 0) {
                    map2.put(y.get(i), "");
                } else {
                    map2.replace(y.get(i - 1), y.get(i));
                }
            }
        }

        public void closeFileB2C() {
            loadFileB2C.close();
        }
    }


    private void loadDataB2C(HashMap<String, String> map) {
        String FileName = "StoredDataB2C.txt";
        File file = new File(FileName);
        if (file.exists()) {
            map.clear();
            TrainStation.readFileB2C loadDataB2C = new TrainStation.readFileB2C();
            loadDataB2C.openFileB2C();
            loadDataB2C.readRecordB2C(map);
            loadDataB2C.closeFileB2C();
            System.out.println("\nProgram data loaded from file.");
        } else {
            System.out.println("\nCan\'t load\nNo stored file");
        }
    }

    public class readFileC2B {
        private Scanner loadFileC2B;

        public void openFileC2B() {
            String FileNameC2B = "StoredDataC2B.txt";
            try {
                loadFileC2B = new Scanner(new File(FileNameC2B));
            } catch (Exception e) {
                System.out.println("\nColombo to Badulla load program Error !!!");
            }
        }

        public void readRecordC2B(HashMap<String, String> map) {
            ArrayList<String> x = new ArrayList<String>();

            while (loadFileC2B.hasNext()) {
                String a = loadFileC2B.next();
                x.add(a);
            }

            for (int i = 0; i < x.size(); i++) {
                if (i % 2 == 0) {
                    map.put(x.get(i), "");
                } else {
                    map.replace(x.get(i - 1), x.get(i));
                }
            }
        }

        public void closeFileC2B() {
            loadFileC2B.close();
        }
    }

    private void loadDataC2B(HashMap<String, String> map) {
        String FileName = "StoredDataC2B.txt";
        File file = new File(FileName);
        if (file.exists()) {
            map.clear();
            TrainStation.readFileC2B loadDataC2B = new TrainStation.readFileC2B();
            loadDataC2B.openFileC2B();
            loadDataC2B.readRecordC2B(map);
            loadDataC2B.closeFileC2B();
            System.out.println("\nProgram data loaded from file!");
        } else {
            System.out.println("\nCan\'t load file!");
        }
    }


    private void menu(Scanner sc, String input, HashMap<String, String> map, File file, PassengerQueue trainQueue) {
        menu:
        while (true) {
            System.out.println("Enter \"W\" to Add Passengers to waiting room  ");
            System.out.println("Enter \"A\" to Add a passenger to the train queue ");
            System.out.println("Enter \"V\" to View the train queue ");
            System.out.println("Enter \"D\" Delete a passenger from the train queue ");
            System.out.println("Enter \"S\" to Store data ");
            System.out.println("Enter \"L\" to Load data ");
            System.out.println("Enter \"R\" to Run the simulation report ");

            String option = sc.next();
            switch (option) {
                case "A":
                case "a":
                    addPassenger();
                    break;
                case "V":
                case "v":
                    viewTrainQueue();
                    break;
                case "D":
                case "d":
                    deleteSeat(sc);
                    break;
                case "S":
                case "s":
                    storeData(file);
                    break;
                case "L":
                case "l":
                    loadData(file);
                    break;
                case "R":
                case "r":
                    report();
                    break;
                case "w":
                case "W":
                    waitingRoom(map, input, sc);                         //Waiting Room;
                    break;
                case "Q":
                case "q":
                    System.out.println("\nProgram is now exiting!!!!");
                    break menu;
            }
        }
    }

    private void report() {

        while(!trainQueue.isEmpty()){
            int x = random() + random()+ random();
            for(int i= 0; i > trainQueue.getLast() ; i++){
                trainQueue.getQueue()[i].setSecondsINQueue(x);
            }
          boardedPassengers[Integer.parseInt(trainQueue.remove().getSeat() )-1] = trainQueue.remove();
          setQueue(trainQueue.getQueue(),trainQueue.getLast());
          trainQueue.setLast(-1);

        }



    }

    int index = 0;

    private void loadData(File file) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" ");
                for (String s : parts) {
                    System.out.println(s);
                }

                String name = parts[3];
                String seatNum = parts[6];

                System.out.println("seat - " + parts[6]);


                Passenger p = new Passenger();
                p.setName(name);
                p.setSeat(seatNum);

                trainQueue.add(p);


            }
            System.out.println(Arrays.toString(trainQueue.getQueue()));
        } catch (Exception ignored) {}

    }

    private void storeData(File file) {
        BufferedWriter writer = null;

        while (true) {
            try {
                writer = new BufferedWriter((new FileWriter(file, false)));
                for (Passenger pArray : trainQueue.getQueue()) {
                    if (pArray != null) {
                        writer.write(" Name : " + pArray.getName() + " Seat : " + pArray.getSeat());
                        writer.newLine();
                    }
                }
                writer.newLine();
                writer.flush();
                writer.close();
                System.out.println("Done!");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("File not Found!");
            }
        }
    }

    private void deleteSeat(Scanner sc) {
        System.out.println(Arrays.toString(trainQueue.getQueue()));
        System.out.println("Enter the Seat Number ");
        String seat = sc.next();

        for (int i = 0; i < trainQueue.getLength(); i++) {
            if (trainQueue.getQueue()[i].getSeat().equals(seat)) {
                trainQueue.delete(i);
                System.out.println(seat + " deleted!!!");
                break;
            }
        }
    }

    private void viewTrainQueue() {
        System.out.println(Arrays.toString(trainQueue.getQueue()));
        HashMap<String, String> map = new HashMap<>();
        int j = 0;
        for (Passenger p : trainQueue.getQueue()) {
            if (p != null) {
                map.put(trainQueue.getQueue()[j].getSeat(), trainQueue.getQueue()[j].getName());
                j++;
            }
        }

        System.out.println(map.entrySet());

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: black ;");


        for (int i = 1; i <= 42; i++) {

            if (map.containsKey(String.valueOf(i))) {
                Button button = new Button();
                flowPane.getChildren().add(button);
                button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #D3D3D3 ; -fx-text-fill : black");
                button.setText(String.valueOf(i));
                Label names = new Label((map.get(String.valueOf(i))));
                names.setStyle("-fx-pref-height: 28px;-fx-pref-width: 70px; -fx-text-fill : #D3D3D3");
                flowPane.getChildren().add(names);
                flowPane.setPadding(new Insets(15));
                flowPane.setHgap(15);
                flowPane.setVgap(15);

            } else {
                Button button = new Button();
                Label names = new Label("Empty");
                names.setStyle("-fx-pref-height: 28px;-fx-pref-width: 70px;-fx-text-fill : #DAA520");
                button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color : #DAA520 ; -fx-text-fill : black");
                button.setText(String.valueOf(i));
                flowPane.setPadding(new Insets(15));
                flowPane.setHgap(15);
                flowPane.setVgap(15);
                flowPane.getChildren().add(button);
                flowPane.getChildren().add(names);
            }
        }
        Scene scene = new Scene(flowPane, 470, 620);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void addPassenger() {

        int var = random();
        if (trainQueue.isFull()) {                                       // display alert when it s full
            System.out.println("TrainQueue is Full");

        } else {
            if (waitingRoomNext != 0) {                                 //checks whether waiting room is Empty or not
                if (waitingRoomNext >= var) {                           //checks randomnumber equals to the waiting room passengers
                    for (int i = 0; i < var; i++) {
                        trainQueue.add(waitinRoom[0]);
                        System.out.println(waitinRoom[0].getName() + " Added to the train queue ");
                        setQueue(waitinRoom, waitingRoomNext);          //adding first in the waiting room to queue
                        waitingRoomNext--;

                    }

                } else {
                    for (int i = 0; i < waitingRoomNext; i++) {
                        trainQueue.add(waitinRoom[0]);
                        System.out.println(waitinRoom[0].getName() + " Added to the train queue ");
                        setQueue(waitinRoom, waitingRoomNext);
                        waitingRoomNext--;

                    }
                }
            } else {
                System.out.println("Empty");                             //alert when waiting room is empty
            }
        }
        Stage stage = new Stage();
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color : black");
        gridPane.setAlignment(CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        Label heading1 = new Label("Waiting Room");
        heading1.setStyle("-fx-text-fill: #DAA520");
        gridPane.getChildren().add(heading1);
        GridPane.setConstraints(heading1, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        for (int i = 0; i < waitingRoomNext; i++) {
            Label waitingPassenger = new Label();
            waitingPassenger.setText("Customer Name : " + waitinRoom[i].getName() + " Seat Number : " + waitinRoom[i].getSeat());
            waitingPassenger.setStyle("-fx-text-fill: #DAA520");
            gridPane.getChildren().add(waitingPassenger);
            GridPane.setConstraints(waitingPassenger, 0, i + 2, 1, 1);
        }
        Label heading2 = new Label("Train Queue");
        heading2.setStyle("-fx-text-fill:#D3D3D3");
        gridPane.getChildren().add(heading2);
        GridPane.setConstraints(heading2, 4, 0, 1, 1, HPos.CENTER, VPos.CENTER);

        int len = 0;
        for (Passenger p : trainQueue.getQueue()) {
            if (p != null) {
                len++;
            }
        }

        System.out.println(Arrays.toString(trainQueue.getQueue()));

        for (int i = 0; i < len; i++) {
            Label tQueuePassenger = new Label();
            tQueuePassenger.setText("Customer Name : " + trainQueue.getQueue()[i].getName() + " Seat Number" + trainQueue.getQueue()[i].getSeat());
            tQueuePassenger.setStyle("-fx-text-fill:#D3D3D3");
            gridPane.getChildren().add(tQueuePassenger);
            GridPane.setConstraints(tQueuePassenger, 4, i + 2, 1, 1);
        }
        Scene SeatMapScene = new Scene(gridPane, 750, 900);
        stage.setScene(SeatMapScene);
        stage.showAndWait();
    }

    private void waitingRoom(HashMap<String, String> map, String input, Scanner sc) {

        Stage stage = new Stage();
        stage.setTitle("Train Booking System");
        stage.getIcons().add(new Image("logo.png"));
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: black;");
        for (int i = 1; i <= 42; i++) {
            if (map.containsKey(String.valueOf(i))) {                                                                                //checks the seat booked or not
                Button button = new Button(String.valueOf(i));
                button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; -fx-text-fill : black");
                flowPane.getChildren().add(button);
                Label names = new Label((map.get(String.valueOf(i))));
                names.setStyle("-fx-pref-height: 28px;-fx-pref-width: 70px; -fx-text-fill : #D3D3D3");
                flowPane.getChildren().add(names);
                int finalI = i;
                button.setOnAction(event -> {
                    button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color: #D3D3D3; -fx-text-fill : black");
                    button.setDisable(true);
                    waitinRoom[waitingRoomNext] = new Passenger();
                    waitinRoom[waitingRoomNext].setName(map.get(String.valueOf(finalI)));
                    waitinRoom[waitingRoomNext].setSeat(String.valueOf(finalI));
                    waitingRoomNext++;
                    System.out.println(map.get(String.valueOf(finalI)) + " Added to the Waiting room!");
                    map.remove(String.valueOf(finalI));
                });

            } else {

                Button button = new Button(String.valueOf(i));
                button.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px;-fx-background-color:  #DAA520");
                flowPane.setPadding(new Insets(15));
                flowPane.setHgap(15);
                flowPane.setVgap(15);
                flowPane.getChildren().add(button);
                Label names = new Label("Empty");
                names.setStyle("-fx-pref-height: 28px;-fx-pref-width: 70px; -fx-text-fill : #DAA520");
                flowPane.getChildren().add(names);
            }
        }
        Scene scene = new Scene(flowPane, 470, 620);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private int random() {
        Random random = new Random();
        int randomNum = random.nextInt(6) + 1;
        return randomNum;
    }

    private void setQueue(Passenger[] obj, int num) {
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                obj[i] = null;
            } else {
                obj[i] = obj[i + 1];
            }
        }
    }
}

