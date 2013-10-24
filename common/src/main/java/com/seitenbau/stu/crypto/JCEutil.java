package com.seitenbau.stu.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

/**
 * Helpers for JCE ( Java Cryptography Extensions )
 */
public class JCEutil
{
  /**
   * @return true new JCE unlimited extensions installiert sind
   * 
   * @throws NoSuchAlgorithmException
   */
  public static boolean isUnlimitedCryptography()
  {
    try
    {
      boolean isJCEunlimited = !(Cipher.getMaxAllowedKeyLength("RC5") < 256);
      return isJCEunlimited;
    }
    catch (NoSuchAlgorithmException e)
    {
      return false;
    }
  }
}
