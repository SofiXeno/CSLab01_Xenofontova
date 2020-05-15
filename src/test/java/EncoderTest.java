

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * Unit test for simple Encoder.
 */
public class EncoderTest {

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowException_When_InvalidPacket() {


    }



    //написать ещё 2 теста со своими собственными ексепшенами для срс1 хедера и срс2 сообщения

    @Test
    public void ShouldPass_When_ValidPacket() throws DecoderException {


        final String plainText = "My plain t ";
        final byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        Encoder.decodePacket(Hex.decodeHex(("13 00 0000101000000001 0000000A 4163 " + Hex.encodeHexString(plainBytes) + "0B3F").replace(" ", "")));

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
