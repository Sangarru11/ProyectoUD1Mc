package org.example.View;

public enum Scenes {
    AddUser("/org/example.View/add_user.fxml"),
    Login("/org/example.View/login_user.fxml"),
    AddContact("/org/example.View/AddContact.fxml"),
    Chat("/org/example.View/Chat.fxml"),
    ContactList("/org/example.View/ContactList.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
