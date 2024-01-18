package viewmodel;

import view.MainView;
import model.*; 
import java.io.File;
import model.Drawing;
import model.DrawingDOM;
import model.Figure;

public class MainViewModel extends ViewModel {
   private IOFigureViewModel vmIOFigure = null;
   private CADViewModel vmCAD = null;
   private Drawing zeichnung = null;
   private Figure figure = null;
   private int idFigure = 0;

   public MainViewModel() {
      this.zeichnung = new Drawing();
      this.figure = (Figure)this.zeichnung.get(this.idFigure);
      this.vmIOFigure = new IOFigureViewModel(this.figure, this);
      this.vmCAD = new CADViewModel(this.zeichnung, this);
   }

   
   /******************************
    *          GET
    ******************************/
   
   public ViewModel getIOFigureVM() {
      return this.vmIOFigure;
   }

   public ViewModel getCADVM() {
      return this.vmCAD;
   }

   public Drawing getDrawing() {
      return this.zeichnung;
   }

   public int getFigureID() {
      return this.idFigure;
   }

   public Figure getFigure() {
      return this.figure;
   }

   public Figure getSettings() {
      return this.vmIOFigure.getSettings();
   }

   public Figure getFigure(int id) {
      return (Figure)this.zeichnung.get(id);
   }

   public int getID(Figure f) {
      return this.zeichnung.getID(f);
   }

   
   /******************************
    *          Update
    ******************************/
   public void updateFigure(int id) {
      this.vmIOFigure.setFigure(this.getFigure(id), id);
   }
   
   /******************************
    *          ADD
    ******************************/

   public int addFigure(Figure f) {
      this.figure = f;
      return this.zeichnung.add(this.figure);
   }

   /******************************
    *          Remove
    ******************************/ 
   public Figure removeFigure(Figure f) {
      return this.zeichnung.remove(getFigureID()) ? (Figure)this.zeichnung.get(0) : f;
   }
   
   
   

   public void updateDrawing() {
      ((MainView)this.view).update();
      System.out.println();
      System.out.println(this.zeichnung);
   }

   public void updateDrawing(Figure f, int id) {
      if (f == null) {
         f = (Figure)this.zeichnung.get(id);
      }

      this.figure = f;
      this.idFigure = id;
      this.zeichnung.set(this.figure, id);
      this.vmIOFigure.setFigure(this.figure, id);
      this.updateDrawing();
   }
  
   
   /*****************
    * TEST
    ****************/
   
   
   public Tupel<Color> getChosenColors(){
		   return new Tupel<>(getFigure().getColors().x, getFigure().getColors().y); 
	   
   }
   
  

   public void openDrawing(File file) {
      String path = file.getPath();
      if (path.substring(path.length() - 3).contentEquals("ser")) {
         this.zeichnung = DrawingDOM.readFromObjectStream(path);
      } else if (path.substring(path.length() - 3).contentEquals("xml")) {
         this.zeichnung = DrawingDOM.readXMLDecoder(path);
      } else {
         this.zeichnung = DrawingDOM.readXMLDOM(path);
      }

      this.vmCAD.setZeichnung(this.zeichnung);
      this.updateDrawing();
   }
   
   

   public void saveDrawing(File file) {
      String path = file.getPath();
      if (path.substring(path.length() - 3).contentEquals("ser")) {
         DrawingDOM.writeToObjectStream(this.zeichnung, path);
      } else if (path.substring(path.length() - 3).contentEquals("xml")) {
         DrawingDOM.writeXMLEncoder(this.zeichnung, path);
      } else {
         DrawingDOM.writeXMLDOM(this.zeichnung, path);
      }

   }
}
