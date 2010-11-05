/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;

/**
 *
 * @author Andrew
 */

public class StarEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private int x, y;

    public StarEntity(int x, int y) {
        super(x,y);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StarEntity)) {
            return false;
        }
        StarEntity other = (StarEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StarEntity[id=" + id + "]";
    }

}
