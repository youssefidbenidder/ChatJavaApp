package controler;

import Entity.Message;
import Entity.User;
import client.ChatEventListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import main.ChatMain;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatWindow implements ChatEventListener, Initializable {

    //pour tester
    public static User user;
    public static User currentUser;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane svgPane;

    @FXML
    private AnchorPane profile;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private TextArea txtMsg;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox chatBox;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnEmoji;

    @FXML
    private AnchorPane detailPane1;

    @FXML
    private ScrollPane clientListScroll;

    @FXML
    private VBox clientListBox;

    @FXML
    private TextFlow emojiList;

    @FXML
    private Pane anchor;

    @FXML
    private FontAwesomeIcon bars;

    @FXML
    private JFXButton profilebutton;

    @FXML
    private AnchorPane titleBar;

    @FXML
    private Button btnClose;

    @FXML
    private Pane pan1;

    @FXML
    private AnchorPane home;

    @FXML
    private VBox vboxFriends;

    @FXML
    private ImageView imageprofile;

    @FXML
    private JFXRadioButton male;

    @FXML
    private JFXRadioButton femal;

    @FXML
    private JFXDatePicker date;

    @FXML
    private AnchorPane hidden;

    @FXML
    private PasswordField password;

    @FXML
    private Button back;

    @FXML
    private TextField erreur;

    @FXML
    private JFXButton button;

    @FXML
    private VBox clientListBox1;

    @FXML
    public Pane pan2;


    @FXML
    TextField userTexte;

    @FXML
    private JFXButton Add;

    @FXML
    private TextField userToInvite;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //aniss
        pan2.setVisible(false);
        pan1.setVisible(false);
        hidden.setVisible(false);
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        for (Node text : emojiList.getChildren()) {
            text.setOnMouseClicked(event -> {
                txtMsg.setText(txtMsg.getText() + " " + ((Text) text).getText());
                emojiList.setVisible(false);
            });
        }

        ChatMain.client.addChatEventListener(this);
        try {
            ChatMain.client.sendConnectedFriendsList(user);
            ChatMain.client.sendOfflineFriendsList(user);
            ChatMain.client.ConnectionNotification(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendInvitation(ActionEvent event) {
        try {
            ChatMain.client.invite(user, userToInvite.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectSuccess(String token, User user) {

    }

    @Override
    public void onConnectFailed() {

    }

    @Override
    public void onMessageReceived(User fromUser, String content) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageFromFriend(fromUser.getUsername(),content);
            }
        });
    }
    public void messageFromFriend(String friendName,String content){
        Text text = new Text(content);
        text.setFill(Color.WHITE);
        text.getStyleClass().add("msg");
        TextFlow tempFlow = new TextFlow();
        Text txtName = new Text(friendName + "\n");
        txtName.getStyleClass().add("txtName");
        tempFlow.getChildren().add(txtName);
        tempFlow.getChildren().add(text);
        tempFlow.setMaxWidth(200);
        TextFlow flow = new TextFlow(tempFlow);
        HBox hbox = new HBox(12);
        Circle img = new Circle(32, 32, 16);

        try {
            String path = new File(String.format("file:C:\\Users\\youssef\\IdeaProjects\\JavaAppV\\src\\image\\youssef.jpg", ChatWindow.user.getUsername())).toURI().toString();
            //img.setFill(new ImagePattern(new Image(path)));
        } catch (Exception ex) {
            String path = new File("file:C:\\Users\\youssef\\IdeaProjects\\JavaAppV\\src\\image\\youssef.jpg").toURI().toString();
            // img.setFill(new ImagePattern(new Image(path)));
        }
        img.getStyleClass().add("imageView");


        tempFlow.getStyleClass().add("tempFlowFlipped");
        flow.getStyleClass().add("textFlowFlipped");
        chatBox.setAlignment(Pos.TOP_LEFT);
        hbox.setAlignment(Pos.BOTTOM_LEFT);
        //hbox.getChildren().add(img);
        hbox.getChildren().add(flow);
        hbox.getStyleClass().add("hbox");
        chatBox.getChildren().addAll(hbox);

    }
    @Override
    public void onOldMessagesReceived(User userFrom, User userTo, List<Message> messages) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatBox.getChildren().clear();
                for (Message message : messages){
                    if (message.getUserFrom().equals(userFrom)) {
                        messageFromMe(message.getMessage());
                    }else messageFromFriend(message.getUserFrom().getUsername(),message.getMessage());
                }
            }
        });
    }

    @Override
    public void onConnectedFriendsListReceived(List<User> users) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (User user : users) {
                    if (!user.getUsername().equals(ChatWindow.user.getUsername())) {
                        HBox container = new HBox();
                       container.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                txtMsg.clear();
                                txtMsg.setEditable(true);
                                currentUser = user;
                                try {
                                    ChatMain.client.loadOldMessage(ChatWindow.user,currentUser);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        container.setId(user.getUsername());
                        container.setAlignment(Pos.CENTER_LEFT);
                        container.setSpacing(10);
                        container.setPrefWidth(clientListBox.getPrefWidth());
                        container.setPadding(new Insets(3));
                        container.getStyleClass().add("online-user-container");
                        Circle img = new Circle(30, 30, 15);
                        try {
                            String path = new File(String.format("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\%s.jpg", user.getUsername())).toURI().toString();
                            //img.setFill(new ImagePattern(new Image(path)));

                        } catch (Exception ex) {
                            String path = new File("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\user.png").toURI().toString();
                            //img.setFill(new ImagePattern(new Image(path)));
                        }
                        //   container.getChildren().add(img);
                        VBox userDetailContainer = new VBox();
                        userDetailContainer.setPrefWidth(clientListBox.getPrefWidth() / 1.7);
                        Label lblUsername = new Label(user.getUsername());
                        lblUsername.getStyleClass().add("online-label");
                        userDetailContainer.getChildren().add(lblUsername);
                        Label lblName = new Label(user.getUsername());
                        lblName.getStyleClass().add("online-label-details");
                        userDetailContainer.getChildren().add(lblName);
                        container.getChildren().add(userDetailContainer);
                        Label settings = new Label("oo");
                        settings.getStyleClass().add("online-settings");
                        settings.setTextAlignment(TextAlignment.CENTER);
                        container.getChildren().add(settings);
                        clientListBox.getChildren().add(container);
                    }
                }
            }
        });
    }

    @Override
    public void onOfflineFriendsListReceived(List<User> users) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (User user : users) {
                    if (!user.getUsername().equals(ChatWindow.user.getUsername())) {
                        HBox container = new HBox();
                       container.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                txtMsg.clear();
                                txtMsg.setEditable(true);
                                currentUser = user;
                                try {
                                    ChatMain.client.loadOldMessage(ChatWindow.user,currentUser);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        container.setId(user.getUsername());
                        container.setAlignment(Pos.CENTER_LEFT);
                        container.setSpacing(10);
                        container.setPrefWidth(clientListBox.getPrefWidth());
                        container.setPadding(new Insets(3));
                        container.getStyleClass().add("online-user-container");
                        Circle img = new Circle(30, 30, 15);
                        try {
                            String path = new File(String.format("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\%s.jpg", user.getUsername())).toURI().toString();
                            //img.setFill(new ImagePattern(new Image(path)));

                        } catch (Exception ex) {
                            String path = new File("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\user.png").toURI().toString();
                            //img.setFill(new ImagePattern(new Image(path)));
                        }
                        //   container.getChildren().add(img);
                        VBox userDetailContainer = new VBox();
                        userDetailContainer.setPrefWidth(clientListBox.getPrefWidth() / 1.7);
                        Label lblUsername = new Label(user.getUsername());
                        lblUsername.getStyleClass().add("online-label");
                        userDetailContainer.getChildren().add(lblUsername);
                        Label lblName = new Label(user.getUsername());
                        lblName.getStyleClass().add("online-label-details");
                        userDetailContainer.getChildren().add(lblName);
                        container.getChildren().add(userDetailContainer);
                        Label settings = new Label("oo");
                        settings.getStyleClass().add("offline-settings");
                        settings.setTextAlignment(TextAlignment.CENTER);
                        container.getChildren().add(settings);
                        clientListBox.getChildren().add(container);
                    }
                }
            }
        });
    }

    /*
        @FXML//attention
        void sendMessage(MouseEvent event) {
            try {
                System.out.println("username : " + username + "current : " + currentUser);
                ChatMain.client.sendMessage(username, currentUser, typeMessageText.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    */
    @Override
    public void onInvitationReceived(String fromId, String content) {
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invitation received");
                alert.setHeaderText(content);

                ButtonType buttonAccept = new ButtonType("Accept");
                ButtonType buttonReject = new ButtonType("Rejecte");

                alert.getButtonTypes().setAll(buttonAccept, buttonReject);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonAccept) {
                    // ....
                } else {
                    // ....
                }
            }
        });*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String client = fromId;

                HBox container = new HBox();
                container.setAlignment(Pos.CENTER_LEFT);
                container.setSpacing(10);
                container.setPrefWidth(clientListBox1.getPrefWidth());
                container.setPadding(new Insets(3));
                container.getStyleClass().add("online-user-container");
                Circle img = new Circle(30, 30, 15);
                try {
                    String path = new File(String.format("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\%s.jpg", client)).toURI().toString();
                    // img.setFill(new ImagePattern(new Image(path)));

                } catch (Exception ex) {
                    String path = new File("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\user.png").toURI().toString();
                    //img.setFill(new ImagePattern(new Image(path)));
                }
                //container.getChildren().add(img);

                VBox userDetailContainer = new VBox();
                userDetailContainer.setPrefWidth(clientListBox1.getPrefWidth() / 1.7);
                Label lblUsername = new Label(client);
                lblUsername.getStyleClass().add("online-label");
                userDetailContainer.getChildren().add(lblUsername);
                Label lblName = new Label(client);
                lblName.getStyleClass().add("online-label-details");
                userDetailContainer.getChildren().add(lblName);

                container.getChildren().add(userDetailContainer);

                Button settings = new Button("Confirm");
                settings.getStyleClass().add("invit");
                settings.setTextAlignment(TextAlignment.CENTER);
                container.getChildren().add(settings);
                Button settings1 = new Button("Delete");
                settings1.getStyleClass().add("invit");
                settings1.setTextAlignment(TextAlignment.CENTER);
                container.getChildren().add(settings1);
                Platform.runLater(() -> clientListBox1.getChildren().add(container));
            }

        });
    }

    @Override
    public void onUserConnect(User user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> clientListBox.getChildren().clear());
                try {
                    ChatMain.client.sendConnectedFriendsList(user);
                    ChatMain.client.sendOfflineFriendsList(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //and remove it from offline list
    }

    @Override
    public void onUserLeave(User user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> clientListBox.getChildren().clear());
                try {
                    ChatMain.client.sendConnectedFriendsList(user);
                    ChatMain.client.sendOfflineFriendsList(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML
    void testinvit(ActionEvent event) {

    }

    @FXML
    void testmessage(ActionEvent event) {

    }


    @FXML
    void closeAction(ActionEvent event) {
        try {
            ChatMain.client.DisconnectionNotification(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    @FXML
    void emojiAction(ActionEvent event) {
        if (emojiList.isVisible()) {

            emojiList.setVisible(false);
        } else {
            emojiList.setVisible(true);
        }
    }


    @FXML
    void sendMessage(ActionEvent event) throws RemoteException {

        String msg = txtMsg.getText();
        messageFromMe(msg);
        try {
            ChatMain.client.sendMessage(new Message(user, currentUser, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messageFromMe(String msg){
        Text text = new Text(msg);
        text.setFill(Color.WHITE);
        text.getStyleClass().add("msg");
        TextFlow tempFlow = new TextFlow();
        tempFlow.getChildren().add(text);
        tempFlow.setMaxWidth(200);
        TextFlow flow = new TextFlow(tempFlow);
        HBox hbox = new HBox(12);
        Circle img = new Circle(32, 32, 16);

        try {
            String path = new File(String.format("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\youssef.jpg", ChatWindow.user.getUsername())).toURI().toString();
            //img.setFill(new ImagePattern(new Image(path)));
        } catch (Exception ex) {
            String path = new File("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\youssef.jpg").toURI().toString();
            //img.setFill(new ImagePattern(new Image(path)));
        }
        // img.getStyleClass().add("imageView");

        //if (!user_name.equals(user_name_send)) {

        text.setFill(Color.WHITE);
        tempFlow.getStyleClass().add("tempFlow");
        flow.getStyleClass().add("textFlow");
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(flow);
        // hbox.getChildren().add(img);


          /*  tempFlow.getStyleClass().add("tempFlowFlipped");
            flow.getStyleClass().add("textFlowFlipped");
            chatBox.setAlignment(Pos.TOP_LEFT);
            hbox.setAlignment(Pos.BOTTOM_LEFT);
            hbox.getChildren().add(img);
            hbox.getChildren().add(flow);
*/

           /* text.setFill(Color.WHITE);
            tempFlow.getStyleClass().add("tempFlow");
            flow.getStyleClass().add("textFlow");
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().add(flow);
            hbox.getChildren().add(img);*/


        hbox.getStyleClass().add("hbox");
        chatBox.getChildren().addAll(hbox);
    }

    public void setUser(User profile) {
        this.user = profile;
    }

    @FXML
    void add_online(ActionEvent event) {
        //pour supprimer tout les online  users
        //Platform.runLater(() -> clientListBox.getChildren().clear());

    }

    @FXML
    public void createpage(ActionEvent event) {
        if (profile.isVisible()) {
            profile.setVisible(false);
            pan1.setVisible(true);
            pan2.setVisible(false);
        } else {
            profile.setVisible(true);
            pan1.setVisible(false);
        }
    }

    @FXML
    public void back(ActionEvent event) {
        pan1.setVisible(false);
        pan2.setVisible(false);
        profile.setVisible(true);

    }

    @FXML
    public void hide(ActionEvent event) {
        if (hidden.isVisible()) {
            hidden.setVisible(false);
        } else {
            hidden.setVisible(true);
        }
    }

    //youssef
    @FXML
    public void valider(ActionEvent event) {
        erreur.setStyle("-fx-text-inner-color:red;-fx-background-color:transparent");
        erreur.setText("Veuillez remplir tout les champs");
    }

    @FXML
    void modifierImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String imageUrl = file.toURI().toURL().toExternalForm();
                Image image = new Image(imageUrl);
                imageprofile.setImage(image);
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @FXML
    public void back1(ActionEvent event) {
        pan2.setVisible(false);
        pan1.setVisible(false);
        profile.setVisible(true);

    }

    @FXML
    void createinvit(ActionEvent event) {
        //pour supprimer tout les online  users
        //Platform.runLater(() -> clientListBox.getChildren().clear());
        String client = "aniss";

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(10);
        container.setPrefWidth(clientListBox1.getPrefWidth());
        container.setPadding(new Insets(3));
        container.getStyleClass().add("online-user-container");
        Circle img = new Circle(30, 30, 15);
        try {
            String path = new File(String.format("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\%s.jpg", client)).toURI().toString();
            // img.setFill(new ImagePattern(new Image(path)));

        } catch (Exception ex) {
            String path = new File("C:\\Users\\youssef\\Desktop\\projetFinalFinal\\src\\image\\user.png").toURI().toString();
            //img.setFill(new ImagePattern(new Image(path)));
        }
        //container.getChildren().add(img);

        VBox userDetailContainer = new VBox();
        userDetailContainer.setPrefWidth(clientListBox1.getPrefWidth() / 1.7);
        Label lblUsername = new Label(client);
        lblUsername.getStyleClass().add("online-label");
        userDetailContainer.getChildren().add(lblUsername);
        Label lblName = new Label(client);
        lblName.getStyleClass().add("online-label-details");
        userDetailContainer.getChildren().add(lblName);

        container.getChildren().add(userDetailContainer);

        Button settings = new Button("Confirm");
        settings.getStyleClass().add("invit");
        settings.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(settings);
        Button settings1 = new Button("Delete");
        settings1.getStyleClass().add("invit");
        settings1.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(settings1);
        Platform.runLater(() -> clientListBox1.getChildren().add(container));
    }

    @FXML
    public void createpage1(ActionEvent event) {
        if (pan2.isVisible()) {
            profile.setVisible(true);
            pan1.setVisible(false);
            pan2.setVisible(false);
        } else {
            profile.setVisible(false);
            pan1.setVisible(false);
            pan2.setVisible(true);
        }
    }

}

