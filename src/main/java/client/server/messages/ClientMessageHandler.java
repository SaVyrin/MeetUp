package client.server.messages;

import acquaintance.Person;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.List;

public class ClientMessageHandler extends AbstractMessageHandler {

    private Person loggedInPerson;

    public ClientMessageHandler(Socket socket) {
        super(socket);
    }

    public void sendMessage(Command command, String message) {
        System.out.println("sent to server");

        Message messageToServer = new Message(loggedInPerson);
        messageToServer.setCommand(command);
        messageToServer.setMessage(message);

        try {
            objectOutputStream.writeObject(messageToServer);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to server");
            closeEverything();
        }
    }

    public void receiveMessage(VBox vBoxOnline, VBox vBoxPending, VBox vBoxFriends, ImageView avatar, Text description) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    System.out.println("received from server");
                    Message messageFromServer = (Message) objectInputStream.readObject();

                    Command command = messageFromServer.getCommand();

                    if (command.equals(Command.LOG_IN)) {
                        this.loggedInPerson = messageFromServer.getLoggedInPerson();
                    }
                    if (command.equals(Command.ONLINE)) {
                        List<String> peopleOnline = messageFromServer.getOnlinePeople();
                        addContentToVBox(peopleOnline, Command.ONLINE.getCommandString(), vBoxOnline);
                    }
                    if (command.equals(Command.FRIEND_REQ)) {
                        List<String> pendingRequests = messageFromServer.getPendingFriendRequests();
                        addContentToVBox(pendingRequests, Command.FRIEND_REQ.getCommandString(), vBoxPending);
                    }
                    if (command.equals(Command.FRIENDS)) {
                        List<String> friends = messageFromServer.getFriends();
                        addContentToVBox(friends, Command.FRIENDS.getCommandString(), vBoxFriends);
                    }
                    if (command.equals(Command.SHOW)) {
                        Person show = messageFromServer.getShow();
                        showNextPerson(show, avatar, description);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a server");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }

    private void addContentToVBox(List<String> message, String type, VBox vBox) {
        Platform.runLater(() -> {
            vBox.getChildren().clear();
            for (String str : message) {
                Label label = new Label(type + str);
                label.setStyle("-fx-background-color : #99ff99;");
                vBox.getChildren().add(label);
            }
        });
    }

    private void showNextPerson(Person personToShow, ImageView avatar, Text description) throws MalformedURLException {
        String login = personToShow.getLogin();
        String name = personToShow.getName();
        String surname = personToShow.getSurname();
        int age = personToShow.getAge();
        String city = personToShow.getCity();
        String sex = personToShow.getSex();

        File fileLogo = new File("C:/Users/proto/IdeaProjects/OOP_Task_1/src/main/resources/com/example/oop_task_1/images/" + sex + "Ava.jpg");
        String localUrlLogo = fileLogo.toURI().toURL().toString();
        Image imageLogo = new Image(localUrlLogo);
        avatar.setImage(imageLogo);

        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append("@"+login+"\n");
        descriptionText.append(name + " " + surname + "\n");
        descriptionText.append("Возраст: " + age + "\n");
        descriptionText.append("Город: " + city + "\n");
        descriptionText.append("Хобби: ");

        List<String> interests = personToShow.getInterests();
        for (int i = 0; i < interests.size(); i++) {
            String interest = interests.get(i);
            descriptionText.append(interest);
            if (i == interests.size() - 1) {
                descriptionText.append(".");
            } else {
                descriptionText.append(", ");
            }
        }
        description.setText(descriptionText.toString());
    }
}
