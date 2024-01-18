package application;

import view.MainView;
import viewmodel.MainViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CADApp extends Application {

    @Override
   public void start(Stage primaryStage) {
      try {
         BorderPane root = new BorderPane();
         Scene scene = new Scene(root, 1300.0, 700.0);
          MainViewModel vmMain = new MainViewModel();
          MainView viewMain = new MainView(vmMain, this, primaryStage);
         root.setCenter(viewMain);
         primaryStage.setScene(scene);
         primaryStage.setTitle("CADApp V.: LE 12, A.: Kevin Schallmo");
         primaryStage.show();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static void start(String... args) {
      launch(args);
   }
   

}
