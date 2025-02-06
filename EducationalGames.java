import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.*;
import java.io.File;

public class EducationalGames extends Application {
    private Stage primaryStage;
    private int score = 0;
    private int currentQuestionIndex = 0;
    private Map<String, String> flagsData;
    private List<QuizQuestion> quizQuestions;
    private ImageView flagImageView;
    private Label scoreLabel;
    private Label questionLabel;
    private TextField answerField;
    private VBox gameContainer;
    private List<String> remainingCountries;
    private Random random = new Random();
    private int numberToGuess;
    private int guessesRemaining;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Educational Games");

        initializeFlagsData();
        initializeQuizQuestions();

        showMainMenu();
        primaryStage.show();
    }

    private void initializeFlagsData() {
        flagsData = new HashMap<>();
        try {
            // Using African country flags
            flagsData.put("Egypt", "https://flagcdn.com/w640/eg.png");
            flagsData.put("South Africa", "https://flagcdn.com/w640/za.png");
            flagsData.put("Nigeria", "https://flagcdn.com/w640/ng.png");
            flagsData.put("Kenya", "https://flagcdn.com/w640/ke.png");
            flagsData.put("Ethiopia", "https://flagcdn.com/w640/et.png");
            flagsData.put("Ghana", "https://flagcdn.com/w640/gh.png");
            flagsData.put("Morocco", "https://flagcdn.com/w640/ma.png");
            flagsData.put("Tanzania", "https://flagcdn.com/w640/tz.png");
            flagsData.put("Uganda", "https://flagcdn.com/w640/ug.png");
            flagsData.put("Algeria", "https://flagcdn.com/w640/dz.png");
            flagsData.put("Sudan", "https://flagcdn.com/w640/sd.png");
            flagsData.put("Rwanda", "https://flagcdn.com/w640/rw.png");
            flagsData.put("Senegal", "https://flagcdn.com/w640/sn.png");
            flagsData.put("Zimbabwe", "https://flagcdn.com/w640/zw.png");
            flagsData.put("Somalia", "https://flagcdn.com/w640/so.png");
            
            // Test if images can be loaded
            for (String path : flagsData.values()) {
                try {
                    new Image(path);
                } catch (Exception e) {
                    System.err.println("Could not load image: " + path);
                    throw e;
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading flag images: " + e.getMessage());
            showAlert("Error", "Could not load flag images. Please check your internet connection.", Alert.AlertType.ERROR);
        }
    }

    private void initializeQuizQuestions() {
        quizQuestions = new ArrayList<>();
        
        // Ethiopian Indigenous Animals Questions
        quizQuestions.add(new QuizQuestion(
            "Which rare Ethiopian wolf is found in the Bale Mountains?",
            Arrays.asList("Simien Fox", "Red Fox", "Grey Wolf", "Arctic Wolf"),
            "Simien Fox"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "What is the national animal of Ethiopia?",
            Arrays.asList("Lion", "Gelada Baboon", "Ethiopian Wolf", "Mountain Nyala"),
            "Lion"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which primate is endemic to Ethiopia and is often called the 'Bleeding Heart Monkey'?",
            Arrays.asList("Gelada Baboon", "Colobus Monkey", "Vervet Monkey", "Mandrill"),
            "Gelada Baboon"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which antelope species is only found in Ethiopia?",
            Arrays.asList("Mountain Nyala", "Kudu", "Impala", "Gazelle"),
            "Mountain Nyala"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "What is the largest bird found in Ethiopia?",
            Arrays.asList("Ostrich", "Ethiopian Bush-crow", "Stork", "Eagle"),
            "Ostrich"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which endangered Ethiopian animal is known as 'Walia'?",
            Arrays.asList("Walia Ibex", "Mountain Goat", "Highland Sheep", "Alpine Gazelle"),
            "Walia Ibex"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which monkey species is known for its distinctive white and black coloring in Ethiopia?",
            Arrays.asList("Colobus Monkey", "Vervet Monkey", "Baboon", "Mandrill"),
            "Colobus Monkey"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "What is the rarest carnivore in Ethiopia?",
            Arrays.asList("Ethiopian Wolf", "Lion", "Leopard", "Hyena"),
            "Ethiopian Wolf"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which large mammal is found in Ethiopia's Awash National Park?",
            Arrays.asList("Beisa Oryx", "Giraffe", "Zebra", "Rhinoceros"),
            "Beisa Oryx"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "What unique primate is found in Ethiopia's highlands and lives in large groups?",
            Arrays.asList("Gelada Baboon", "Hamadryas Baboon", "Vervet Monkey", "Colobus Monkey"),
            "Gelada Baboon"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "Which bird species is endemic to Ethiopia and is considered sacred?",
            Arrays.asList("Ethiopian Bush-crow", "African Eagle", "Vulture", "Hawk"),
            "Ethiopian Bush-crow"
        ));
        
        quizQuestions.add(new QuizQuestion(
            "What is the largest predator found in Ethiopia's Bale Mountains?",
            Arrays.asList("Ethiopian Wolf", "Leopard", "Spotted Hyena", "Lion"),
            "Ethiopian Wolf"
        ));
    }

    private VBox createMainMenu() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-background-color: #f0f0f0; -fx-font-family: 'Arial';");
        menu.setPadding(new Insets(20));

        Label titleLabel = new Label("Educational Games");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setStyle("-fx-text-fill: #333333;");

        Button flagGameBtn = createStyledButton("Flag Guessing Game");
        Button quizGameBtn = createStyledButton("General Knowledge Quiz");
        Button numberGameBtn = createStyledButton("Number Guessing Game");

        flagGameBtn.setOnAction(e -> startFlagGame());
        quizGameBtn.setOnAction(e -> startQuizGame());
        numberGameBtn.setOnAction(e -> startNumberGame());

        menu.getChildren().addAll(titleLabel, flagGameBtn, quizGameBtn, numberGameBtn);
        return menu;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        button.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 15 30;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        button.setMinWidth(250);
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #45a049;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 15 30;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: #4CAF50;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 15 30;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;"
            )
        );
        
        return button;
    }

    private void startFlagGame() {
        score = 0;
        currentQuestionIndex = 0;
        
        remainingCountries = new ArrayList<>(flagsData.keySet());
        Collections.shuffle(remainingCountries);
        
        gameContainer = new VBox(20);
        gameContainer.setAlignment(Pos.CENTER);
        gameContainer.setPadding(new Insets(20));
        gameContainer.setStyle("-fx-background-color: #f0f0f0;");

        flagImageView = new ImageView();
        flagImageView.setFitWidth(300);
        flagImageView.setFitHeight(200);
        flagImageView.setPreserveRatio(true);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        answerField = new TextField();
        answerField.setMaxWidth(300);
        answerField.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 1;"
        );

        answerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                checkFlagAnswer();
            }
        });

        Button submitButton = createStyledButton("Submit Answer");
        submitButton.setOnAction(e -> checkFlagAnswer());

        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMainMenu());

        gameContainer.getChildren().addAll(
            scoreLabel, flagImageView, 
            new Label("Enter country name:"), 
            answerField, submitButton, backButton
        );

        Scene gameScene = new Scene(gameContainer, 800, 600);
        primaryStage.setScene(gameScene);

        showNextFlag();
    }

    private void startQuizGame() {
        score = 0;
        currentQuestionIndex = 0;
        
        gameContainer = new VBox(20);
        gameContainer.setAlignment(Pos.CENTER);
        gameContainer.setPadding(new Insets(20));
        gameContainer.setStyle("-fx-background-color: #f0f0f0;");

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        questionLabel = new Label();
        questionLabel.setFont(Font.font("Arial", 18));
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(600);

        VBox answersBox = new VBox(10);
        answersBox.setAlignment(Pos.CENTER);

        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMainMenu());

        gameContainer.getChildren().addAll(
            scoreLabel, questionLabel, answersBox, backButton
        );

        Scene gameScene = new Scene(gameContainer, 800, 600);
        primaryStage.setScene(gameScene);

        showNextQuestion();
    }

    private void startNumberGame() {
        numberToGuess = random.nextInt(100) + 1; // Random number between 1 and 100
        guessesRemaining = 3;
        
        gameContainer = new VBox(20);
        gameContainer.setAlignment(Pos.CENTER);
        gameContainer.setPadding(new Insets(20));
        gameContainer.setStyle("-fx-background-color: #f0f0f0;");

        Label titleLabel = new Label("Number Guessing Game");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Label instructionLabel = new Label("Guess a number between 1 and 100");
        instructionLabel.setFont(Font.font("Arial", 18));

        Label attemptsLabel = new Label("Attempts remaining: " + guessesRemaining);
        attemptsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TextField guessField = new TextField();
        guessField.setMaxWidth(200);
        guessField.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 1;"
        );

        Button submitButton = createStyledButton("Submit Guess");
        Button backButton = createStyledButton("Back to Menu");

        submitButton.setOnAction(e -> {
            try {
                int guess = Integer.parseInt(guessField.getText().trim());
                checkNumberGuess(guess, guessField, attemptsLabel);
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid number", Alert.AlertType.ERROR);
            }
        });

        backButton.setOnAction(e -> showMainMenu());

        // Add Enter key support
        guessField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    int guess = Integer.parseInt(guessField.getText().trim());
                    checkNumberGuess(guess, guessField, attemptsLabel);
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid number", Alert.AlertType.ERROR);
                }
            }
        });

        gameContainer.getChildren().addAll(
            titleLabel,
            instructionLabel,
            attemptsLabel,
            guessField,
            submitButton,
            backButton
        );

        Scene gameScene = new Scene(gameContainer, 800, 600);
        primaryStage.setScene(gameScene);
    }

    private void checkNumberGuess(int guess, TextField guessField, Label attemptsLabel) {
        if (guess < 1 || guess > 100) {
            showAlert("Invalid Input", "Please enter a number between 1 and 100", Alert.AlertType.WARNING);
            return;
        }

        guessesRemaining--;
        attemptsLabel.setText("Attempts remaining: " + guessesRemaining);

        if (guess == numberToGuess) {
            showAlert("Congratulations!", "You guessed the number correctly!", Alert.AlertType.INFORMATION);
            showMainMenu();
        } else {
            if (guessesRemaining > 0) {
                String hint = guess < numberToGuess ? "Try a higher number" : "Try a lower number";
                showAlert("Wrong Guess", hint, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Game Over", "The number was " + numberToGuess, Alert.AlertType.INFORMATION);
                showMainMenu();
            }
        }
        
        guessField.clear();
    }

    private void showMainMenu() {
        VBox mainMenu = createMainMenu();
        Scene scene = new Scene(mainMenu, 800, 600);
        primaryStage.setScene(scene);
    }

    private void checkFlagAnswer() {
        String userAnswer = answerField.getText().trim().toLowerCase();
        String correctAnswer = remainingCountries.get(currentQuestionIndex).toLowerCase();

        if (userAnswer.equals(correctAnswer)) {
            score++;
            showAlert("Correct!", "Well done! That's the right answer.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Wrong!", "The correct answer was: " + correctAnswer, Alert.AlertType.ERROR);
        }

        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;
        
        if (currentQuestionIndex < remainingCountries.size()) {
            showNextFlag();
        } else {
            showFinalScore();
        }
        
        answerField.clear();
    }

    private void showNextFlag() {
        if (currentQuestionIndex < remainingCountries.size()) {
            String countryName = remainingCountries.get(currentQuestionIndex);
            String flagUrl = flagsData.get(countryName);
            try {
                Image flagImage = new Image(flagUrl);
                flagImageView.setImage(flagImage);
            } catch (Exception e) {
                System.err.println("Error loading flag image: " + e.getMessage());
                flagImageView.setImage(null);
                showAlert("Error", "Could not load flag image. Please try again.", Alert.AlertType.ERROR);
                currentQuestionIndex++;
                showNextFlag();
            }
        }
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestion());

            VBox answersBox = new VBox(10);
            answersBox.setAlignment(Pos.CENTER);

            for (String answer : question.getAnswers()) {
                Button answerButton = createStyledButton(answer);
                answerButton.setOnAction(e -> checkQuizAnswer(answer, question.getCorrectAnswer()));
                answersBox.getChildren().add(answerButton);
            }

            gameContainer.getChildren().set(2, answersBox);
        } else {
            showFinalScore();
        }
    }

    private void checkQuizAnswer(String userAnswer, String correctAnswer) {
        if (userAnswer.equals(correctAnswer)) {
            score++;
            showAlert("Correct!", "Well done! That's the right answer.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Wrong!", "The correct answer was: " + correctAnswer, Alert.AlertType.ERROR);
        }

        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void showFinalScore() {
        String gameType = flagImageView != null ? "Flag Game" : "Quiz Game";
        String message = String.format("Game Over!\nYour final score in %s is: %d out of %d", 
            gameType, score, flagsData.size());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        showMainMenu();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class QuizQuestion {
        private final String question;
        private final List<String> answers;
        private final String correctAnswer;

        public QuizQuestion(String question, List<String> answers, String correctAnswer) {
            this.question = question;
            this.answers = answers;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() { return question; }
        public List<String> getAnswers() { return answers; }
        public String getCorrectAnswer() { return correctAnswer; }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 