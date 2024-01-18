package application;

import view.MainView;
import viewmodel.MainViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CADApp extends Application {
   private MainView viewMain = null;
   private MainViewModel vmMain = null;

   @Override
   public void start(Stage primaryStage) {
      try {
         BorderPane root = new BorderPane();
         Scene scene = new Scene(root, 1300.0, 700.0);
         this.vmMain = new MainViewModel();
         this.viewMain = new MainView(this.vmMain, this, primaryStage);
         root.setCenter(this.viewMain);
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
