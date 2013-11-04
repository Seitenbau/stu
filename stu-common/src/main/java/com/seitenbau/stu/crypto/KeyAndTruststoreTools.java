package com.seitenbau.stu.crypto;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.seitenbau.stu.io.files.FileReference;
import com.seitenbau.stu.io.files.FileUriFactory;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;
import com.seitenbau.stu.util.Closure;

/**
 * Loading of an test-key/trust store.
 * 
 * tries to load the keystores from {@linkplain #defaultKeystores} and
 * the truststores from {@linkplain #defaultTruststores}.
 * 
 * E.g. a Test keystore can be in "/config/test/keystore.jks" If no
 * keystore was found, but a truststore, the trustore is also used as
 * keystore.
 */
public enum KeyAndTruststoreTools
{
  INSTANCE;

  private static Logger logger = TestLoggerFactory.get(KeyAndTruststoreTools.class);

  private static final String PASSWORD = "changeit";
  
  static final String[] defaultKeystores = {  //
    "classpath:/config/test/keystore.jks" //
    , "classpath:/config/crypto/test-keystore.jks"  //
    , "classpath:/test-keystore.jks"  //
    , "classpath:/config/crypto/keystore.jks" //
    , "classpath:/keystore.jks" //
  };

  static final String[] defaultTruststores = {  //
    "classpath:/config/test/truststore.jks" //
    , "classpath:/config/crypto/test-truststore.jks"  //
    , "classpath:/test-truststore.jks"  //
    , "classpath:/config/crypto/truststore.jks" //
    , "classpath:/truststore.jks" //
  };
  
  KeyStore loadedKeystore;

  public void loadTestStores(boolean requiresUnlimitedJCE)
  {
    if (requiresUnlimitedJCE && !JCEutil.isUnlimitedCryptography())
    {
      throw new IllegalStateException("There seems to be no JCE unlimited policy installed in your JRE");
    }
    loadTestStores();
  }
  
  public void loadTestStores()
  {
    if (loadedKeystore != null)
    {
      return;
    }
    if (!JCEutil.isUnlimitedCryptography())
    {
      logger.warn("There seems no JCE unlimited policy files installed");
    }

    KeyStore keystore;
    KeyStore truststore;
    try
    {
      keystore = loadKeystore(defaultKeystores);
      truststore = loadKeystore(defaultTruststores);
    }
    catch (KeyStoreException kse)
    {
      throw new RuntimeException("Error while loading key/truststore file", kse);
    }

    if (keystore == null)
    {
      keystore = truststore;
    }
    try
    {
      prepare(keystore, truststore);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Error while loading key/truststore", e);
    }
  }

  private void prepare(KeyStore keystore, KeyStore truststore) throws NoSuchAlgorithmException, KeyStoreException,
      UnrecoverableKeyException, KeyManagementException
  {
    TrustManagerFactory tmFactory = TrustManagerFactory.getInstance("PKIX");
    tmFactory.init((KeyStore) null);

    // Set up key manager factory to use our key store
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(keystore, PASSWORD.toCharArray());

    KeyManager[] km = kmf.getKeyManagers();
    tmFactory.init(truststore);
    TrustManager[] tm = tmFactory.getTrustManagers();

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(km, tm, null);

    SSLContext.setDefault(sslContext);
  }
  
  private KeyStore loadKeystore(String[] defaultFilesList) throws KeyStoreException
  {
    List<String> files2try = new ArrayList<String>();
    files2try.addAll(Arrays.asList(defaultFilesList));

    FileReference ref = FileUriFactory.getInstance().find(files2try);
    if (ref == null)
    {
      return null;
    }

    final KeyStore keystore = KeyStore.getInstance("JKS");
    try
    {
      ref.withInputStream(new Closure<Void, InputStream, Exception>()
      {
        public Void call(InputStream input) throws Exception
        {
          keystore.load(input, PASSWORD.toCharArray());
          return null;
        }
      });
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    return keystore;
  }
  
  public SSLContext getSSLContext()
  {
    try
    {
      return SSLContext.getDefault();
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException(e);
    }
  }
}
