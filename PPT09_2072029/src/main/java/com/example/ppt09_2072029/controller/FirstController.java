package com.example.ppt09_2072029.controller;

import com.example.ppt09_2072029.GroupThread;
import com.example.ppt09_2072029.HelloApplication;
import com.example.ppt09_2072029.SimpleThread;
import com.example.ppt09_2072029.dao.CategoryDao;
import com.example.ppt09_2072029.dao.ItemsDao;
import com.example.ppt09_2072029.model.Category;
import com.example.ppt09_2072029.model.Items;
import com.example.ppt09_2072029.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstController {
    @FXML
    public TableView<Items> tableHome;
    @FXML
    private TableColumn<Integer, Items> colId;
    @FXML
    private TableColumn<String, Items> colName;
    @FXML
    private TableColumn<Double, Items> colPrice;
    @FXML
    private TableColumn<Category, Items> colCategory;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDescription;
    @FXML
    public ComboBox<Category> comboCategory;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    ObservableList<Items> iList;
    private Stage stage;
    public CategoryDao categoryDao = new CategoryDao();
    public ItemsDao itemsDao = new ItemsDao();
    private FXMLLoader fxmlLoader;

    public void initialize() {
        ObservableList<Category> cList = FXCollections.observableArrayList(categoryDao.getData());
        comboCategory.setItems(cList);

        iList = FXCollections.observableArrayList();
        ShowData();
    }

    public void OnShowTab(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.showAndWait();

        ObservableList<Category> cList = FXCollections.observableArrayList(categoryDao.getData());
        comboCategory.setItems(cList);
    }

    public void OnCloseHome(ActionEvent actionEvent) {
        txtId.getScene().getWindow().hide();
    }

    public void ShowData() {
        iList.clear();
        iList = FXCollections.observableArrayList(itemsDao.getData());
        tableHome.setItems(iList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryByCategoryId"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void OnSave(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty() || comboCategory.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            Items i = new Items();
            i.setId(Integer.parseInt(txtId.getText()));
            i.setName(txtName.getText());
            i.setPrice(Double.parseDouble(txtPrice.getText()));
            i.setDescription(txtDescription.getText());
            i.setCategoryByCategoryId(comboCategory.getValue());
            itemsDao.addData(i);
            ShowData();
            OnReset();
        }
    }

    public void OnReset() {
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        txtDescription.clear();
        comboCategory.setValue(null);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.setDisable(false);
    }

    public void OnUpdate(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty() || comboCategory.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            Items i = new Items();
            i.setId(Integer.parseInt(txtId.getText()));
            i.setName(txtName.getText());
            i.setPrice(Double.parseDouble(txtPrice.getText()));
            i.setDescription(txtDescription.getText());
            i.setCategoryByCategoryId(comboCategory.getValue());
            itemsDao.updateData(i);
            ShowData();
            OnReset();
        }
    }

    public void OnDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            itemsDao.deleteData(tableHome.getSelectionModel().getSelectedItem());
        }
        OnReset();
        ShowData();
    }

    public void OnClick(MouseEvent mouseEvent) {
        if(!tableHome.getSelectionModel().getSelectedItems().isEmpty()) {
            txtId.setText(String.valueOf(tableHome.getSelectionModel().getSelectedItem().getId()));
            txtName.setText(tableHome.getSelectionModel().getSelectedItem().getName());
            txtPrice.setText(String.valueOf(tableHome.getSelectionModel().getSelectedItem().getPrice()));
            txtDescription.setText(tableHome.getSelectionModel().getSelectedItem().getDescription());
            comboCategory.setValue(tableHome.getSelectionModel().getSelectedItem().getCategoryByCategoryId());
            txtId.setDisable(true);
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void OnSimpleReport(ActionEvent actionEvent) {
        SimpleThread simpleThread = new SimpleThread();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(simpleThread);
        executorService.shutdown();
    }

    public void OnGroupReport(ActionEvent actionEvent) {
        GroupThread groupThread = new GroupThread();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(groupThread);
        executorService.shutdown();
    }
}