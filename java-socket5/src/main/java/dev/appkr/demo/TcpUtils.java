package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.HEADERSIZE;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TcpUtils {

  static final int EOF = -1;

  public static boolean containsNewLine(byte[] haystack) {
    byte[] needle = new byte[]{10};
    for (int i = 0; i <= haystack.length - needle.length; i++) {
      int j = 0;
      while (j < needle.length && haystack[i + j] == needle[j]) {
        j++;
      }
      if (j == needle.length) {
        return true;
      }
    }
    return false;
  }

  public static byte[] trim(byte[] data) {
    int idx = 0;
    while (idx < data.length) {
      if (data[idx] == 0) {
        break;
      }
      idx++;
    }

    byte[] trimed;
    if (idx > 0 && idx < data.length) {
      trimed = Arrays.copyOfRange(data, 0, idx);
    } else {
      trimed = data;
    }

    return trimed;
  }

  public static byte[] leftPad(byte[] in) {
    String value = String.valueOf(in.length);
    final int padSize = HEADERSIZE - value.length();
    for (int i = 0; i < padSize; i++) {
      value = "0" + value;
    }

    return value.getBytes();
  }
}
