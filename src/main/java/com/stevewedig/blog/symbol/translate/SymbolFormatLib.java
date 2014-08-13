package com.stevewedig.blog.symbol.translate;

import com.stevewedig.blog.symbol.SymbolMap;
import com.stevewedig.blog.translate.Format;
import com.stevewedig.blog.translate.FormatLib;
import com.stevewedig.blog.translate.ParseError;


public abstract class SymbolFormatLib {

  // ===========================================================================
  // translator
  // ===========================================================================

  public static SymbolParser.Builder parser() {
    return new SymbolTranslatorClass.ParserBuilder();
  }

  public static SymbolWriter.Builder writer() {
    return new SymbolTranslatorClass.WriterBuilder();
  }

  public static SymbolTranslator.Builder translator() {
    return new SymbolTranslatorClass.TranslatorBuilder();
  }

  // ===========================================================================
  // format
  // ===========================================================================

  public static SymbolFormat format(ConfigFormat configFormat, SymbolTranslator translator) {

    final Format<SymbolMap> symbolFormat = FormatLib.chain(configFormat, translator);

    // implement SymbolFormat "type alias" using the generic Format<SymbolMap>
    return new SymbolFormat() {
      @Override
      public SymbolMap parse(String fileContent) throws ParseError {
        return symbolFormat.parse(fileContent);
      }

      @Override
      public String write(SymbolMap symbolMap) {
        return symbolFormat.write(symbolMap);
      }
    };

  }
}
