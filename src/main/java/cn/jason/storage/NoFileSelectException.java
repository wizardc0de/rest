package cn.jason.storage;

public class NoFileSelectException extends StorageException {
    public NoFileSelectException(String message) {
        super(message);
    }

    public NoFileSelectException(String message, Throwable cause) {
        super(message, cause);
    }
}
