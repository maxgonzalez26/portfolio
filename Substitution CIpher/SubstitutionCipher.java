import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public class SubstitutionCipher extends Cipher {
    private final char[] encryptionKey;
    private final char[] decryptionKey;

    public SubstitutionCipher(long key) {
        super(key);
        Random rand = new Random(key);
        encryptionKey = new char[256];
        decryptionKey = new char[256];
        for (int i = 0; i < 256; i++) {
            encryptionKey[i] = (char) i;
            decryptionKey[i] = (char) i;
        }
        for (int i = 0; i < 256; i++) {
            int j = rand.nextInt(256);
            char Char1 = encryptionKey[i];
            encryptionKey[i] = encryptionKey[j];
            encryptionKey[j] = Char1;
        }
        for (int i = 0; i < 256; i++) {
            char Char2 = encryptionKey[i];
            decryptionKey[Char2] = (char) i;
        }
    }

    public List<Character> encrypt(List<Character> cleartext) {
        List<Character> ciphertext = new LinkedList<>();
        for (char clearchar : cleartext) {
            char cipherchar = encryptionKey[clearchar];
            ciphertext.add(cipherchar);
        }
        return ciphertext;
    }

    public List<Character> decrypt(List<Character> ciphertext) {
        List<Character> cleartext = new LinkedList<>();
        for (char cipherchar : ciphertext) {
            char clearchar = decryptionKey[cipherchar];
            cleartext.add(clearchar);
        }
        return cleartext;
    }

	
}
