/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.utilisateurs;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author SLIMEN
 */
public class offre_experience {
    private int id;
    private int ref_compte;
    private String nom;
    private String description;
    private String url_image;
    private String addrese;
    private int avis_id;
    private int region_id;
    private int rating;
    private int catid;
    private int nbravis;
    private int nbrjaime;
    private Date datecreation;
    private Boolean recherche;
        private Boolean administration;
    private Boolean theme;
    private Boolean vitesse;
    private Boolean actualite;
    private Boolean originalite;
    private Boolean attractivite;
    private Boolean security;
    private Boolean experience;
    private Boolean control;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getRecherche() {
        return recherche;
    }

    public void setRecherche(Boolean recherche) {
        this.recherche = recherche;
    }

    public int getRef_compte() {
        return ref_compte;
    }

    public void setRef_compte(int ref_compte) {
        this.ref_compte = ref_compte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getAddrese() {
        return addrese;
    }

    public void setAddrese(String addrese) {
        this.addrese = addrese;
    }

    public int getAvis_id() {
        return avis_id;
    }

    public void setAvis_id(int avis_id) {
        this.avis_id = avis_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getNbravis() {
        return nbravis;
    }

    public void setNbravis(int nbravis) {
        this.nbravis = nbravis;
    }

    public int getNbrjaime() {
        return nbrjaime;
    }

    public void setNbrjaime(int nbrjaime) {
        this.nbrjaime = nbrjaime;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Boolean getAdministration() {
        return administration;
    }

    public void setAdministration(Boolean administration) {
        this.administration = administration;
    }

    public Boolean getTheme() {
        return theme;
    }

    public void setTheme(Boolean theme) {
        this.theme = theme;
    }

    public Boolean getVitesse() {
        return vitesse;
    }

    public void setVitesse(Boolean vitesse) {
        this.vitesse = vitesse;
    }

    public Boolean getActualite() {
        return actualite;
    }

    public void setActualite(Boolean actualite) {
        this.actualite = actualite;
    }

    public Boolean getOriginalite() {
        return originalite;
    }

    public void setOriginalite(Boolean originalite) {
        this.originalite = originalite;
    }

    public Boolean getAttractivite() {
        return attractivite;
    }

    public void setAttractivite(Boolean attractivite) {
        this.attractivite = attractivite;
    }

    public Boolean getSecurity() {
        return security;
    }

    public void setSecurity(Boolean security) {
        this.security = security;
    }

    public Boolean getExperience() {
        return experience;
    }

    public void setExperience(Boolean experience) {
        this.experience = experience;
    }

    public Boolean getControl() {
        return control;
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

  
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
        hash = 61 * hash + this.ref_compte;
        hash = 61 * hash + Objects.hashCode(this.nom);
        hash = 61 * hash + Objects.hashCode(this.description);
        hash = 61 * hash + Objects.hashCode(this.addrese);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final offre_experience other = (offre_experience) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.addrese, other.addrese)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "offre_experience{" + "id=" + id + ", ref_compte=" + ref_compte + ", nom=" + nom + ", description=" + description + ", url_image=" + url_image + ", addrese=" + addrese + ", avis_id=" + avis_id + ", region_id=" + region_id + ", rating=" + rating + ", catid=" + catid + ", nbravis=" + nbravis + ", nbrjaime=" + nbrjaime + ", datecreation=" + datecreation + ", recherche=" + recherche + ", administration=" + administration + ", theme=" + theme + ", vitesse=" + vitesse + ", actualite=" + actualite + ", originalite=" + originalite + ", attractivite=" + attractivite + ", security=" + security + ", experience=" + experience + ", control=" + control + '}';
    }

    public offre_experience() {
    }

    public offre_experience(int id, int ref_compte, String nom, String description, String url_image, String addrese, int avis_id, int region_id, int rating, int catid, int nbravis, int nbrjaime, Date datecreation, Boolean recherche, Boolean administration, Boolean theme, Boolean vitesse, Boolean actualite, Boolean originalite, Boolean attractivite, Boolean security, Boolean experience, Boolean control) {
        this.id = id;
        this.ref_compte = ref_compte;
        this.nom = nom;
        this.description = description;
        this.url_image = url_image;
        this.addrese = addrese;
        this.avis_id = avis_id;
        this.region_id = region_id;
        this.rating = rating;
        this.catid = catid;
        this.nbravis = nbravis;
        this.nbrjaime = nbrjaime;
        this.datecreation = datecreation;
        this.recherche = recherche;
        this.administration = administration;
        this.theme = theme;
        this.vitesse = vitesse;
        this.actualite = actualite;
        this.originalite = originalite;
        this.attractivite = attractivite;
        this.security = security;
        this.experience = experience;
        this.control = control;
    }

   
    
    
    
}
