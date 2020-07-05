/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.controller;

import com.sire.interestcalculator.domain.InterestElement;
import com.sire.interestcalculator.domain.InterestElementString;
import com.sire.interestcalculator.domain.InterestRate;
import com.sire.interestcalculator.domain.InterestRateString;
import com.sire.interestcalculator.model.InterestModel;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;

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
    private TableView interestTable;
    @FXML
    private Pane exportPane;
    @FXML
    private DatePicker inputDueDate;
    @FXML
    private DatePicker inputPaymentDate;
    @FXML
    private TextField inputAmount;
    @FXML
    private Button calculation;
    @FXML
    private Button exportRateList;
    @FXML
    private Button exportInterests;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="class-variables">
    private final String MENU_MAIN = "Kamat kalkulátor";
    private final String MENU_CALCULATOR = "Kamat számítás";
    private final String MENU_INTEREST = "Kamatok";
    private final String MENU_EXIT = "Kilépés";
    private InterestModel interestModel = new InterestModel();
    private boolean decision;
    ArrayList<InterestElementString> interestStringList = new ArrayList();
//</editor-fold>

    private final ObservableList<InterestRateString> rates = FXCollections.observableArrayList();
    private final ObservableList<InterestElementString> interests = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRateTableData();
        setMenuData();
        setInterestTableData();
    }

    private void setRateTableData() {
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
                interestModel.updateRate(actualRateString);
            }
        }
        );

        TableColumn rateCol = new TableColumn("Kamatmérték (%)");
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
                interestModel.updateRate(actualRateString);
            }
        }
        );

        TableColumn removeCol = new TableColumn("Törlés");
        removeCol.setMinWidth(60);

        Callback<TableColumn<InterestRateString, String>, TableCell<InterestRateString, String>> cellFactory
                = new Callback<TableColumn<InterestRateString, String>, TableCell<InterestRateString, String>>() {
            @Override
            public TableCell call(final TableColumn<InterestRateString, String> param) {
                final TableCell<InterestRateString, String> cell = new TableCell<InterestRateString, String>() {
                    final Button btn = new Button("Törlés");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                InterestRateString rate = getTableView().getItems().get(getIndex());
                                rates.remove(rate);
                                interestModel.removeRate(rate);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        removeCol.setCellFactory(cellFactory);
        rateTable.getColumns().addAll(rateDateCol, rateCol, removeCol);
        rates.addAll(interestModel.selectAllString());
        rateTable.setItems(rates);
    }

    private void setInterestTableData() {
        TableColumn periodCol = new TableColumn("Időszak");
        periodCol.setMinWidth(160);
        periodCol.setCellFactory(TextFieldTableCell.forTableColumn());
        periodCol.setCellValueFactory(new PropertyValueFactory<>("period"));

        TableColumn daysCol = new TableColumn("Eltelt napok száma");
        daysCol.setMinWidth(130);
        daysCol.setCellFactory(TextFieldTableCell.forTableColumn());
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        daysCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn interestCol = new TableColumn("Kamatösszeg");
        interestCol.setMinWidth(100);
        interestCol.setCellFactory(TextFieldTableCell.forTableColumn());
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        interestCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn rateCol = new TableColumn("Kamatmérték");
        rateCol.setMinWidth(100);
        rateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rateCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        interestTable.getColumns().addAll(periodCol, daysCol, rateCol, interestCol);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        Node exitNode = new ImageView(new Image(getClass().getResourceAsStream("/Actions-application-exit-icon.png")));
        Node calculatorNode = new ImageView(new Image(getClass().getResourceAsStream("/Actions-document-preview-archive-icon.png")));
        Node rateNode = new ImageView(new Image(getClass().getResourceAsStream("/Actions-insert-table-icon.png")));

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_MAIN);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT, exitNode);
        nodeItemA.setExpanded(true);

        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_CALCULATOR, calculatorNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_INTEREST, rateNode);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
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
            LocalDate actualDate = inputRateDate.getValue();
            if (Double.parseDouble(inputRate.getText()) > 0 && Double.parseDouble(inputRate.getText()) < 100) {
                InterestRateString newRateString = new InterestRateString(inputRateDate.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), inputRate.getText());
                rates.add(newRateString);
                interestModel.addRate(newRateString);
                clearInputRateFields();
            } else {
                alert("A kamatkulcsnak 0 és 100 közé kell esnie!");
                clearInputRateFields();
            }

        } catch (Exception e) {
            alert("A dátumot és a kamatot helyesen add meg!");
            clearInputRateFields();
        }
    }

    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputFilename.getText();
        fileName = fileName.replaceAll("ˇ\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            if (decision) {
                pdfCreator.pdfGenerationRate(fileName, rates);
                setMenuVisible(false, true, false);
            } else {
                pdfCreator.pdfGeneraton(fileName, interests);
                setMenuVisible(true, false, false);
            }
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
        interests.clear();
        interestStringList.clear();
        interestTable.getItems().clear();
        Long sumOfTheDays = 0L;
        double sumOfTheInterest = 0;
        try {
            String dueDate = inputDueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            String paymentDate = inputPaymentDate.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            Long amount = Long.parseLong(inputAmount.getText());
            ArrayList<InterestElement> interestList = new ArrayList<>();
            ArrayList<InterestRate> rateList = interestModel.selectInterestPeriod(dueDate, paymentDate);
            rateList.add(new InterestRate(inputPaymentDate.getValue().plusDays(1), 0));
            LocalDate previousDate;
            if (rateList.size() == 1) {
                InterestElement element = new InterestElement(inputDueDate.getValue(), inputPaymentDate.getValue(), rateList.get(0).getRate(), amount);
                element.interest();
                element.setDays(ChronoUnit.DAYS.between(element.getStartDate(), element.getEndDate()));
                interestList.add(element);
                InterestElementString stringElement = new InterestElementString();
                interestStringList.add(stringElement);
                interests.add(element.convertToString());
                sumOfTheDays = element.getDays();
                sumOfTheInterest = element.getInterest();
             } else {
                previousDate = inputDueDate.getValue();
                InterestElement element = new InterestElement();
                for (int i = 0; i < rateList.size() - 1; i++) {
                    element = new InterestElement(previousDate, rateList.get(i + 1).getRateDate(), rateList.get(i).getRate(), amount);
                    element.interest();
                    element.setDays(ChronoUnit.DAYS.between(element.getStartDate(), element.getEndDate()));
                    interestList.add(element);
                    previousDate = rateList.get(i + 1).getRateDate();
                    interests.add(element.convertToString());
                    sumOfTheDays += element.getDays();
                    sumOfTheInterest += element.getInterest();
                }
                interests.addAll(interestStringList);
                interestTable.setItems(interests);
            }
        } catch (Exception e) {
            alert("Kérlek adj meg minden adatot!");
        }
    }

    private void clearInputRateFields() {
        inputRateDate.getEditor().clear();
        inputRate.clear();
        inputRateDate.setValue(null);
    }

    @FXML
    private void exportRateList(ActionEvent event) {
        setMenuVisible(false, false, true);
        decision = true;
    }

    @FXML
    private void exportInterests(ActionEvent event) {
        setMenuVisible(false, false, true);
        decision = false;
    }
}
