import org.apache.commons.codec.DecoderException;

import java.nio.charset.StandardCharsets;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

public class Sender {
    private static byte freeClientID = 0;
    private final byte clientID;
    private User loggedUser;
    private static long freePacketID = 0;

    public Sender() {
        clientID = freeClientID++;
    }


    //return an encoded and encrypted packet
    public byte[] sendMessage(String messageText) {
        if (loggedUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
            String s = Encryptor.encrypt(messageText);
        try {
             return encodePacket(clientID, freePacketID++, loggedUser.getUserID(), s);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void setUser(User user) {
        this.loggedUser = user;
    }

    //return a ready-to-use packet
    private byte[] encodePacket(Byte bSrc, Long bPktId, Integer userID, String msg) throws DecoderException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X",0x13)); //magic
        sb.append(" ");
        sb.append(String.format("%02X" ,bSrc)); //client id
        sb.append(" ");

        sb.append(String.format("%016X" ,bPktId)); //packet id
        sb.append(" ");
        String hexMsg = encodeHexString(msg.getBytes(StandardCharsets.UTF_8));
        final int wLen = hexMsg.length()/2 + 8;
        sb.append(String.format("%08X", wLen)); //wLen
        sb.append(" ");

        try {
            sb.append(String.format("%04X",CRC16.check(decodeHex(sb.toString().replaceAll(" ", "")), 0, 14)));
        }catch (DecoderException e){
            System.out.println("Something went wrong inside encodePacket() logic.");
        }
        sb.append(" ");
        String cTypeString = String.format("%08X", 16);
        sb.append(cTypeString); //cType
        sb.append(" ");
        String userIdString = String.format("%08X", userID);
        sb.append(userIdString); //bUserId
        sb.append(" ");
        sb.append(hexMsg);
        sb.append(" ");
        byte[] msgBytes = decodeHex(cTypeString + userIdString+ hexMsg);
        sb.append(String.format("%04X",CRC16.check(msgBytes, 0, msgBytes.length)));

        System.out.println(sb.toString());

        return decodeHex(sb.toString().replaceAll(" ", ""));

    }
}
