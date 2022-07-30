package me.christian.bankportal.server.utility;

import me.christian.bankportal.server.BankPortal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Cryptography {

    /**
     * The public access method to hash a password using the {@link Cryptography} class.
     * Takes in a char array containing the raw password to hash this mutable array.
     * Char Array rather than String for neater usage and harder memory accessibility.
     *
     * @param pwd A character array taking password in letter by letter.
     * @return A String containing the hashed, computationally expensive (to decrypt) password.
     */
    public static String encrypt(char[] pwd) {
        try {
            return new Cryptography().enc(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    private String enc(char[] pwd) throws NoSuchAlgorithmException {
        String enc = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(toBytes(pwd)));
        BankPortal.log("enc: %s, %s", Arrays.toString(pwd), enc);
        return enc;
    }

    private byte[] toBytes(char[] chars) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars));
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

}
