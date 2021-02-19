package dev.dankom.re;

public class RefectionEncryptor {
    private final String text;
    private final String extension;

    public RefectionEncryptor(String text, String extension) {
        this.text = text;
        this.extension = extension;
    }

    public int encryptI() {
        return Integer.parseInt(encryptS());
    }

    public String encryptS() {
        return ReflectionEncryptorUtil.encrypt(text + "|" + extension);
    }
}
