package lk.ijse.hostelmanagementsystem.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.util.Loader;
import lk.ijse.hostelmanagementsystem.view.tm.StudentTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class DashBoardFormController implements Loader {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane mainContext;
    public Label lblRoomCount;
    public Label lblStudentCount;

    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDob;
    public TableColumn colGender;

    // Property Injection(DI)
    private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);
    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadAllStudents();
        loadDateAndTime();

        try {
            lblRoomCount.setText(String.valueOf(roomBO.getRoomCount()));
            lblStudentCount.setText(String.valueOf(studentBO.getStudentCount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudents() {
        tblStudent.getItems().clear();
        /*Get all students*/
        try {
            ArrayList<StudentDTO> allStudents = studentBO.getAllStudents();

            for (StudentDTO student : allStudents) {
                tblStudent.getItems().add(new StudentTM(student.getSId(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("HomePageForm");
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
