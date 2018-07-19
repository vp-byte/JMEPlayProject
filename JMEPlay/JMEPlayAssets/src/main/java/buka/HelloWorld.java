package buka;
import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.getContentTypes().stream().forEach(System.out::println);
        clipboard.getContentTypes().stream().forEach(f-> System.out.println(f + " -> " +clipboard.getContent(f)));
        System.out.println(clipboard.getContent(JMEPlayClipboardFormat.JAVA_FILES()));

    }
}