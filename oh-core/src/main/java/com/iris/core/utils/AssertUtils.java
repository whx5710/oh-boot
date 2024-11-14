package com.iris.core.utils;

import com.iris.core.constant.Constant;
import com.iris.core.exception.ServerException;

/**
 * 校验工具类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class AssertUtils {

    public static void isBlank(String str, String variable) {
        if (str == null || str.isEmpty()) {
            throw new ServerException(variable + "不能为空");
        }
    }

    public static void isNull(Object object, String variable) {
        if (object == null) {
            throw new ServerException(variable + "不能为空");
        }
    }

    public static void isArrayEmpty(Object[] array, String variable) {
        if(array == null || array.length == 0){
            throw new ServerException(variable + "不能为空");
        }
    }

    //定义特殊字符
    public static String SPECIAL_CHAR = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    /**
     * 密码强度校验
     * @param grade 强度等级
     *              1、弱密码；必须包含字母
     *              2、中等 ；必须包含字母、数字
     *              3、复杂密码；必须包含大小写字母、数字、特殊字符
     * @param password 密码值
     */
    public static boolean passwordStrength(int grade, String password){
        if(password == null){
            throw new ServerException("密码不能为空");
        }
        if(password.length() < Constant.PASSWORD_MIN_LENGTH){
            throw new ServerException("密码长度不满足要求，至少" + Constant.PASSWORD_MIN_LENGTH + "位长度");
        }
        if(password.length() > Constant.PASSWORD_MAX_LENGTH){
            throw new ServerException("密码长度不满足要求，最多" + Constant.PASSWORD_MAX_LENGTH + "位长度");
        }
        return switch (grade) {
            case 1 -> checkContainCase(password);
            case 2 -> checkContainCase(password) && checkContainDigit(password);
            default ->
                    checkContainLowerCase(password) && checkContainDigit(password) && checkContainUpperCase(password) && checkContainSpecialChar(password);
        };
    }

    /**
     * 检测密码中是否包含数字
     * @param password 密码
     * @return 包含数字 返回true
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (Character.isDigit(pass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测密码中是否包含字母（不区分大小写）
     * @param password 密码
     * @return 包含字母 返回true
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (Character.isLetter(pass)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 检测密码中是否包含小写字母
     * @param password 密码字符串
     * @return 包含小写字母 返回true
     */
    public static boolean checkContainLowerCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (Character.isLowerCase(pass)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 检测密码中是否包含大写字母
     * @param password 密码
     * @return 包含大写字母 返回true
     */
    public static boolean checkContainUpperCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (Character.isUpperCase(pass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测密码中是否包含特殊符号
     * @param password 密码字符串
     * @return 包含特殊符号 返回true
     */
    public static boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (SPECIAL_CHAR.indexOf(pass) != -1) {
                return true;
            }
        }
        return false;
    }
}