package view;

import javafx.scene.shape.Shape;
import model.*;

/**
 * Klasse FXAdapter
 * Adapterklasse zur Konvertierung zwischen der Color und den Figure-Klassen
 * und den analogen Klassen und Methoden der FX-Klassen Shape und Color 
 * @author H. Loose
 * @version 0.9 23.08.2022
 * @version 0.91 12.01.2023
 */
public class FXAdapter {

////////////FIGURE /////////////////////////////////////////////////////////////////////
	/**
	 * Konvertierung von Figure nach Shape
	 * 
	 * @return Instanz von Shape
	 */
	public static Shape getShape(IFigure iF) {
		Shape s = null;
		Figure f = (Figure) iF;
		String form = f.getForm();
        s = switch (form) {
            case "Arc" -> getShape((Arc) f);
            case "Triangle" -> getShape((Polygon) f, 3);
            case "Polygon" -> getShape((Polygon) f, 6);
            case "Line" -> getShape((Line) f);
            case "Ellipse" -> getShape((Ellipse) f);
            case "Rectangle" -> getShape((Rectangle) f);
            default -> s;
        };
		return s;
	}

	/**
	 * Setzen der Farben in einer Shape
	 *
     */
	public static void setShapeColors(Figure f, Shape s) {
		Tupel<Color> tc = f.getColors();
		s.setFill(getFXfromRGB(tc.x));
		s.setStroke(getFXfromRGB(tc.y));
		s.setStrokeWidth(3.0);
	}

	////////////////ARC /////////////////////////////////////////////////////////////////////////////
	/**
	* Konvertierung von Figure nach Shape
	* @return Instanz von Shape
	*/
	//@Override
	public static Shape getShape(model.Arc f) {
	double a = f.getSize().x/2, b = f.getSize().y/2;
	Shape s = new javafx.scene.shape.Arc(f.getPosition().x + a,f.getPosition().y + b,a,b,
		0.0,Math.sqrt(f.getSize().x*f.getSize().x + f.getSize().y*f.getSize().y));
	setShapeColors(f,s);
	return s;
	}
	////////////////Ellipse /////////////////////////////////////////////////////////////////////////////
	/**
	 * Konvertierung von Figure nach Shape
	 * @return Instanz von Shape
	 */
	//@Override
	public static Shape getShape(model.Ellipse f) {
		double a = f.getSize().x/2, b = f.getSize().y/2;
		Shape s = new javafx.scene.shape.Ellipse(f.getPosition().x + a,f.getPosition().y + b,a,b);
		setShapeColors(f,s);
		return s;
	}
	
	////////////////POLYGON /////////////////////////////////////////////////////////////////////////////
	/**
	 * Konvertierung von Figure nach Shape
	 * @return Instanz von Shape
	 */
	//@Override
	public static Shape getShape(model.Polygon f, int nEdges) {
		Shape s;
		double[] points = new double [2*nEdges];
		if (nEdges == 3) {
			points[0] = f.getPosition().x;
			points[1] = f.getPosition().y;
			points[2] = f.getPosition().x + f.getSize().x;
			points[3] = f.getPosition().y;
			points[4] = f.getPosition().x + f.getSize().x/2.0;
			points[5] = f.getPosition().y + f.getSize().y;
			s = new javafx.scene.shape.Polygon(points);
		}
		else if (nEdges == 6) {
			points[0] = f.getPosition().x + 0.3 * f.getSize().x;
			points[1] = f.getPosition().y + 0.0 * f.getSize().y;
			points[2] = f.getPosition().x + 0.7 * f.getSize().x;
			points[3] = f.getPosition().y + 0.0 * f.getSize().y;
			points[4] = f.getPosition().x + 1.0 * f.getSize().x;
			points[5] = f.getPosition().y + 0.5 * f.getSize().y;
			points[6] = f.getPosition().x + 0.7 * f.getSize().x;
			points[7] = f.getPosition().y + 1.0 * f.getSize().y;
			points[8] = f.getPosition().x + 0.3 * f.getSize().x;
			points[9] = f.getPosition().y + 1.0 * f.getSize().y;
			points[10] = f.getPosition().x + 0.0 * f.getSize().x;
			points[11] = f.getPosition().y + 0.5 * f.getSize().y;
			s = new javafx.scene.shape.Polygon(points);
		}
		else {
			s = new javafx.scene.shape.Polygon(f.getPosition().x,f.getPosition().y,f.getSize().x,f.getSize().y);
		}
		setShapeColors(f,s);
		return s;
	}
	
	////////////////LINE /////////////////////////////////////////////////////////////////////////////
	/**
	 * Konvertierung von Figure nach Shape
	 * @return Instanz von Shape
	 */
	//@Override
	public static Shape getShape(model.Line f) {
		Shape s = new javafx.scene.shape.Line(f.getPosition().x,f.getPosition().y,
				f.getPosition().x + f.getSize().x,f.getPosition().y + f.getSize().y);
		setShapeColors(f,s);
		return s;
	}
	
	//////////////// Rectangle /////////////////////////////////////////////////////////////////////////////
	/**
	 * Konvertierung von Figure nach Shape
	 * @return Instanz von Shape
	 */
	//@Override
	public static Shape getShape(model.Rectangle r) {
		Shape s = new javafx.scene.shape.Rectangle(r.getPosition().x,r.getPosition().y,r.getSize().x,r.getSize().y);
		setShapeColors(r,s);
		return s;
	}
////////////////////////////////////////////////////////////////////////////////////////////////

//////////// COLORDOM /////////////////////////////////////////////////////////////////////
//	public ColorDOM(java.awt.Color c) {
//		setRGBfromAWT(c);
//	}
//	
//	public ColorDOM(javafx.scene.paint.Color c) {
//		setRGBfromFX(c);
//	}
	
	/**
	 * Klassenmethode Konvertierung einer AWT-Color in Color
	 * 
	 * @param color - AWT
	 * @return color - diese Klasse
	 */
	public static Color setRGBfromAWT(java.awt.Color color) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	/**
	 * Klassenmethode Konvertierung einer FX-Color in Color
	 *
	 * @param color - FX
	 */
	public static void setRGBfromFX(javafx.scene.paint.Color color) {
		int r = (int) (255.0 * color.getRed() + 0.5);
		int g = (int) (255.0 *  color.getGreen() + 0.5);
		int b = (int) (255.0 *  color.getBlue() + 0.5);
		int a = (int) (255.0 *  color.getOpacity() + 0.5);
		new Color(r, g, b, a);
	}
	
	/**
	 * Klassenmethode Konvertierung einer Color in AWT-Color
	 * 
	 * @param color - diese Klasse
	 * @return color - AWT
	 */
	public static java.awt.Color getAWTfromRGB(Color color) {
		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Klassenmethode Konvertierung einer Color in FX-Color
	 * 
	 * @param color - diese Klasse
	 * @return color - FX
	 */
	public static javafx.scene.paint.Color getFXfromRGB(Color color) {
		return new javafx.scene.paint.Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
	}

	/**
	 * Klassenmethode Konvertierung einer FX-Color in AWT-Color
	 * 
	 * @param color - FX
	 * @return color - AWT
	 */
	public static java.awt.Color getRGBFX(javafx.scene.paint.Color color) {
		return new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
	}

	/**
	 * Klassenmethode Konvertierung einer AWT-Color in FX-Color
	 * 
	 * @param color - AWT
	 * @return color - FX
	 */
	public static javafx.scene.paint.Color setRGBFX(java.awt.Color color) {
		return new javafx.scene.paint.Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0,
				color.getAlpha() / 255.0);
	}

}
