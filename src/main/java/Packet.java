public class Packet {

    private int bSrc;
    private Long bPktId;
    private int cType;
    private int bUserId;
    private String msg;


    public Packet(int bSrc, long bPktId, int cType, int bUserId, String msg) {

        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.cType = cType;
        this.bUserId = bUserId;
        this.msg = msg;


    }

    public String getMsg() {
        return msg;
    }

    public int getbSrc() {
        return bSrc;
    }

    public Long getbPktId() {
        return bPktId;
    }


    public int getcType() {
        return cType;
    }

    public int getbUserId() {
        return bUserId;
    }
}
