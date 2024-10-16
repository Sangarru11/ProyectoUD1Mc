package org.example.View;

public enum Scenes {
    MAIN("View/main.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
