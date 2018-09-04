package model;

/**
 * Created by gede on 13/04/18.
 */
public class SCTPResponseMessage {

    private  long requestId;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Diameter getMessage() {
        return message;
    }

    public void setMessage(Diameter message) {
        this.message = message;
    }

    private Diameter message;

    public SCTPResponseMessage(long requestId,Diameter message){
      this.requestId = requestId;
      this.message = message;


    }

}
