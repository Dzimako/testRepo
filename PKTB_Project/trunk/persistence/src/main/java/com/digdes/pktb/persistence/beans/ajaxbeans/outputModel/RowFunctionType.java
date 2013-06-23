package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 21.11.12
 * Time: 18:16
 */
public enum RowFunctionType {
    TOTAL_BY_UNIQUE("TOTAL_BY_UNIQUE"),
    MAKE_DYNAMIC_HEADERS("DYNAMIC_HEADERS"),
    MAKE_REPORT_WITH_UNIQUE_HEADERS("UNIQUE_HEADERS_REPORT");

        private String rowFunctionType;

        private RowFunctionType(String rowFunctionType) {
            this.rowFunctionType = rowFunctionType;
        }

        public String getRowFunctionType() {
            return rowFunctionType;
        }

        static public RowFunctionType getType(String opType) {
                for (RowFunctionType type: RowFunctionType.values()) {
                    if (type.getRowFunctionType().equalsIgnoreCase(opType)) {
                        return type;
                    }
                }
//            Thread thread = new Thread();
                throw new RuntimeException("unknown type");
            }
}
