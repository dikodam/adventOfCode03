package com.adiko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage window;
    private TextField tfOutput;

    private List<IntTriplet> triplets;
    private int validTriplets = 0;
    private List<String[]> lines;

    public static void main(String[] args) {
        launch(args);
    }

    // GUI setup
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);


        tfOutput = new TextField();
        Button btnGo = new Button("GO");
        btnGo.setOnAction(e -> doTheMagic());

        layout.getChildren().addAll(btnGo, tfOutput);

        StackPane root = new StackPane();
        root.getChildren().add(layout);
        window.setScene(new Scene(root, 300, 300));
        window.show();
    }

    // business code
    private void doTheMagic() {
        parseInput();
        for (IntTriplet triplet : triplets) {
            if (isValidTriangle(triplet)) {
                validTriplets++;
            }
        }
        tfOutput.setText("valid triangles: " + validTriplets);
    }

    private boolean isValidTriangle(IntTriplet triplet) {
        int x = triplet.getX();
        int y = triplet.getY();
        int z = triplet.getZ();
        return x + y > z && x + z > y && y + z > x;
    }

    private void parseInput() {
        lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line.split(" +"));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        triplets = new ArrayList<>(lines.size());
        createTriplets();
    }

    private void createTriplets() {
        for (int i = 0; i < lines.size(); i += 3) {
            String[] line0 = lines.get(i);
            String[] line1 = lines.get(i + 1);
            String[] line2 = lines.get(i + 2);
            triplets.add(new IntTriplet(Integer.parseInt(line0[1]), Integer.parseInt(line1[1]), Integer.parseInt(line2[1])));
            triplets.add(new IntTriplet(Integer.parseInt(line0[2]), Integer.parseInt(line1[2]), Integer.parseInt(line2[2])));
            triplets.add(new IntTriplet(Integer.parseInt(line0[3]), Integer.parseInt(line1[3]), Integer.parseInt(line2[3])));
        }
    }
}
