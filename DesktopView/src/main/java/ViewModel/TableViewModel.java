package ViewModel;

public class TableViewModel {
    private String fileName;

    private String fileLocation;

    public TableViewModel(String fileName, String fileLocation) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
