

public class Encryptor {

    private static final String secretKey = "ssshhhhhhhhhhh!!!!"; //клас що енкріптить пакет що надійшов

    public static String encrypt(String str) {
        return AES.encrypt(str, secretKey);
    }

    public static String decrypt(String str) {
        return AES.decrypt(str, secretKey);
    }
}
