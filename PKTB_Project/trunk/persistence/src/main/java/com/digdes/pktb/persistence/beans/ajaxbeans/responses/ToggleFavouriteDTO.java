package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

/**
 * Created with IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 07.11.12
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class ToggleFavouriteDTO {

    boolean favourited = true;

    public ToggleFavouriteDTO(boolean favourited) {
        this.favourited = favourited;
    }

    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }
}
