import lombok.SneakyThrows;
import utils.CRC16;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Receiver {// клас що розбиває пакет на блоки меджик байт, айді користувача тощо...


    public void receiveMessage(byte[] msg) {
        decodePacketInfo(msg);
    }

    @SneakyThrows
    public void decodePacketInfo(final byte[] inputPacket) {


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
            throw new CRCHeaderException("Invalid header utils.CRC16, actual utils.CRC16 for header = " + actualHeaderCrc + ", but was: " + headerCRC);


        final short messageCRC = ByteBuffer.wrap(inputPacket, inputPacket.length - 2, 2).order(ByteOrder.BIG_ENDIAN).getShort();
        System.out.println("CRC16 message: " + messageCRC);
        final short actualMessageCrc = CRC16.check(inputPacket, 16, messageLength);
        if (messageCRC != actualMessageCrc)
            throw new CRCMessageException("Invalid message utils.CRC16, actual utils.CRC16 for message = " + actualMessageCrc + ", but was: " + messageCRC);

        System.out.println("Command code: " + ByteBuffer.wrap(inputPacket, 16, 4).order(ByteOrder.BIG_ENDIAN).getInt());

        System.out.println("User ID: " + ByteBuffer.wrap(inputPacket, 20, 4).order(ByteOrder.BIG_ENDIAN).getInt());

        String encyptedMsg = new String(Arrays.copyOfRange(inputPacket, 24, 16 + messageLength), StandardCharsets.UTF_8);
        System.out.println(encyptedMsg);
        System.out.println(Encryptor.decrypt(encyptedMsg));


    }

    public Packet getPacketFromArray(final byte[] inputPacket) {

        int bSrc = inputPacket[1] & 0xFF;//bSrc

        long bPktId = ByteBuffer.wrap(inputPacket, 2, 8).order(ByteOrder.BIG_ENDIAN).getLong(); //bPktId


        int cType = ByteBuffer.wrap(inputPacket, 16, 4).order(ByteOrder.BIG_ENDIAN).getInt();


        int bUserId = ByteBuffer.wrap(inputPacket, 20, 4).order(ByteOrder.BIG_ENDIAN).getInt();

        int messageLength = ByteBuffer.wrap(inputPacket, 10, 4).order(ByteOrder.BIG_ENDIAN).getInt();//wLen


        String msg = new String(Arrays.copyOfRange(inputPacket, 24, 16 + messageLength), StandardCharsets.UTF_8);

        return new Packet(bSrc, bPktId, cType, bUserId, msg);
    }

}
