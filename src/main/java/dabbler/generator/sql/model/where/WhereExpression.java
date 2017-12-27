package dabbler.generator.sql.model.where;

import com.dabbler.common.utils.ConvertUtils;
import com.dabbler.generator.sql.model.OperatorEnum;
import com.dabbler.generator.sql.model.SQLConstant;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class WhereExpression<T> {

    private String dbTableNameCond; // 等式左边
    private String dbFieldNameCond;
    private OperatorEnum operatorEnum;


    protected String getLeftOfOperator(){
        return dbTableNameCond + SQLConstant.SEPARATOR_DOT + dbFieldNameCond;
    }

    public WhereExpression getInstance(T ... value){
        List<T> values = Lists.newArrayList(value);
        if (operatorEnum == OperatorEnum.BETWEEN_AND){
            return new BetweenWhereCondExpression(values.get(0),values.get(1));
        } else if (isSingleValueExpression(operatorEnum)) {
            return new SingleValueCondExpression(values.get(0));
        } else if (operatorEnum == OperatorEnum.IN || operatorEnum == OperatorEnum.NOT_IN) {
            return new MultiValueCondExpression(values);
        } else if (operatorEnum == OperatorEnum.IS_NOT_NULL || operatorEnum == OperatorEnum.IS_NULL) {
            return new NoneValueCondExpression();
        }
        throw new UnsupportedOperationException();
    }

    private boolean isSingleValueExpression(OperatorEnum operatorEnum){
        return operatorEnum == OperatorEnum.EQUAL || operatorEnum == OperatorEnum.NOT_EQUAL
                || operatorEnum == OperatorEnum.LOWER_EQUALTHAN || operatorEnum == OperatorEnum.LOWER_THAN
                || operatorEnum == OperatorEnum.GREATER_EQUAL_THAN || operatorEnum == OperatorEnum.GREATER_THAN
                || operatorEnum == OperatorEnum.CONTAIN || operatorEnum == OperatorEnum.NOT_CONTAIN ;
    }

    public String toString(){
        return generate();
    }

    protected abstract String generate();

    @AllArgsConstructor
    @Data
    class BetweenWhereCondExpression extends WhereExpression {

        private T startValue;
        private T endValue;

        @Override
        protected String generate() {
            return new StringBuilder(super.getDbTableNameCond()).append(SQLConstant.SEPARATOR_DOT).append(super.getDbFieldNameCond())
                    .append(SQLConstant.SQL_BETWEEN).append(startValue).append(SQLConstant.SQL_AND).append(endValue).toString();
        }
    }

    @AllArgsConstructor
    @Data
    class SingleValueCondExpression extends WhereExpression {

        private T value;

        @Override
        protected String generate() {
            return super.getLeftOfOperator() + operatorEnum.getOperator() + this.value;
        }
    }

    @Data
    class NoneValueCondExpression extends WhereExpression {

        @Override
        protected String generate() {
            return super.getLeftOfOperator() + operatorEnum.getOperator() ;
        }
    }


    @Data
    @AllArgsConstructor
    class MultiValueCondExpression extends WhereExpression {

        private List<T> values;
        @Override
        protected String generate() {
            return super.getLeftOfOperator() + operatorEnum.getOperator() + ConvertUtils.convertListToStr(values);
        }
    }

}
