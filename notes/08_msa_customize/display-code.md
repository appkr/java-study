## DisplayCode

```bash
$ jshell --class-path path/to/guava.jar

jshell> import com.google.common.base.*;

jshell> String displayCode = UUID.randomUUID().toString();
displayCode ==> "1136d829-c0a2-4a82-8f7e-5e2f99a193f2"

jshell> String[] splits = displayCode.split("-");
splits ==> String[5] { "1136d829", "c0a2", "4a82", "8f7e", "5e2f99a193f2" }

jshell> String prefix = splits[0];
prefix ==> "1136d829"

jshell> String suffix = splits.length >= 2 ? splits[1] : null;
suffix ==> "c0a2"

jshell> Strings.padStart(Long.toHexString(Long.parseLong(prefix, 16)), 4, '0') + Strings.padStart(Long.toHexString(Long.parseLong(suffix, 16)), 4, '0');
$6 ==> "1136d829c0a2"

# For copy and paste
import com.google.common.base.*;

String displayCode = UUID.randomUUID().toString();
String[] splits = displayCode.split("-");
String prefix = splits[0];
String suffix = splits.length >= 2 ? splits[1] : null;
Strings.padStart(Long.toHexString(Long.parseLong(prefix, 16)), 4, '0') + Strings.padStart(Long.toHexString(Long.parseLong(suffix, 16)), 4, '0');
```
