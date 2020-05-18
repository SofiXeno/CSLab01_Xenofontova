import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Receiver {

    public void receiveMessage(byte[] msg){
        decodePacket(msg);
    }

    @SneakyThrows
    public void decodePacket(final byte[] inputPacket) {


        if (inputPacket[0] != 0x13) throw new WrongH13Exception("Invalid magic byte");//bMagic


        final int clientID = inputPacket[1] & 0xFF;//bSrc
        System.out.println("Sender id: " + clientID);


        final long packetID = ByteBuffer.wrap(inputPacket, 2, 8).order(ByteOrder.BIG_ENDIAN).getLong();//bPktId
        System.out.println("Packet id: " + packetID);


        final int messageLength = ByteBuffer.wrap(inputPacket, 10, 4).order(ByteOrder.BIG_ENDIAN).getInt();//wLen
        System.out.println("Message length: " + messageLength);


        final short headerCRC = ByteBuffer.wrap(inputPacket, 14, 2).order(ByteOrder.BIG_ENDIAN).getShort();//wCrc16_1
        System.out.println("CRC16 header: " + headerCRC);
        final short actualHeaderCrc = CRC16.check(inputPacket, 0, 14);

        if (headerCRC != actualHeaderCrc)
            throw new CRCHeaderException("Invalid header CRC16, actual CRC16 for header = " + actualHeaderCrc + ", but was: " + headerCRC);


        final short messageCRC = ByteBuffer.wrap(inputPacket, inputPacket.length - 2, 2).order(ByteOrder.BIG_ENDIAN).getShort();
        System.out.println("CRC16 message: " + messageCRC);
        final short actualMessageCrc = CRC16.check(inputPacket, 16, messageLength);
        if (messageCRC != actualMessageCrc)
            throw new CRCMessageException("Invalid message CRC16, actual CRC16 for message = " + actualMessageCrc + ", but was: " + messageCRC);

        System.out.println("Command code: " + ByteBuffer.wrap(inputPacket, 16, 4).order(ByteOrder.BIG_ENDIAN).getInt());

        System.out.println("User ID: " + ByteBuffer.wrap(inputPacket, 20, 4).order(ByteOrder.BIG_ENDIAN).getInt());

        String encyptedMsg = new String(Arrays.copyOfRange(inputPacket, 24, 16+messageLength), StandardCharsets.UTF_8);
        System.out.println(encyptedMsg);
        System.out.println(Encryptor.decrypt(encyptedMsg));


    }
}
