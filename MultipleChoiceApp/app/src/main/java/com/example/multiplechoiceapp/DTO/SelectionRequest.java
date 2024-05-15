package com.example.multiplechoiceapp.DTO;

public class SelectionRequest {
    private String selectionContent;

    public SelectionRequest() {
    }

    public SelectionRequest(String selectionContent) {
        this.selectionContent = selectionContent;
    }

    public String getSelectionContent() {
        return selectionContent;
    }

    public void setSelectionContent(String selectionContent) {
        this.selectionContent = selectionContent;
    }

    @Override
    public String toString() {
        return "SelectionRequest{" +
                "selectionContent='" + selectionContent + '\'' +
                '}';
    }
}
