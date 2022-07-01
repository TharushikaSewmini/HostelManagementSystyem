package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.UserBO;
import lk.ijse.hostelmanagementsystem.dto.UserDTO;
import lk.ijse.hostelmanagementsystem.util.ValidationUtil;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UserManagementFormController {
    public TextField txtUserId;
    public TextField txtCurrentUserName;
    public TextField txtNewUserName;
    public PasswordField pwdNewPassword;
    public JFXButton btnSave;
    public JFXButton btnCancel;
    public PasswordField pwdConfirmPassword;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    // Property Injection(DI)
    private final UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);

    public void initialize() {
        initUI();

        try {
            getUserName(txtUserId.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern userId = Pattern.compile("[U,0-9]{4}");
        Pattern userNamePattern = Pattern.compile("^[A-Z][A-z ]{3,15}$");
        Pattern passwordPattern = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");
        Pattern currentPassword = Pattern.compile("^.*[A-z].*[0-9].*[!@#$%^&*()_]$");

        map.put(txtUserId, userId);
        //map.put(txtCurrentUserName, userNamePattern);
        map.put(txtNewUserName, userNamePattern);
        map.put(pwdNewPassword, passwordPattern);
        map.put(pwdConfirmPassword, currentPassword);
    }

    private void initUI() {
        txtUserId.clear();
        txtCurrentUserName.clear();
        txtNewUserName.clear();
        pwdNewPassword.clear();
        pwdConfirmPassword.clear();
        txtUserId.requestFocus();
        btnSave.setDisable(true);
    }

    private void getUserName(String id) throws Exception {
        try{
            id = txtCurrentUserName.getText();
            String username= userBO.getUserName(id);
            txtCurrentUserName.setText(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();

            }
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();
        String currentUserName = txtCurrentUserName.getText();
        String newUserName = txtNewUserName.getText();
        String newPassword = pwdNewPassword.getText();
        String confirmPassword = pwdConfirmPassword.getText();

        if (pwdConfirmPassword.getText().equals(pwdNewPassword.getText())) {
            try {
                //Save Changes
                /*userBO.add(new UserDTO(userId, newUserName, newPassword));
                new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
                if (userBO.update(new UserDTO(userId, newUserName, newPassword))) {
                new Alert(Alert.AlertType.CONFIRMATION, userId + "Save Changes.!").show();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Password doesn't match.!").show();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        txtUserId.clear();
        txtCurrentUserName.clear();
        txtNewUserName.clear();
        pwdNewPassword.clear();
        pwdConfirmPassword.clear();
        txtUserId.requestFocus();
        btnSave.setDisable(true);
    }


}
