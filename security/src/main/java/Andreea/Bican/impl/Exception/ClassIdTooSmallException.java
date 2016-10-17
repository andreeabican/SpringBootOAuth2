package Andreea.Bican.impl.Exception;

/**
 * Created by andre on 18.10.2016.
 */
public class ClassIdTooSmallException extends Throwable {

    private String id;

    public ClassIdTooSmallException(String id) {
        super();
        this.id = id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
