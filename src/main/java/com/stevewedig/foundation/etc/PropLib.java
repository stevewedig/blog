package com.stevewedig.foundation.etc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropLib {

  // http://www.mkyong.com/java/java-getresourceasstream-in-static-method/
  public static Properties loadResource(Class<?> klass, String resourcePath) {
    Properties props = new Properties();
    
    InputStream is = klass.getClassLoader().getResourceAsStream(resourcePath);
    
//    File f = new File("/lingo/lscode/java/console-jpa/src/main/resources/META-INF/mysql.properties");
//    InputStream is;
//    try {
//      is = new FileInputStream(f);
//    } catch (FileNotFoundException e1) {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//      throw new RuntimeException("fail");
//    }
    
    try {
      props.load(is);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("could not load DbManager properties file");
    }
    
    return props;
  }
}
