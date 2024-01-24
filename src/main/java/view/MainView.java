package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import viewmodel.MainViewModel;

import java.io.File;

public class MainView extends BorderPane implements IView {
    private Stage stage;
   private MainViewModel vm;
   private IOFigureView viewIOFigure;
   private CADView viewCAD;
   private MainView.FilePane filePane;

   public MainView(MainViewModel vm, Application parent, Stage stage) {
      try {
          this.stage = stage;
         this.vm = vm;
         vm.registerView(this);
         this.viewCAD = new CADView(vm.getCADVM(), this);
         this.viewIOFigure = new IOFigureView(vm.getIOFigureVM(), this, stage);
         this.setRight(this.viewIOFigure);
         BorderPane.setAlignment(this.viewIOFigure, Pos.CENTER_RIGHT);
         this.setCenter(this.viewCAD);
         this.filePane = new MainView.FilePane();
         this.setBottom(this.filePane);
         BorderPane.setAlignment(this.filePane, Pos.BOTTOM_CENTER);
         this.setPrefSize(1100.0, 600.0);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   
   /******************************
    *          Overrides
    ******************************/
   
   @Override
   public void update() {
      this.viewCAD.update();
      this.viewIOFigure.update();
   }

   private class FilePane extends HBox {
      private final FileChooser fileChooser = new FileChooser();
      private final EventHandler<ActionEvent> handler = me -> {
          if (me.getSource() == FilePane.this.btnLoad) {
              FilePane.this.btnLoadHandle(me);
          } else if (me.getSource() == FilePane.this.btnSave) {
              FilePane.this.btnSaveHandle(me);
          }

      };
      private final IOFigureView.ButtonAction btnLoad = new IOFigureView.ButtonAction("Load", this.handler);
      private final IOFigureView.ButtonAction btnSave = new IOFigureView.ButtonAction("Save", this.handler);

      public FilePane() {
         this.fileChooser
            .getExtensionFilters()
            .addAll(
               new FileChooser.ExtensionFilter("Serialize", "*.ser"),
               new FileChooser.ExtensionFilter("XMLEncoder Files", "*.xml"),
               new FileChooser.ExtensionFilter("XMLDOM Files", "*.xml2")
            );
       
         this.fileChooser.setInitialDirectory(new File("./"));
         this.fileChooser.setInitialFileName("zch.ser");
         this.setPrefSize(200.0, 5.0);
         this.setAlignment(Pos.CENTER);
         this.setSpacing(10.0);
         this.getChildren().addAll(this.btnLoad, new Label("        "), this.btnSave);
      }

    
      public void btnLoadHandle(Event e) {
         File selectedFile = MainView.this.filePane.fileChooser.showOpenDialog(MainView.this.stage);
         if (selectedFile != null) {
            MainView.this.vm.openDrawing(selectedFile);
         }

      }

      public void btnSaveHandle(Event e) {
         File selectedFile = MainView.this.filePane.fileChooser.showSaveDialog(MainView.this.stage);
         if (selectedFile != null) {
            MainView.this.vm.saveDrawing(selectedFile);
         }

      }
   }
}
