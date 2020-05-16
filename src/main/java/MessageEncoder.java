import javax.crypto.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MessageEncoder {


    private static int cType;
    private static int bUserId;
    private static String msg;
    private static byte[] message;
    private static Cipher cipher;

    final String secretKey = "ssshhhhhhhhhhh!!!!11111";


    public MessageEncoder(int msgLen) {

        message = new byte[msgLen];
        cType = ByteBuffer.wrap(message, 0, 4).order(ByteOrder.BIG_ENDIAN).getInt();
        bUserId = ByteBuffer.wrap(message, 4, 4).order(ByteOrder.BIG_ENDIAN).getInt();
        msg = ByteBuffer.wrap(message, 8, msgLen - 8).order(ByteOrder.BIG_ENDIAN).toString();

        String encryptedMessage = AES.encrypt(msg, secretKey) ;
        String decryptedMessage = AES.decrypt(encryptedMessage, secretKey) ;


    }

//    public byte[] decodeMsg(byte[] codemsg) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {//decryption расшифрование
//        KeyGenerator kg = KeyGenerator.getInstance("AES/CBC/PKCS5Padding");
//
//        Key key =  kg.generateKey();// get / create symmetric encryption key
//        cipher.init(Cipher.DECRYPT_MODE, key);
//
//        return cipher.doFinal(codemsg);
//    }
//
//    public byte[] encodeMsg(byte[] plainmsg) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {//encryption шифрование
//
//        KeyGenerator kg = KeyGenerator.getInstance("AES/CBC/PKCS5Padding");
//        Key key = kg.generateKey(); // get / create symmetric encryption key
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//
//        return cipher.doFinal(plainmsg);
//    }

    public int getcType() {
        return cType;
    }

    public int getbUserId() {
        return bUserId;
    }

    public String getMsg() {
        return msg;
    }

    public byte[] getMessage() {
        return message;
    }


//    static final Key constructKey(byte[] encoding, String keyAlgorithm,
//                                  int keyType)
//            throws InvalidKeyException, NoSuchAlgorithmException {
//        Key result = null;
//        switch (keyType) {
//            case Cipher.SECRET_KEY:
//                result = ConstructKeys.constructSecretKey(encoding,
//                        keyAlgorithm);
//                break;
//            case Cipher.PRIVATE_KEY:
//                result = ConstructKeys.constructPrivateKey(encoding,
//                        keyAlgorithm);
//                break;
//            case Cipher.PUBLIC_KEY:
//                result = ConstructKeys.constructPublicKey(encoding,
//                        keyAlgorithm);
//                break;
//        }
//        return result;
//    }

}
