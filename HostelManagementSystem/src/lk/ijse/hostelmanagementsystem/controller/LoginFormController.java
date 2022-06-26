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
import lk.ijse.hostelmanagementsystem.util.Loader;
import lk.ijse.hostelmanagementsystem.util.ValidationUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginFormController implements Loader {
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public JFXButton btnLogin;
    public JFXButton btnSignUp;
    public AnchorPane context1;
    public ImageView imgVisible;
    public ImageView imgInvisible;
    public TextField txtVisiblePassword;

    int attempts=0;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        btnLogin.setDisable(true);
        imgInvisible.setVisible(false);

        //add pattern and text to the map
        //Create a pattern and compile it to use
        Pattern userNamePattern = Pattern.compile("^[A-Z][A-z ]{3,15}$");
        Pattern passwordPattern = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");
        Pattern visiblePasswordPattern = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");

        map.put(txtUserName, userNamePattern);
        map.put(pwdPassword, passwordPattern);
        map.put(txtVisiblePassword, visiblePasswordPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnLogin);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnLogin);
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it

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


    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        //attempts++;
        if(attempts<=3){
            loadUi("DashBoardForm");
        }else{
            txtUserName.setEditable(false);
            pwdPassword.setEditable(false);
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("SignUpForm");
    }

    public void loadUi(String location) throws IOException {
        Stage stage = (Stage) context1.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hostelmanagementsystem/view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

}
