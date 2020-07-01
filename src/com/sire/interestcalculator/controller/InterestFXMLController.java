/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.controller;

import com.sire.interestcalculator.domain.InterestRate;
import com.sire.interestcalculator.domain.InterestRateString;
import com.sire.interestcalculator.model.InterestModel;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Pane ratePane;
    @FXML
    private DatePicker inputRateDate;
    @FXML
    private TextField inputRate;
    @FXML
    private Button addRate;
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
    @FXML
    private Pane exportPane;
    @FXML
    private Button calculation;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="class-variables">
    private final String MENU_MAIN = "Kamat kalkulátor";
    private final String MENU_CALCULATOR = "Kamat számítás";
    private final String MENU_INTEREST = "Kamatok";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    private InterestModel interestModel = new InterestModel();
//</editor-fold>

    private final ObservableList<InterestRateString> rates = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }

    private void setTableData() {
        TableColumn rateDateCol = new TableColumn("Dátum");
        rateDateCol.setMinWidth(50);
        rateDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rateDateCol.setCellValueFactory(new PropertyValueFactory<>("rateDate"));

        rateDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<InterestRateString, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InterestRateString, String> t) {
                InterestRateString actualRateString = ((InterestRateString) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                actualRateString.setRateDate(t.getNewValue());
                InterestRate actualRate = new InterestRate(actualRateString.getId(), actualRateString.getRateDate(), Double.parseDouble(actualRateString.getRate()));
                interestModel.updateRate(actualRate);
            }
        }
        );

        TableColumn rateCol = new TableColumn("Kamatkulcs");
        rateCol.setMinWidth(50);
        rateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rateCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        rateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<InterestRateString, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InterestRateString, String> t) {
                InterestRateString actualRateString = ((InterestRateString) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                actualRateString.setRate(t.getNewValue());
                InterestRate actualRate = new InterestRate(actualRateString.getId(), actualRateString.getRateDate(), Double.parseDouble(actualRateString.getRate()));
                interestModel.updateRate(actualRate);
            }
        }
        );

        rateTable.getColumns().addAll(rateDateCol, rateCol);
        rates.addAll(interestModel.selectAll());
        rateTable.setItems(rates);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        Node exitNode = new ImageView(new Image(getClass().getResourceAsStream("/Actions-application-exit-icon.png")));

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_MAIN);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT, exitNode);
        nodeItemA.setExpanded(true);

        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_CALCULATOR);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_INTEREST);
        TreeItem<String> nodeItemA3 = new TreeItem<>(MENU_EXPORT);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);
        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_MAIN:
                            try {
                            selectedItem.setExpanded(true);
                        } catch (Exception e) {
                        }
                        break;
                        case MENU_CALCULATOR:
                            setMenuVisible(true, false, false);
                            break;
                        case MENU_INTEREST:
                            setMenuVisible(false, true, false);
                            break;
                        case MENU_EXPORT:
                            setMenuVisible(false, false, true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        });

    }

    private void setMenuVisible(boolean calculatorPaneVisible, boolean ratePaneVisible, boolean exportPaneVisible) {
        calculatorPane.setVisible(calculatorPaneVisible);
        ratePane.setVisible(ratePaneVisible);
        exportPane.setVisible(exportPaneVisible);
    }

    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        alertBox.setVisible(true);
        alertBox.setDisable(false);
        alertText.setText(text);

    }

    @FXML
    private void addRate(ActionEvent event) {
        try {
            Double.parseDouble(inputRate.getText());
            
            if (Double.parseDouble(inputRate.getText()) > 0 && Double.parseDouble(inputRate.getText()) < 100) {
                InterestRate newRate = new InterestRate(inputRateDate.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), Double.parseDouble(inputRate.getText()));
                InterestRateString newRateString = new InterestRateString(inputRateDate.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), inputRate.getText());
                rates.add(newRateString);
                interestModel.addRate(newRate);
                clearInputRateFields();
                inputRateDate.setValue(null);
            } else {
                alert("A kamatkulcsnak 0 és 100 közé kell esnie!");
                clearInputRateFields();
                inputRateDate.setValue(null);

            }

        } catch (Exception e) {
            alert("A dátum vagy a kamatkulcs nem maradhat üresen!");
            clearInputRateFields();
            inputRateDate.setValue(null);
        }

    }

    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputFilename.getText();
        fileName = fileName.replaceAll("ˇ\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, rates);
            inputFilename.clear();
        } else {
            alert("Adj meg egy fájlnevet!");
        }
    }

    @FXML
    private void alertButton(ActionEvent event) {
        mainSplit.setDisable(false);
        mainSplit.setOpacity(1);
        alertBox.setVisible(false);
    }

    @FXML
    private void calculation(ActionEvent event) {
    }

    private void clearInputRateFields() {
        inputRateDate.getEditor().clear();
        inputRate.clear();
    }

}
