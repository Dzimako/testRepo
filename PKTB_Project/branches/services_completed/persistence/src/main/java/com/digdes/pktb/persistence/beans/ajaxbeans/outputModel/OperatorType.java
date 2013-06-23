package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 15:30
 */
public enum OperatorType {
    ADD_INTEGERS("+"),
    SUBTRACTION_INTEGERS("-"),
    MULTIPLY_INTEGERS("*"),
    DIVIDE_INTEGERS("/"),
    SIMPLE_COLUMN("COLUMN"),
    ADD_STRINGS("CONCATENATE"),
    ITERATION("ITERATION"),
    CHECK_ON_DEFAULT_VALUE("CHECK_ON_DEFAULT_VALUE"),
    IF_THEN_ELSE("IF_THEN_ELSE"),
    DICTIONARY("DICTIONARY"),
    TRANSFORM_TIME_FROM_DATE("TRANSFORM_TIME_FROM_DATE"),
    BOOLEAN_OR("||"),
    BOOLEAN_AND("&&"),
    BOOLEAN_NOT("!"),
    BOOLEAN_EQUAL("=="),
    BOOLEAN_LESS("<"),
    BOOLEAN_LESS_OR_EQUAL("<="),
    BOOLEAN_GREATER(">"),
    BOOLEAN_GREATER_OR_EQUAL(">="),
    ADD_PREVIOUS_VALUES("ADD_PREVIOUS");

    private String operatorType;

    private OperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorType() {
        return operatorType;
    }

    static public OperatorType getType(String opType) {
            for (OperatorType type: OperatorType.values()) {
                if (type.getOperatorType().equalsIgnoreCase(opType)) {
                    return type;
                }
            }
        throw new IllegalArgumentException("Illegal type of OperatorType");
    }
}
