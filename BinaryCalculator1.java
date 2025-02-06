package javaapplication17;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Optional;
import static javafx.application.Application.launch;

public class BinaryCalculator1 extends Application {

    private TextField display = new TextField();
    private int firstOperand = 0;
    private String currentOperation = "";
    private boolean newNumberExpected = false;

    @Override
    public void start(Stage primaryStage) {
        display.setEditable(false);
        display.setStyle("-fx-font-size: 24; -fx-padding: 15; -fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        // Create operation buttons
        Button addButton = createStyledButton("+", "#4CAF50");
        Button subtractButton = createStyledButton("-", "#2196F3");
        Button multiplyButton = createStyledButton("×", "#FF9800");
        Button divideButton = createStyledButton("÷", "#E91E63");
        Button equalsButton = createStyledButton("=", "#9C27B0");
        Button clearButton = createStyledButton("C", "#607D8B");

        // Create special function buttons
        Button decToBinButton = createStyledButton("Dec→Bin", "#009688");
        Button binToDecButton = createStyledButton("Bin→Dec", "#009688");
        Button octToBinButton = createStyledButton("Oct→Bin", "#009688");
        Button binToOctButton = createStyledButton("Bin→Oct", "#009688");
        Button hexToBinButton = createStyledButton("Hex→Bin", "#009688");
        Button binToHexButton = createStyledButton("Bin→Hex", "#009688");
        Button binarySizeButton = createStyledButton("Binary Size", "#795548");
        Button binaryCalcButton = createStyledButton("Binary Calc", "#FF5722");

        // Number buttons
        Button zeroButton = createStyledButton("0", "#FFFFFF");
        Button oneButton = createStyledButton("1", "#FFFFFF");

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: #eeeeee;");
        
        // Center the grid
        grid.setAlignment(javafx.geometry.Pos.CENTER);

        // Add components
        grid.add(display, 0, 0, 4, 1);
        
        HBox numberBox = new HBox(10);
        numberBox.setAlignment(javafx.geometry.Pos.CENTER);
        numberBox.getChildren().addAll(zeroButton, oneButton, clearButton, equalsButton);
        grid.add(numberBox, 0, 1, 4, 1);

        HBox operationBox = new HBox(10);
        operationBox.setAlignment(javafx.geometry.Pos.CENTER);
        operationBox.getChildren().addAll(addButton, subtractButton, multiplyButton, divideButton);
        grid.add(operationBox, 0, 2, 4, 1);

        HBox conversionBox1 = new HBox(10);
        conversionBox1.setAlignment(javafx.geometry.Pos.CENTER);
        conversionBox1.getChildren().addAll(decToBinButton, binToDecButton, octToBinButton);
        grid.add(conversionBox1, 0, 3, 4, 1);

        HBox conversionBox2 = new HBox(10);
        conversionBox2.setAlignment(javafx.geometry.Pos.CENTER);
        conversionBox2.getChildren().addAll(binToOctButton, hexToBinButton, binToHexButton);
        grid.add(conversionBox2, 0, 4, 4, 1);

        HBox utilityBox = new HBox(10);
        utilityBox.setAlignment(javafx.geometry.Pos.CENTER);
        utilityBox.getChildren().addAll(binarySizeButton, binaryCalcButton);
        grid.add(utilityBox, 0, 5, 4, 1);

        // Make display expand to fill width
        display.setMaxWidth(Double.MAX_VALUE);
        GridPane.setFillWidth(display, true);

        // Event handlers
        zeroButton.setOnAction(e -> handleDigit("0"));
        oneButton.setOnAction(e -> handleDigit("1"));
        clearButton.setOnAction(e -> clearAll());
        equalsButton.setOnAction(e -> calculateResult());
        
        addButton.setOnAction(e -> setOperation("+"));
        subtractButton.setOnAction(e -> setOperation("-"));
        multiplyButton.setOnAction(e -> setOperation("*"));
        divideButton.setOnAction(e -> setOperation("/"));

        decToBinButton.setOnAction(e -> handleDecToBin());
        binToDecButton.setOnAction(e -> handleBinToDec());
        octToBinButton.setOnAction(e -> handleOctToBin());
        binToOctButton.setOnAction(e -> handleBinToOct());
        hexToBinButton.setOnAction(e -> handleHexToBin());
        binToHexButton.setOnAction(e -> handleBinToHex());
        binarySizeButton.setOnAction(e -> handleBinarySize());
        binaryCalcButton.setOnAction(e -> handleBinaryCalc());

        // Scene setup
        Scene scene = new Scene(grid);

        // Load CSS file with error handling
        try {
            String cssPath = getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("CSS file not found. Application will run without custom styles.");
        }

        primaryStage.setTitle("Advanced Binary Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }


private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-padding: 15 25; " +
                       "-fx-background-color: " + color + "; -fx-text-fill: " + 
                       (color.equals("#FFFFFF") ? "black" : "white") + "; " +
                       "-fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        button.setOnMouseEntered(e -> button.setOpacity(0.8));
        button.setOnMouseExited(e -> button.setOpacity(1));
        return button;
    }

    private void handleDecToBin() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Decimal to Binary");
        dialog.setHeaderText("Enter a decimal number:");
        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(number -> {
            try {
                int decimal = Integer.parseInt(number);
                display.setText(Integer.toBinaryString(decimal));
                newNumberExpected = true;
            } catch (NumberFormatException e) {
                display.setText("Error");
                clearAll();
            }
        });
    }

    private void handleBinToDec() {
        String binary = display.getText();
        if (binary.isEmpty()) return;
        
        try {
            int decimal = Integer.parseInt(binary, 2);
            display.setText(String.valueOf(decimal));
            newNumberExpected = true;
        } catch (NumberFormatException e) {
            display.setText("Error");
            clearAll();
        }
    }

    private void handleOctToBin() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Octal to Binary");
        dialog.setHeaderText("Enter an octal number:");
        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(number -> {
            try {
                int decimal = Integer.parseInt(number, 8);
                display.setText(Integer.toBinaryString(decimal));
                newNumberExpected = true;
            } catch (NumberFormatException e) {
                display.setText("Error");
                clearAll();
            }
        });
    }

