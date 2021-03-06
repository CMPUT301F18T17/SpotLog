package cs.ualberta.ca.medlog;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Random;

import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Encryption;

import static junit.framework.TestCase.*;

/**
 * <p>
 *     Description: <br>
 *         Test class for the Encryption helper.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see Encryption
 */
@RunWith(RobolectricTestRunner.class)
public class EncryptionTest {

    /**
     * Tests if a message is successfully encrypted and decrypted.
     */
    @Test
    public void testEncrypt(){
        try {
            String enc = Encryption.encryptData("roskewic", "TEST MESSAGE".getBytes());
            System.out.println(enc);
            byte[] dec = Encryption.decryptData("roskewic", enc);
            assertFalse("Messages are equal after encryption.", enc.equals("TEST MESSAGE"));
            assertNotSame("The encrypted and decrypted strings are the same!", enc, Encryption.byteArrayToString(dec));
            assertEquals("Decrypted message does not equal initial message.", "TEST MESSAGE", Encryption.byteArrayToString(dec));
        }catch(EncryptionException e){
            e.printStackTrace();
            fail("EncryptionException occurred.");
        }
    }

    /**
     * Tests if a Bitmap is successfully encrypted and decrypted.
     */
    @Test
    public void testBitmapEncrypt(){
        try{
            Bitmap test = generateBitmap(100, 100);
            String enc = Encryption.encryptData("roskewic", Encryption.bitMaptoBytes(test));
            System.out.println(enc);
            byte[] dec = Encryption.decryptData("roskewic", enc);
            assertTrue("Decrypted data bytes are not equal.", Arrays.equals(dec, Encryption.bitMaptoBytes(test)));
            Bitmap out = Encryption.byteArrayToBitmap(dec);
            // The bitmap encryption is lossy, i.e. some data is lost in compression.
            // Therefor, I am unable to test if the encryption is successful without a bitmap viewer.
        }catch(EncryptionException e){
            e.printStackTrace();
            fail("EncryptionException occurred.");
        }

    }

    /**
     * Generates a Bitmap for encryption testing.
     */
    private Bitmap generateBitmap(int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, false);
        Random r = new Random();
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bitmap.setPixel(j, i, Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            }
        }
        return bitmap;
    }
}
