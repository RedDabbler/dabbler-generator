package dabbler.common.utils;

import com.google.common.base.Joiner;

import java.util.List;

public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> String convertListToStr(List<T> values){
        if (values==null){
            return "''";
        }
        StringBuilder stringBuilder = new StringBuilder("'");
        Joiner.on("', '").skipNulls().appendTo(stringBuilder,values);
        stringBuilder.append("'");

        return stringBuilder.toString();
    }
}
