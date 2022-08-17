package com.example.ppt09_2072029.controller;

import com.example.ppt09_2072029.dao.CategoryDao;
import com.example.ppt09_2072029.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondController {
    @FXML
    public TableView<Category> tableCategory;
    @FXML
    private TableColumn<Integer, Category> colId_category;
    @FXML
    private TableColumn<String, Category> colCategoryName;
    @FXML
    private TextField txtId_category;
    @FXML
    private TextField txtName_category;
    ObservableList<Category> cList;
    private CategoryDao categoryDao;

    public void initialize() {
        categoryDao = new CategoryDao();
        ShowData();
    }

    public void ShowData() {
        cList = FXCollections.observableArrayList(categoryDao.getData());
        tableCategory.setItems(cList);
        colId_category.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void Reset() {
        txtId_category.clear();
        txtName_category.clear();
    }

    public void OnSaveCategory(ActionEvent actionEvent) {
        if (txtId_category.getText().isEmpty() || txtName_category.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            Category c = new Category();
            c.setId(Integer.parseInt(txtId_category.getText()));
            c.setName(txtName_category.getText());
            categoryDao.addData(c);
            ShowData();
            Reset();
        }
    }
}