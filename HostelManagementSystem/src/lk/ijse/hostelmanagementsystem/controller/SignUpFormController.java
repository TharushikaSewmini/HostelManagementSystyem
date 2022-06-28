package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelmanagementsystem.util.ValidationUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SignUpFormController {
    public TextField txtFullName;
    public TextField txtEmail;
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public JFXButton btnSignUp;
    public JFXButton btnLogin;
    public AnchorPane context2;
    public ImageView imgInvisible;
    public ImageView imgVisible;
    public TextField txtVisiblePassword;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        btnSignUp.setDisable(true);
        imgInvisible.setVisible(false);

        //add pattern and text to the map
        //Create a pattern and compile it to use
        Pattern fullNamePattern = Pattern.compile("^[A-Z][A-z ]{3,30}$");
        Pattern emailPattern = Pattern.compile("^[A-z]{3,30}@gmail.com$");
        Pattern userNamePattern = Pattern.compile("^[A-Z][A-z ]{3,15}$");
        Pattern passwordPattern = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");
        //Pattern visiblePasswordPattern = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");

        map.put(txtFullName, fullNamePattern);
        map.put(txtEmail, emailPattern);
        map.put(txtUserName, userNamePattern);
        map.put(pwdPassword, passwordPattern);
        //map.put(txtVisiblePassword, visiblePasswordPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSignUp);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSignUp);

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();

            }
        }
    }

    public void MouseClickedOnAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgVisible":
                    pwdPassword.setVisible(false);
                    txtVisiblePassword.setVisible(true);
                    imgInvisible.setVisible(true);
                    imgVisible.setVisible(false);

                    txtVisiblePassword.setText(pwdPassword.getText());
                    break;
                case "imgInvisible":
                    txtVisiblePassword.setVisible(false);
                    pwdPassword.setVisible(true);
                    imgVisible.setVisible(true);
                    imgInvisible.setVisible(false);

                    pwdPassword.setText(txtVisiblePassword.getText());
                    break;
            }
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("DashBoardForm");

    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("LoginForm");
    }

    public void loadUi(String location) throws IOException {
        Stage stage = (Stage) context2.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

}
