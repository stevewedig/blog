package com.stevewedig.blog.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.errors.Bug;
import com.stevewedig.blog.symbol.translate.ConfigFormat;
import com.stevewedig.blog.translate.ParseError;

/**
 * Properties manipulation utilities, not compatible with GWT.
 */
public abstract class PropLib {

  public static ConfigFormat format = new ConfigFormat() {

    @Override
    public Map<String, String> parse(String fileContent) throws ParseError {

      // http://stackoverflow.com/questions/782178/how-do-i-convert-a-string-to-an-inputstream-in-java

      InputStream input = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

      Properties props = new Properties();
      try {
        props.load(input);
      } catch (IOException e) {
        throw new ParseError(e);
      }

      ImmutableMap.Builder<String, String> map = ImmutableMap.builder();
      for (Object _key : props.keySet()) {

        String key = (String) _key;

        String value = props.getProperty(key);

        map.put(key, value);
      }

      return map.build();
    }

    @Override
    public String write(Map<String, String> map) {

      Properties props = new Properties();

      props.putAll(map);

      // http://stackoverflow.com/questions/216894/get-an-outputstream-into-a-string
      ByteArrayOutputStream output = new ByteArrayOutputStream();

      try {
        props.store(output, null);

        return output.toString("UTF-8");

      } catch (IOException e) {
        e.printStackTrace();
        throw new Bug("could not write properties");
      }

    }
  };

}
