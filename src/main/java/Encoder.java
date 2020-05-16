import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.binary.Hex.decodeHex;


public class Encoder {
    public static void main(String[] args) throws DecoderException {

        final String plainText = "My-plain-t";
        final byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        final String head = "13 00 0000101000000001 0000000A".replace(" ", "");

        final byte[] headBytes = decodeHex(head);
        short headerCrc = CRC16.check(headBytes, 0, 14);
        System.out.println(headerCrc);
        System.out.println(CRC16.check(plainBytes, 0, plainBytes.length));

        decodePacket(decodeHex((head + "4163" + Hex.encodeHexString(plainBytes) + "68FB")));

//        decode(new byte[]{
//                0x13,
//                0xB,
//                0x0, 0x0, 0x1, 0x1, 0x0, 0x41, 0x15, 0x00,
//                0x0, 0x0, 0 x0, 0x04,
//                0x0F, 0x06
//
//        });

    }

    public static byte[] encodePacket(String packet){
        return packet.getBytes();
    }

    public static void decodePacket(final byte[] inputPacket) {

        if (inputPacket[0] != 0x13) throw new IllegalArgumentException("Invalid magic byte");//bMagic


        final int clientID = inputPacket[1] & 0xFF;//bSrc
        System.out.println("Sender id: " + clientID);


        final long packetID = ByteBuffer.wrap(inputPacket, 2, 8).order(ByteOrder.BIG_ENDIAN).getLong();//bPktId
        System.out.println("Packet id: " + packetID);


        final int messageLength = ByteBuffer.wrap(inputPacket, 10, 4).order(ByteOrder.BIG_ENDIAN).getInt();//wLen
        System.out.println("Message length: " + messageLength);


        final short headerCRC = ByteBuffer.wrap(inputPacket, 14, 2).order(ByteOrder.BIG_ENDIAN).getShort();//wCrc16_1
        System.out.println("CRC16 header: " + headerCRC);
      //  byte[] headBytes = new byte[14];
      //  System.arraycopy(inputPacket, 0, headBytes, 0, 14);
        final short actualHeaderCrc = CRC16.check(inputPacket, 0, 14);

        if (headerCRC != actualHeaderCrc)
            throw new IllegalArgumentException("Invalid header CRC16, actual CRC16 for header = " + actualHeaderCrc + ", but was: " + headerCRC);



        MessageEncoder message = new MessageEncoder(messageLength);

        try {
            byte [] encodemsg = message.decodeMsg(message.getMessage());
            System.out.println(encodemsg);

            byte [] decodemsg = message.decodeMsg(encodemsg);


        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.arraycopy(inputPacket, 16, message, 0, messageLength);

        System.out.println("Input message: " + new String(message.getMessage(), StandardCharsets.UTF_8));


        final short messageCRC = ByteBuffer.wrap(inputPacket, 16 + messageLength, 2).order(ByteOrder.BIG_ENDIAN).getShort();//wCrc16_1
        System.out.println("CRC16 for message: " + messageCRC);

        final short actualMessageCrc = CRC16.check(message.getMessage(), 0, messageLength);

        if (messageCRC != actualMessageCrc)
            throw new IllegalArgumentException("Invalid message CRC16, actual CRC16 for message = " + actualMessageCrc + ", but was: " + messageCRC);


    }

    public static byte[] buildResponse() {
        final String response = "response from server";
        final byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        final byte[] header = new byte[]{
                0x13,
                0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10,
                0x0, 0x0, 0x0, (byte) bytes.length,

        };

        return ByteBuffer.allocate(14 + 2 + bytes.length + 2).put(header).putShort(CRC16.check(header, 0, header.length)).put(bytes).putShort(CRC16.check(bytes, 0, bytes.length)).array();
    }


}
