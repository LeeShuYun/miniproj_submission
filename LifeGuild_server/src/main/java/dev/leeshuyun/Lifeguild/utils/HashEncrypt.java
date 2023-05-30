package dev.leeshuyun.Lifeguild.utils;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashEncrypt {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${hash.api.key}")
    private String publicApiKey;

    @Value("${hash.priv.key}")
    private String apiKey;

    // gets hash with time
    public String[] getMd5Hash(String publicApiKey, String apiKey) {
        String[] result = new String[2];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long tsVal = timestamp.getTime();
        String hashVal = tsVal + apiKey + publicApiKey;
        result[0] = tsVal + "";
        result[1] = DigestUtils.md5Hex(hashVal);

        logger.info("getMd5Hash: hash=%s".formatted(result[1]));
        return result;
    }

    // TODO... need test. seems cleaner
    public String altGetHashSha256(String originalString) {
        String result = DigestUtils.sha256Hex(originalString);
        logger.info("alternate getMd5Hash: hash=%s".formatted(result));
        return result;
    }

    public String getHashSha256(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));

        String result = bytesToHex(encodedHash);
        logger.info("alternate getHashSha256: hash=%s".formatted(result));
        return result;
    }

    private static String bytesToHex(byte[] encodedHash) {
        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (int i = 0; i < encodedHash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedHash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
