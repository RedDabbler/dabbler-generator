package dabbler.generator.sql.model;

import com.dabbler.generator.sql.model.where.WhereExpression;
import com.google.common.base.Joiner;
import lombok.Data;

import java.util.List;

@Data
public class Where {

    private List<WhereExpression> whereExpression;

    private String ALWAYS_TRUE_EXPRESSION = " 1 = 1";

    public String toString(){
        StringBuilder whereBuilder = new StringBuilder(SQLConstant.SQL_WHERE).append(ALWAYS_TRUE_EXPRESSION);
        Joiner.on(SQLConstant.SQL_AND).appendTo(whereBuilder,whereExpression);
        return  whereBuilder.toString();
    }



}
