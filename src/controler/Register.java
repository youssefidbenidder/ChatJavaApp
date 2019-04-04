package controler;


import Entity.Message;
import Entity.User;
import client.ChatEventListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.ChatMain;
import provider.Environment;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

public class Register implements ChatEventListener, Environment {

    public static boolean register = false;

    public Register() {
        ChatMain.client.addChatEventListener(this);
    }

    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton femal;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXButton cancelAction;
    @FXML
    private JFXButton signup;
    @FXML
    TextField usernameRegister;
    @FXML
    PasswordField passwordRegister;
    @FXML
    PasswordField confirmPassword;
    @FXML
    TextField emailRegister;
    @FXML
    TextField erreur;
    @FXML
    public void initialize() {
        imageView.setVisible(false);
    }

    void ouvrirFenetre(ActionEvent event,String str) throws IOException{

        imageView.setVisible(true);
        Parent signUpWindow = FXMLLoader.load(getClass().getResource(str));
        Scene scene = new Scene(signUpWindow);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Stage mainWindow;
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.close();
        imageView.setVisible(false);
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public void Style(){
        String css = "-fx-border-color:red;-fx-background-color:#565a60;-fx-border-radius:5px;-fx-background-radius: 5px";
        if(emailRegister.getText().trim().isEmpty()) {
            emailRegister.getParent().setStyle(css);
        }
        if(usernameRegister.getText().trim().isEmpty()) {
            usernameRegister.getParent().setStyle(css);
        }
        if(passwordRegister.getText().trim().isEmpty()) {
            passwordRegister.getParent().setStyle(css);
        }
        if(confirmPassword.getText().trim().isEmpty()) {
            confirmPassword.getParent().setStyle(css);
        }
        erreur.setText("Veuillez remplir tout les champs");
    }

    @FXML
    void inscription(ActionEvent event) throws IOException {
        String passwordRegisterText = passwordRegister.getText();
        String confirmPasswordText = confirmPassword.getText();
        if(emailRegister.getText().trim().isEmpty() || passwordRegister.getText().trim().isEmpty() || confirmPassword.getText().trim().isEmpty() || usernameRegister.getText().trim().isEmpty() || (!male.isSelected() && !femal.isSelected())
        ){
            Style();
        }else {
            if (passwordRegisterText.equals(confirmPasswordText)) {
                if(!validate(emailRegister.getText())){
                    erreur.setText("Email incorrect");
                }
                else {
                    imageView.setVisible(true);
                    signup.setDisable(true);
                    cancelAction.setDisable(true);
                    boolean sexe = male.isSelected();
                    ChatMain.client.inscriptionUser(usernameRegister.getText(), passwordRegister.getText(),
                            emailRegister.getText(),sexe);
                }
            } else {
                passwordRegister.setStyle("-fx-text-fill: rgb(211, 76, 76);-fx-background-color:transparent");
                confirmPassword.setStyle("-fx-text-fill: rgb(211, 76, 76);-fx-background-color:transparent");
                erreur.setText("Veuillez ressaisir le Password");
            }
        }
    }

    @FXML
    public void closeprog(ActionEvent event) throws IOException {

        Stage mainWindow;
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.close();
    }

    @FXML
    public void retour(ActionEvent event) throws IOException {
        ouvrirFenetre(event,"../view/login.fxml");
    }

    @FXML
    public void selectMale(ActionEvent actionEvent) {
        femal.setSelected(false);
    }

    @FXML
    public void selectFemal(ActionEvent actionEvent) {
        male.setSelected(false);
    }

    @Override
    public void onConnectSuccess(String token, User profile) {
        System.out.println("token = " + token);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
                Parent root = null;
                try {
                    root = (Parent)fxmlLoader.load();
                    Login controller = fxmlLoader.<Login>getController();
                    controller.setUser(usernameRegister.getText());
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    Stage mainWindow;
                    mainWindow = (Stage) usernameRegister.getScene().getWindow();
                    mainWindow.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        imageView.setVisible(false);
        signup.setDisable(false);
        cancelAction.setDisable(false);
    }

    @Override
    public void onConnectFailed() {
        imageView.setVisible(false);
        signup.setDisable(false);
        cancelAction.setDisable(false);
        erreur.setText("User deja existant");
    }

    @Override
    public void onMessageReceived(User fromId, String content) {

    }

    @Override
    public void onOldMessagesReceived(User userFrom, User userTp, List<Message> messages) {

    }

    @Override
    public void onConnectedFriendsListReceived(List<User> users) {

    }

    @Override
    public void onOfflineFriendsListReceived(List<User> users) {

    }


    @Override
    public void onInvitationReceived(String fromId, String content) {

    }

    @Override
    public void onUserConnect(User username) {

    }

    @Override
    public void onUserLeave(User user) {

    }


}