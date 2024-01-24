package view;

import viewmodel.IOFigureViewModel;
import viewmodel.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Color;
import model.Figure;


public class IOFigureView extends BorderPane implements IView {
   
// Privat

    private IOFigureViewModel vm;
   private final ObservableList<String> forms = FXCollections.observableArrayList("Ellipse", "Rectangle", "Polygon", "Triangle", "Line", "Arc"); 
   private final IOFigureView.LabeledText pnIndex = new IOFigureView.LabeledText("Index", "1");
   private final ComboBox<String> pnForm = new ComboBox<>(this.forms);
   private final IOFigureView.LabeledText pnX = new IOFigureView.LabeledText("X", "0");
   private final IOFigureView.LabeledText pnY = new IOFigureView.LabeledText("Y", "0");
   private final IOFigureView.LabeledText pnA = new IOFigureView.LabeledText("Width", "100");
   private final IOFigureView.LabeledText pnB = new IOFigureView.LabeledText("Heigth", "50");
   private final IOFigureView.LabeledText pnFC = new IOFigureView.LabeledText("FillC", Color.YELLOW);
   private final IOFigureView.LabeledText pnLC = new IOFigureView.LabeledText("LineC", Color.BLUE);
   private final IOFigureView.LabeledText pnUmfang = new IOFigureView.LabeledText("Umfang", "300", false);
   private final IOFigureView.LabeledText pnArea = new IOFigureView.LabeledText("Flaeche", "5000", false);
   private final EventHandler<ActionEvent> handler = me -> {
       if (me.getSource() == IOFigureView.this.btnOK) {
           IOFigureView.this.vm.btnOKHandle();
       } else if (me.getSource() == IOFigureView.this.btnAdd) {
           IOFigureView.this.vm.btnAddHandle();
       } else if (me.getSource() == IOFigureView.this.btnRemove) {
           IOFigureView.this.vm.btnRemoveHandle();
       }

   };
   
   
// Privat
   
   private final IOFigureView.ButtonAction btnOK = new IOFigureView.ButtonAction("Change", this.handler);
   private final IOFigureView.ButtonAction btnAdd = new IOFigureView.ButtonAction("Add", this.handler);
   private final IOFigureView.ButtonAction btnRemove = new IOFigureView.ButtonAction("Remove", this.handler);

   public IOFigureView(ViewModel vm, IView parent, Stage stage) {
       this.vm = (IOFigureViewModel)vm;
      vm.registerView(this);
      this.pnForm.setPrefWidth(150.0);
      this.pnForm.setPrefHeight(50.0);
      this.setPrefSize(180.0, 350.0);
      VBox right = new VBox();
      right.setAlignment(Pos.BASELINE_CENTER);
      right.setAlignment(Pos.BASELINE_RIGHT);
      right.setSpacing(8.0);
      right.getChildren()
         .addAll(
            this.pnIndex,
            this.pnForm,
            new Label("        "),
            new Label("Position     "),
            this.pnX,
            this.pnY,
            new Label("Size         "),
            this.pnA,
            this.pnB,
            new Label("Colors        "),
            this.pnFC,
            this.pnLC,
            this.btnOK,
            this.btnAdd,
            this.btnRemove,
            new Label("        "),
            new Label("Features     "),
            this.pnUmfang,
            this.pnArea
         );
      right.setPadding(new Insets(25.0, 25.0, 10.0, 10.0));
      this.setCenter(right);
      this.update();
   }

  
   
   /******************************
    *          Overrides
    ******************************/
   
   @Override
   public void update() {
      this.updateIOFigure(this.vm.getFigure(), this.vm.getFigureID());
   }

   public void updateIOFigure(Figure figure, int id) {
      if (figure != null) {
         this.pnIndex.setTextField(id);
         this.pnForm.getSelectionModel().select(figure.getForm());
         this.pnX.setTextField(figure.getPosition().x);
         this.pnY.setTextField(figure.getPosition().y);
         this.pnA.setTextField(figure.getSize().x);
         this.pnB.setTextField(figure.getSize().y);
  //       this.pnFC.setColor(ColorDOM.getFXfromRGB(figure.getColors().x));
  //       this.pnLC.setColor(ColorDOM.getFXfromRGB(figure.getColors().y));
         this.pnUmfang.setTextField(figure.circumference());
         this.pnArea.setTextField(figure.area());
      }

   }

   
   /******************************
    *          GET
    ******************************/
   
   public double getIndex() {
      return this.pnIndex.getTextField();
   }

   public String getForm() {
	      return this.pnForm.getSelectionModel().getSelectedItem();
	   }
   
   public Figure getSettings(Figure fig) {
      fig.setForm(this.pnForm.getSelectionModel().getSelectedItem());
      fig.setPosition(this.pnX.getTextField(), this.pnY.getTextField());
      fig.setSize(this.pnA.getTextField(), this.pnB.getTextField());
    //  fig.setColors(ColorDOM.setRGBfromFX(this.pnFC.getColor()), ColorDOM.setRGBfromFX(this.pnLC.getColor()));
      FXAdapter.setRGBfromFX(this.pnFC.getColor()); 
      FXAdapter.setRGBfromFX(this.pnLC.getColor()); 
      return fig;
   }

  
   
   
   public static class ButtonAction extends Button {
      public ButtonAction(String btnText, EventHandler<ActionEvent> handler) {
         this.setText(btnText);
         this.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
         this.setPrefWidth(100.0);
         this.addEventHandler(ActionEvent.ACTION, handler);
      }
   }
   
   

   private static class LabeledText extends BorderPane {
      private Label lbl = null;
      private TextField txt = null;
      private final ColorPicker cp = new ColorPicker();

      public LabeledText(String lblText, Color rcColor) {
         this.lbl = this.createLabel(lblText);
      //   this.cp.setValue(ColorDOM.getFXfromRGB(rcColor));
         this.setLeft(this.lbl);
         this.setRight(this.cp);
      }

      public LabeledText(String lblText, String txtText, boolean enable) {
         this.lbl = this.createLabel(lblText);
         this.txt = this.createTextField(txtText, enable);
         this.setLeft(this.lbl);
         this.setRight(this.txt);
      }

      public LabeledText(String lblText, String txtText) {
         this(lblText, txtText, true);
      }

     
      /******************************
       *         SET TEXTFIELD
       ******************************/
      
      public void setTextField(double value) {
         this.txt.setText("" + (int)value);
      }

      public void setTextField(String str) {
         this.txt.setText(str);
      }

      public void setColor(javafx.scene.paint.Color rcColor) {
         this.cp.setValue(rcColor);
      }

      
      /******************************
       *         GET TEXTFIELD
       ******************************/
      
      public double getTextField() {
         return Double.parseDouble(this.txt.getText());
      }

      public String getForm() {
         return this.txt.getText();
      }

      public javafx.scene.paint.Color getColor() {
         return this.cp.getValue();
      }

     
      
      
      /******************************
       *    CREATE LABE/TEXTFIELD
       ******************************/
      
      
      private Label createLabel(String lblText) {
         Label lbl = new Label("  " + lblText + ":   ");
         lbl.setAlignment(Pos.BASELINE_CENTER);
         lbl.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
         return lbl;
      }

      private TextField createTextField(String txtText, Boolean ro) {
         TextField txt = new TextField("  " + txtText + "   ");
         txt.setAlignment(Pos.BASELINE_CENTER);
         txt.setPrefWidth(80.0);
         txt.setDisable(!ro);
         return txt;
      }
   }
}
