package utils;

import java.util.Random;

public class VerificationCode {

    private static final char[] chars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
            'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
            'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
    private static Random random = new Random();

    /**
     * 生成注册验证码
     *
     * @return 6位验证码
     */
    public static String getVerificationCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(chars[random.nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成7位机构登陆码
     * 由于要求只有7位，无法保证生成的7位登陆码不重复
     * 所以会带来一定的问题
     *
     * @return 7位机构登陆码
     */
    public static String getInstitutionCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            stringBuilder.append(chars[random.nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

}
