package com.example.multifileverification.model;

public class FileAnalysis {

    private String fileId;
    private String filePath;
    private String analysisResult;

    public FileAnalysis(String fileId, String filePath) {
        this.fileId = fileId;
        this.filePath = filePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }
}
