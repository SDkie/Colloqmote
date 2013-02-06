


import java.util.*;


public class StringToks
{
  private static String str;    // string to be tokenized
  private static int index;     // current position in the string


  public static String[] parseTokens(String s)
  
  {
    str = s;
    index = 0;
    ArrayList<String> argsList = new ArrayList<String>();
    String arg;

    
    int tokCount = 0;
    while (index != str.length()) {
      skipWhite();
      if (index == str.length())
        break;

      // Determine if a quoted argument
      char c = str.charAt(index);
      if (c == '"')
        arg = readQuotedString();
      else
        arg = readString();
      argsList.add(arg);
      tokCount++;
    }
        
    
    String[] args = new String[tokCount];
    for (int i = 0; i < tokCount; i++)
      args[i] = argsList.get(i);
    return args;
  }  // end of parseTokens()



  private static void skipWhite()
 
  {
    while (index != str.length()) {
      if (Character.isWhitespace(str.charAt(index)) == false)
        break;
      index++;
    }
  }  // end of skipWhite()




  private static String readQuotedString()

  {
    StringBuilder buffer = new StringBuilder();
    index++;       // Skip initial quote

    
  loop:
    while (index != str.length()) {
      char c = str.charAt(index);
      switch (c) {
        case '"':
          index++;
          break loop;

        case '\\':
          if (index < (str.length() - 1))
            index++;
          c = str.charAt(index);
          switch (c) {
            case 't':
              c = '\t';
              break;
            case 'r':
              c = '\r';
              break;
            case 'n':
              c = '\n';
              break;
          }
          buffer.append(c);
          break;

        default:
          buffer.append(c);
          break;
      }
      index++;
    }
        
    return buffer.toString();    // return as a string
  }  // end of readQuotedString()



  private static String readString()
  // Read until the next white space
  {
    int start = index;
    while (index != str.length()) {
      if (Character.isWhitespace(str.charAt(index)))
        break;
      index++;
    }
    return str.substring(start, index);
  }  // end of readString()


}  