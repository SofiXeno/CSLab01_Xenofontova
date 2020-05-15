//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.charset.StandardCharsets;
//
//public class PacketBuilder {
//
//
//
//    public static void decodePacket(final byte[] inputPacket) {
//
//
//        if (inputPacket[0] != 0x13) throw new IllegalArgumentException("Invalid magic byte");//bMagic
//
//
//        final int clientID = inputPacket[1] & 0xFF;//bSrc
//        System.out.println("Sender id: " + clientID);
//
//
//        final long packetID = ByteBuffer.wrap(inputPacket, 2, 8).order(ByteOrder.BIG_ENDIAN).getLong();//bPktId
//        System.out.println("Packet id: " + packetID);
//
//
//        final int messageLength = ByteBuffer.wrap(inputPacket, 10, 4).order(ByteOrder.BIG_ENDIAN).getInt();//wLen
//        System.out.println("Message length: " + messageLength);
//
//
//        final short headerCRC = ByteBuffer.wrap(inputPacket, 14, 2).order(ByteOrder.BIG_ENDIAN).getShort();//wCrc16_1
//        System.out.println("CRC16 header: " + headerCRC);
//
//        final short actualHeaderCrc = CRC16.check(inputPacket, 0, 14);
//
//        if (headerCRC != actualHeaderCrc)
//            throw new IllegalArgumentException("Invalid header CRC16, actual CRC16 for header = " + actualHeaderCrc + ", but was: " + headerCRC);
//
//
//
//        byte[] message = new byte[messageLength];//розбити розпарсити на частини самостійно
//
//        final int bigEndianCode = ByteBuffer.wrap(message,0,4).order(ByteOrder.BIG_ENDIAN).getInt();
//
//        final int currentUserID = ByteBuffer.wrap(message,4,4).order(ByteOrder.BIG_ENDIAN).getInt();
//
//        // final String msg = ByteBuffer.wrap(message,8,4).order(ByteOrder.BIG_ENDIAN).getString();//???????
//
//
//
//
//        System.arraycopy(inputPacket, 16, message, 0, messageLength);
//
//        System.out.println("Input message: " + new String(message, StandardCharsets.UTF_8));
//
//
//        final short messageCRC = ByteBuffer.wrap(inputPacket, 16 + messageLength, 2).order(ByteOrder.BIG_ENDIAN).getShort();//wCrc16_1
//        System.out.println("CRC16 for message: " + messageCRC);
//
//        final short actualMessageCrc = CRC16.check(message, 0, messageLength);
//
//        if (messageCRC != actualMessageCrc)
//            throw new IllegalArgumentException("Invalid message CRC16, actual CRC16 for message = " + actualMessageCrc + ", but was: " + messageCRC);
//
//
//    }
//
//
//
//
//
//
//
//
//
//}
