package com.seitenbau.testing.util;

import static java.lang.Character.isJavaIdentifierPart;
import static java.lang.Character.isJavaIdentifierStart;

/**
 * Functions to check validity of package and Java identifier names.
 */
public final class JavaNameValidator
{

  /**
   * Check if the given string is a valid java identifier.
   * @param name java identifier to check
   * @return {@code true} if valid
   */
  public static boolean isValidIdentifier(String name)
  {
    if (name == null || name.trim().length() == 0)
    {
      return false;
    }

    char[] chars = name.toCharArray();
    if (!isJavaIdentifierStart(chars[0]))
    {
      return false;
    }

    for (int i = 1; i < chars.length; i++)
    {
      if (!isJavaIdentifierPart(chars[i]))
      {
        return false;
      }
    }

    return true;
  }

  /**
   * Check if a package name (e.g. "org.apache") is valid.
   * @param pkg string representing a package
   * @return {@code true} if valid
   */
  public static boolean isValidPackageName(String pkg)
  {
    if (pkg == null)
    {
      return false;
    }

    if (pkg.trim().length() == 0)
    {
      return true;
    }

    // split using dot as delimiter and check each sub-package:
    int start = 0;
    int end = pkg.indexOf('.');

    while (start < end)
    {
      String subPackage = pkg.substring(start, end);
      if (!isValidIdentifier(subPackage))
      {
        return false;
      }
      start = end + 1;
      end = pkg.indexOf('.', start);
    }

    return isValidIdentifier(pkg.substring(start));
  }

}
