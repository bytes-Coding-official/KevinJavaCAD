package viewmodel;

import view.IView;

/**
 * Klasse ViewModel
 *
 * @author Kevin Schallmo
 * @version 11.01.2024
 */


public abstract class ViewModel {
    protected IView view = null;

    public void registerView(IView view) {
        this.view = view;
    }
}

