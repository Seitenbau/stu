package com.seitenbau.testing.util;

/**
 * Abstraction of a common credential representation consisting of a id and a secret.
 */
public class Credentials
{
  String _id;

  String _secret;

  public Credentials()
  {
  }

  public Credentials(String id, String secret)
  {
    _id = id;
    _secret = secret;
  }

  /**
   * Sets the id element.
   * 
   * @param id value for the credential id
   * @return the actual {@link Credentials} instance
   */
  public Credentials id(String id)
  {
    this.setId(id);
    return this;
  }

  /**
   * Sets the secret element.
   * 
   * @param secret value for the credential secret
   * @return the actual {@link Credentials} instance
   */
  public Credentials secret(String secret)
  {
    this.setSecret(secret);
    return this;
  }

  public String getId()
  {
    return _id;
  }

  public void setId(String id)
  {
    _id = id;
  }

  public String getSecret()
  {
    return _secret;
  }

  public void setSecret(String secret)
  {
    _secret = secret;
  }

}
