import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * Unit test for simple Encoder.
 */
public class EncoderTest {

    Receiver r = new Receiver();

    //тест на виліт ексепшенів при ситуаціях коли MagicByte введено некоректно або коли
    @Test(expected = WrongH13Exception.class)
    public void ShouldThrowException_When_InvalidH13() throws DecoderException{
        r.decodePacket(Hex.decodeHex("10"));
    }

    @Test(expected = CRCHeaderException.class)
    public void ShouldThrowException_When_InvalidCRCHeader() throws DecoderException {
        r.decodePacket(Hex.decodeHex("13 00 0000100000000001 0000000A 4163"));
    }

    @Test(expected = CRCMessageException.class)
    public void ShouldThrowException_When_InvalidCRCMessage() throws DecoderException {

       final String text = "my plain text";
       final byte[] plainTextToBytes =text.getBytes(StandardCharsets.UTF_8);
       r.decodePacket(Hex.decodeHex(("13 00 0000000000000001 00000015 49DC" + "0000000A 00000001" + Hex.encodeHexString(plainTextToBytes)+ "1F27").replace(" ", "")));



        r.decodePacket(Hex.decodeHex("13 00 0000100000000001 0000000A 41AD"));
    }


//тест на правильність виконання
    @Test
    public void ShouldPass_When_ValidPacket() throws DecoderException {


        final String plainText = "My plain t ";
        final byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        r.decodePacket(Hex.decodeHex(("13 00 0000101000000001 0000000A 4163 " + Hex.encodeHexString(plainBytes) + "0B3F").replace(" ", "")));

    }


//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public EncoderTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( EncoderTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
    // extends TestCase
}
