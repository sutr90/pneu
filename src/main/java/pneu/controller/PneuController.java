package pneu.controller;

import javafx.fxml.Initializable;
import pneu.model.Rack;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class PneuController implements Initializable {
    @Inject
    private Rack rack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
