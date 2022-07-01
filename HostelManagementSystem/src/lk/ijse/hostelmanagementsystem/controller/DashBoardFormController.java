package lk.ijse.hostelmanagementsystem.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostelmanagementsystem.util.Loader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardFormController implements Loader {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane mainContext;

    public void initialize() {
        loadDateAndTime();
    }

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

    public void roomManagementOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RoomManagementForm");
    }

    public void homeOnAction(ActionEvent actionEvent) {
    }

    public void roomReservationOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RegistrationForm");
    }

    public void userManagementOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("UserManagementForm");
    }

    public void studentManagementOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("StudentManagementForm");
    }

    public void keyMoneyOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("StudentPaymentForm");
    }

    public void loadUi(String location) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"));
        mainContext.getChildren().add(parent);
    }

    private void loadDateAndTime() {
        //set Date
        lblDate.setText(new SimpleDateFormat("yyy-MMMM-dd").format(new Date()));

        //set Time
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e ->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute()+ ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


}
