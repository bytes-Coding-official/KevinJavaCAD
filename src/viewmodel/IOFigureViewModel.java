package viewmodel;

import view.IOFigureView;


import javafx.event.Event;
import javafx.stage.Stage;
import model.Color;
import model.Figure;
import model.*;
import model.Rectangle;

public class IOFigureViewModel extends ViewModel {
    private MainViewModel parent;
    private Stage stage = null;
    private Figure figure;
    private int idFigure;
    private final Figure defaultFigure = new Rectangle(0.0, 0.0, 100.0, 50.0, Color.WHITE, Color.RED);


    public IOFigureViewModel(Figure figure, ViewModel parent) {
        this.parent = (MainViewModel) parent;
        this.figure = figure;
        this.idFigure = ((MainViewModel) parent).getID(figure);
        if (this.idFigure == -1) {
            this.idFigure = 0;
            this.figure = this.defaultFigure;
        }
    }


    /******************************
     *          GET
     ******************************/

    public void setFigureID(int id) {
        if (id < 0) {
            this.idFigure = 0;
            this.figure = this.defaultFigure;
        } else {
            this.idFigure = id;
        }

    }

    public void setFigure(Figure f) {
        if (this.figure == null) {
            this.idFigure = 0;
            this.figure = this.defaultFigure;
        } else {
            this.figure = f;
        }

    }

    public void setFigure(Figure f, int id) {
        this.figure = f;
        this.idFigure = id;
        this.view.update();
    }


    /******************************
     *          GET
     ******************************/


    public Figure getFigure() {
        return this.figure;
    }

    public int getFigureID() {
        return this.idFigure;
    }

    public Figure getSettings() {
        return ((IOFigureView) this.view).getSettings(this.figure.clone());
    }


    /******************************
     *          Update
     ******************************/

    public void updateFigure() {
        int id = (int) ((IOFigureView) this.view).getIndex();
        if (id != this.idFigure) {
            Figure f = this.parent.getFigure(id);
            if (f != null) {
                this.idFigure = id;
                this.figure = f;
            }
        } else {
            Figure f = this.updateFigure(this.figure, false);
            this.figure = f;
        }

        this.parent.updateDrawing(this.figure, this.idFigure);
    }

    public Figure updateFigure(Figure figure, boolean create) {
        Figure fig = figure;
        String pname = figure.getClass().getPackageName();
        String formF = ((IOFigureView) this.view).getForm();
        String form = formF;
        if (create || !formF.equals(figure.getForm())) {
            try {
                fig = (Figure) Class.forName(pname + "." + form).getConstructor().newInstance();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        ((IOFigureView) this.view).getSettings(fig);
        return fig;

    }


    public void btnOKHandle() {
        this.updateFigure();
    }

    public void btnAddHandle() {
        Figure f = this.figure.clone();
        this.figure = this.updateFigure(f, true);
        this.idFigure = this.parent.addFigure(f) - 1;
        this.parent.updateDrawing(this.figure, this.idFigure);
    }

    public void btnRemoveHandle() {
        this.figure = this.parent.removeFigure(this.figure);
        this.idFigure = 0;
        this.parent.updateDrawing(this.figure, this.idFigure);
    }
}
