/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.utilisateurs;

/**
 *
 * @author Abdelkader
 */
public class Badge {
    
    int idBadge;
    private String level;

    public Badge() {
    }

    public Badge(int idBadge, String level) {
        this.idBadge = idBadge;
        this.level = level;
    }

    public int getIdBadge() {
        return idBadge;
    }

    public void setIdBadge(int idBadge) {
        this.idBadge = idBadge;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Badge{" + "idBade=" + idBadge + ", level=" + level + '}';
    }

     
    
    
    
    
    
}
