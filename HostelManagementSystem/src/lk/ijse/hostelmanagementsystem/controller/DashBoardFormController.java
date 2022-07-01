package lk.ijse.hostelmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelmanagementsystem.util.Loader;

import java.io.IOException;

public class DashBoardFormController implements Loader {
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

    public void roomOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RoomManagementForm");
    }

    public void studentOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RegistrationForm");
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("UserManagementForm");
    }

    public void keyMoneyOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("StudentManagementForm");
    }

    public void loadUi(String location) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"));
        mainContext.getChildren().add(parent);
    }
}
