package view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.IFigure;
import viewmodel.CADViewModel;
import viewmodel.ViewModel;

public class CADView extends Group implements IView {
    private final CADViewModel vm;
   private final Pane pane = new Pane();

    public CADView(ViewModel vm, IView parent) {
        this.vm = (CADViewModel)vm;
      vm.registerView(this);
      this.pane.setStyle("-fx-background-color: black;");
        double height = 500.0;
        double width = 900.0;
        this.pane.setPrefSize(width, height);
        EventHandler<MouseEvent> handler = me -> {
            if (me.getEventType() == MouseEvent.MOUSE_PRESSED) {
                CADView.this.vm.mousePressed(me);
            } else if (me.getEventType() == MouseEvent.MOUSE_RELEASED) {
                CADView.this.vm.mouseReleased(me);
            } else if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
                CADView.this.vm.mouseClicked(me);
            }

        };
        this.pane.setOnMousePressed(handler);
      this.pane.setOnMouseReleased(handler);
      this.pane.setOnMouseClicked(handler);
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
