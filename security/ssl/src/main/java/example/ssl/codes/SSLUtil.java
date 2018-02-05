package example.ssl.codes;

public class SSLUtil {
	public static final String SSL_PATH = SSLUtil.class.getResource("/")
			.toString().substring(6);
	public static final String SSL_KEY_STORE = "javax.net.ssl.keyStore";
	public static final String SSL_KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
	public static final String SSL_TRUST_STORE = "javax.net.ssl.trustStore";
	public static final String SSL_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";

	public static void setServerProperty() {
		System.setProperty(SSL_KEY_STORE, SSL_PATH + "sslserverkeys");
		System.setProperty(SSL_KEY_STORE_PASSWORD, "123456");
		System.setProperty(SSL_TRUST_STORE, SSL_PATH + "sslservertrust");
		System.setProperty(SSL_TRUST_STORE_PASSWORD, "123456");
	}

	public static void setClientProperty() {
		System.setProperty(SSL_KEY_STORE, SSL_PATH + "sslclientkeys");
		System.setProperty(SSL_KEY_STORE_PASSWORD, "123456");
		System.setProperty(SSL_TRUST_STORE, SSL_PATH + "sslclienttrust");
		System.setProperty(SSL_TRUST_STORE_PASSWORD, "123456");
	}
}
