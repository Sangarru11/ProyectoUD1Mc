package org.example.View;

public enum Scenes {
    MAIN("/org/example.View/add_user.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
