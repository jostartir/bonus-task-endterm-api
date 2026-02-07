package kz.aitu.endtermapi.exception.handler;

public class ApiError {
    public String timestamp;
    public int status;
    public String message;
    public String path;

    public ApiError(String timestamp,int status,String message,String path){
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }
}


