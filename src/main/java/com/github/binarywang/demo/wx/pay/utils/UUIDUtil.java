package com.github.binarywang.demo.wx.pay.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * UUID工具
 */
public class UUIDUtil {

    private static final int UUID_LENGTH = 16;

    /**
     * 生成32位UUID，不含“-”符
     *
     * @return 32位的UUID字符串，小写不含“-”符
     */
    public static String generateUUID() {
        return UUIDUtil.generateOriginalUUID().replaceAll("-", "");
    }

    /**
     * 生成32位UUID，含“-”符
     *
     * @return 32位的UUID字符串，小写包含“-”符
     */
    public static String generateOriginalUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 根据字节数组构建UUID示例。弥补UUID的构造方法UUID(byte[] bytes)为私有的不便。
     *
     * @param bytes 16字节的数组
     * @return UUID实例，仅在参数bytes为null时，返回null
     * @throws IllegalArgumentException 字节数组长度必须是16字节，否则抛出非法参数异常
     */
    public static UUID getUUIDFromBytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length != UUID_LENGTH) {
            throw new IllegalArgumentException("bytes array length must be 16(32 bits).");
        }
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    /**
     * 从UUID中返回内部构成的bytes数组。
     *
     * @param uuid UUID实例
     * @return bytes数组
     */
    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[UUID_LENGTH]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

}