    private void handleBinToOct() {
        String binary = display.getText();
        if (binary.isEmpty()) return;
        
        try {
            int decimal = Integer.parseInt(binary, 2);
            display.setText(Integer.toOctalString(decimal));
            newNumberExpected = true;
        } catch (NumberFormatException e) {
            display.setText("Error");
            clearAll();
        }
    }

    private void handleHexToBin() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Hexadecimal to Binary");
        dialog.setHeaderText("Enter a hexadecimal number:");
        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(number -> {
            try {
                int decimal = Integer.parseInt(number, 16);
                display.setText(Integer.toBinaryString(decimal));
                newNumberExpected = true;
            } catch (NumberFormatException e) {
                display.setText("Error");
                clearAll();
            }
        });
    }

    private void handleBinToHex() {
        String binary = display.getText();
        if (binary.isEmpty()) return;
        
        try {
            int decimal = Integer.parseInt(binary, 2);
            display.setText(Integer.toHexString(decimal).toUpperCase());
            newNumberExpected = true;
        } catch (NumberFormatException e) {
            display.setText("Error");
            clearAll();
        }
    }

    private void handleBinarySize() {
        String binary = display.getText();
        if (binary.isEmpty()) return;

        int bits = binary.length();
        double bytes = bits / 8.0;
        double kb = bytes / 1024;
        double mb = kb / 1024;
        double gb = mb / 1024;

        String sizeInfo = String.format(
            "Bits: %d\nBytes: %.2f\nKB: %.5f\nMB: %.8f\nGB: %.11f", 
            bits, bytes, kb, mb, gb
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Binary Size Information");
        alert.setHeaderText("Storage Requirements");
        alert.setContentText(sizeInfo);
        alert.showAndWait();
    }

    private void handleBinaryCalc() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Binary Calculator");
        info.setHeaderText("How to Use:");
        info.setContentText("1. Enter first binary number\n" +
                          "2. Choose operation (+, -, ×, ÷)\n" +
                          "3. Enter second binary number\n" +
                          "4. Press = for result");
        info.showAndWait();
    }

    private void handleDigit(String digit) {
        if (newNumberExpected) {
            display.clear();
            newNumberExpected = false;
        }
        display.appendText(digit);
    }

    private void setOperation(String operation) {
        String currentText = display.getText();
        if (!currentText.isEmpty()) {
            try {
                firstOperand = Integer.parseInt(currentText, 2);
                currentOperation = operation;
                display.clear();
            } catch (NumberFormatException e) {
                handleError();
            }
        }
    }

    private void calculateResult() {
        if (currentOperation.isEmpty()) return;
        
        String currentText = display.getText();
        if (currentText.isEmpty()) return;

        try {
            int secondOperand = Integer.parseInt(currentText, 2);
            int result = performOperation(secondOperand);
            
            if (result < 0) {
                handleError();
                return;
            }
            
            display.setText(Integer.toBinaryString(result));
            newNumberExpected = true;
            currentOperation = "";
        } catch (NumberFormatException | ArithmeticException e) {
            handleError();
        }
    }


private int performOperation(int secondOperand) {
        switch (currentOperation) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                if (secondOperand == 0) throw new ArithmeticException();
                return firstOperand / secondOperand;
            default:
                return -1;
        }
    }

    private void clearAll() {
        display.clear();
        firstOperand = 0;
        currentOperation = "";
        newNumberExpected = false;
    }

    private void handleError() {
        display.setText("Error");
        clearAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}