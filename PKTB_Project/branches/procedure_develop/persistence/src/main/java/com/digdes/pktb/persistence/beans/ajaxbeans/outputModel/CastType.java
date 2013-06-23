package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 15:30
 */
public enum CastType {
    INTEGER("int");

    private String castType;

    private CastType(String castType) {
        this.castType = castType;
    }

    public String getCastType() {
        return castType;
    }

    static public CastType getType(String castType) {
            for (CastType type: CastType.values()) {
                if (type.getCastType().equalsIgnoreCase(castType)) {
                    return type;
                }
            }
        throw new IllegalArgumentException("Illegal type of CastType");
    }
}
