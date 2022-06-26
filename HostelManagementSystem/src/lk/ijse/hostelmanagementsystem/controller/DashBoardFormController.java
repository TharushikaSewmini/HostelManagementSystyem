package lk.ijse.hostelmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane mainContext;

    public void backToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) mainContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/DashBoardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) mainContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/LoginForm.fxml"))));
        stage.centerOnScreen();
    }

    public void homeOnAction(ActionEvent actionEvent) {
    }

    public void roomOnAction(ActionEvent actionEvent) {
    }

    public void studentOnAction(ActionEvent actionEvent) {
    }

    public void employeeOnAction(ActionEvent actionEvent) {
    }

    public void keyMoneyOnAction(ActionEvent actionEvent) throws IOException {
        //loadUi("");
    }

    public void loadUi(String location) throws IOException {
        /*Stage stage = (Stage) context1.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"))));
        stage.centerOnScreen();*/

        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"));
        mainContext.getChildren().add(parent);
    }
}