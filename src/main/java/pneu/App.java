package pneu;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pneu.main.MainView;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Map<Object, Object> customProperties = new HashMap<>();
        Injector.setConfigurationSource(customProperties::get);

        MainView appView = new MainView();
        Scene scene = new Scene(appView.getView());
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
