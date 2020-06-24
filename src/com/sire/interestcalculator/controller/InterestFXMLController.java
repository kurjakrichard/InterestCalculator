/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.controller;

import com.sire.interestcalculator.domain.InterestRate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author balza
 */
public class InterestFXMLController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML variables">
    @FXML
    private SplitPane mainSplit;
    @FXML
    private StackPane menuPane;
    @FXML
    private Pane calculatorPane;
    @FXML
    private TextField inputPhoneNumber1;
    @FXML
    private Button addPartner1;
    @FXML
    private Pane ratePane;
    @FXML
    private DatePicker inputRateDate;
    @FXML
    private TextField inputRate;
    @FXML
    private Button addRate;
    @FXML
    private Pane exportPane1;
    @FXML
    private Button exportButton;
    @FXML
    private TextField inputFilename;
    @FXML
    private VBox alertBox;
    @FXML
    private Label alertText;
    @FXML
    private Button alertButton;
    @FXML
    private TableView rateTable;
//</editor-fold>

    private final ObservableList<InterestRate> rates = FXCollections.observableArrayList(
            new InterestRate("2020.01.01", "0.4"),
            new InterestRate("2020.06.01", "0.6"),
            new InterestRate("2020.09.01", "0.5")
    );

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn rateDateCol = new TableColumn("Dátum");
        rateDateCol.setMinWidth(150);
        rateDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rateDateCol.setCellValueFactory(new PropertyValueFactory<>("rateDate"));

        TableColumn rateCol = new TableColumn("Kamatkulcs");
        rateCol.setMinWidth(150);
        rateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rateTable.getColumns().addAll(rateDateCol, rateCol);
        rateTable.setItems(rates);

    }

    @FXML
    private void addPartner(ActionEvent event) {
    }

    @FXML
    private void addRate(ActionEvent event) {
    }

    @FXML
    private void exportList(ActionEvent event) {
    }

    @FXML
    private void alertButton(ActionEvent event) {
    }

}
