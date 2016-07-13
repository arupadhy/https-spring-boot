package com.app.fb;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {

	/*
	 * C:\security>keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize
 2048 -keystore keystore.p12 -validity 3650
 
 display properties of the current keystore
 C:\security>keytool -list -v -keystore keystore.p12 -storetype pkcs12
	 */
	
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		
		String pathOfKeyStoreFile = "C:\\security\\keystore.p12";
		String keyStorePass = "Sap!ent@";
		String keyStoreType = "PKCS12";
		String keyStoreProvider = "SunJSSE";
		String keyStoreAlias = "tomcat";
		
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.addConnectorCustomizers((TomcatConnectorCustomizer)(Connector con) -> {
	        con.setScheme("https");
	        con.setSecure(true);
	        Http11NioProtocol proto = (Http11NioProtocol) con.getProtocolHandler();
	        proto.setSSLEnabled(true);
	        proto.setKeystoreFile(pathOfKeyStoreFile);
	        proto.setKeystorePass(keyStorePass);
	        proto.setKeystoreType(keyStoreType);
	        proto.setProperty("keystoreProvider", keyStoreProvider);
	        proto.setKeyAlias(keyStoreAlias);
	    });
	    
	    return factory;
	}
}
