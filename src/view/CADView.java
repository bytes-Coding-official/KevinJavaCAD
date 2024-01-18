package view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.IFigure;
import viewmodel.CADViewModel;
import viewmodel.ViewModel;
/*import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.IFigure;
import model.Figure;
import view.FXAdapter;
*/
public class CADView extends Group implements IView {
   private IView parent = null;
   private CADViewModel vm = null;
   private Pane pane = new Pane();
   private double width = 900.0;
   private double height = 500.0;
   private EventHandler<MouseEvent> handler = me -> {
      if (me.getEventType() == MouseEvent.MOUSE_PRESSED) {
         CADView.this.vm.mousePressed(me);
      } else if (me.getEventType() == MouseEvent.MOUSE_RELEASED) {
         CADView.this.vm.mouseReleased(me);
      } else if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
         CADView.this.vm.mouseClicked(me);
      }

   };

   public CADView(ViewModel vm, IView parent) {
      this.parent = parent;
      this.vm = (CADViewModel)vm;
      vm.registerView(this);
      this.pane.setStyle("-fx-background-color: black;");
      this.pane.setPrefSize(this.width, this.height);
      this.pane.setOnMousePressed(this.handler);
      this.pane.setOnMouseReleased(this.handler);
      this.pane.setOnMouseClicked(this.handler);
      this.update();
      this.getChildren().add(this.pane);
   }

   @Override
   public void update() {
      this.updateDrawing(this.pane);
   }
   
   

   private void updateDrawing(Pane pane) {
      pane.getChildren().clear();

      for(IFigure f : this.vm.getZeichnung().getShapes()) {
        
    	  Shape shape = FXAdapter.getShape(f);
    	  pane.getChildren().add(shape); 
      }
     

   }
}
