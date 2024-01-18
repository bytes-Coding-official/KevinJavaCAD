package viewmodel;

import model.Color;
import view.CADView;

import javafx.scene.input.MouseEvent;
import model.Drawing;
import model.Figure;
import model.IFigure;




public class CADViewModel extends ViewModel {
   private MainViewModel parent = null;
   private Drawing zeichnung = null;
   private double width = 1100.0;
   private double height = 700.0;
   private CADViewModel.Edges edge = null;

  
   public CADViewModel(Drawing zeichnung, ViewModel parent) {
      this.parent = (MainViewModel)parent;
      this.zeichnung = zeichnung;
   }

   public Drawing getZeichnung() {
      return this.zeichnung;
   }

   public void setZeichnung(Drawing zeichnung) {
      this.zeichnung = zeichnung;
   }

   public void updateDrawing() {
      ((CADView)this.view).update();
   }

   public void mousePressed(MouseEvent me) {
      this.edge = new CADViewModel.Edges();
      this.edge.x0 = me.getX();
      this.edge.y0 = me.getY();
      System.out.println("Mouse pressed"); 
   }

   public void mouseReleased(MouseEvent me) {
      this.edge.x1 = me.getX();
      this.edge.y1 = me.getY();
      System.out.println("Mouse released"); 
     
      if (this.regulizeEdges()) {
         Figure figure = this.parent.getSettings();
         String pname = figure.getClass().getPackageName();
         String formF = figure.getForm();
         String form = formF;
         IFigure f = null;

         try {
            f = (IFigure)Class.forName(pname + "." + form).getConstructor().newInstance();
         } catch (Exception var9) {
            var9.printStackTrace();
         }
         /*Figure f = this.figure.clone();
         this.figure = this.updateFigure(f, true);
         this.idFigure = this.parent.addFigure(f) - 1;
         this.parent.updateDrawing(this.figure, this.idFigure);*/
          assert f != null;
          Figure fig = ((Figure) f).clone();
         try {
            fig.setColors(parent.getChosenColors().x, this.parent.getChosenColors().y);
         } catch (Exception e) {
            fig.setColors(Color.RED, Color.BLUE );
         }
         fig.setPosition(this.edge.x0, this.edge.y0);
         fig.setSize(this.edge.x1 - this.edge.x0, this.edge.y1 - this.edge.y0);
         fig.setForm(formF);
         int id = this.zeichnung.add(fig) - 1;
         this.parent.updateDrawing(fig, id);
      }   

   }
   
   

   private boolean regulizeEdges() {
      if (this.edge.x0 < 0.0) {
         this.edge.x0 = 0.0;
      }

      if (this.edge.y0 < 0.0) {
         this.edge.y0 = 0.0;
      }

      if (this.edge.x1 < 0.0) {
         this.edge.x1 = 0.0;
      }

      if (this.edge.y1 < 0.0) {
         this.edge.y1 = 0.0;
      }

      if (this.edge.x0 > this.width) {
         this.edge.x0 = this.width;
      }

      if (this.edge.y0 > this.height) {
         this.edge.y0 = this.height;
      }

      if (this.edge.x1 > this.width) {
         this.edge.x1 = this.width;
      }

      if (this.edge.y1 > this.height) {
         this.edge.y1 = this.height;
      }

      if (this.edge.x1 < this.edge.x0) {
         double d = this.edge.x0;
         this.edge.x0 = this.edge.x1;
         this.edge.x1 = d;
      }

      if (this.edge.y1 < this.edge.y0) {
         double d = this.edge.y0;
         this.edge.y0 = this.edge.y1;
         this.edge.y1 = d;
      }

      System.out.println("Mouse released (" + this.edge.x0 + "," + this.edge.y0 + "),(" + this.edge.x1 + "," + this.edge.y1 + ")");
      return this.edge.x1 - this.edge.x0 > 0.0 && this.edge.y1 - this.edge.y0 > 5.0 || this.edge.x1 - this.edge.x0 > 5.0 && this.edge.y1 - this.edge.y0 > 0.0;
   }

   public void mouseClicked(MouseEvent me) {
      System.out.println("Mouse clicked " + me.getX() + " " + me.getY());
      int id = this.zeichnung.isPointInside(me.getX(), me.getY());
      if (id != -1) {
         this.parent.updateFigure(id);
      }

   }
  

   private class Edges {
      public double x0;
      public double x1;
      public double y0;
      public double y1;

      public Edges() {
      }
   }
 
}