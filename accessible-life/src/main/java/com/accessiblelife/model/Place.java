package com.accessiblelife.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "places") // <-- THIS is important
public class Place {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id") // add this
    private Long id;

    @NotBlank
   /* private String placeName;

    private String specificAddress;

    private Boolean hasRamp = false;

    private Boolean isVerified = false;

    private Integer verificationVote = 0;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;*/
    @Column(name = "place_name")
    private String placeName;

    @Column(name = "specific_address")
    private String specificAddress;

    @Column(name = "has_ramp")
    private Boolean hasRamp = false;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "verification_vote")
    private Integer verificationVote = 0;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @Column(name = "has_accessible_toilet")
    private Boolean hasAccessibleToilet = false;  // default value
    @Column(name = "has_braille_signage")
    private Boolean hasBrailleSignage = false;
    @Column(name = "has_elevator")
    private Boolean hasElevator = false;  // default value



    // ---------------- Getters & Setters ----------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public Boolean getHasRamp() {
        return hasRamp;
    }

    public void setHasRamp(Boolean hasRamp) {
        this.hasRamp = hasRamp;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getVerificationVote() {
        return verificationVote;
    }

    public void setVerificationVote(Integer verificationVote) {
        this.verificationVote = verificationVote;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
    public Boolean getHasAccessibleToilet() {
        return hasAccessibleToilet;
    }

    public void setHasAccessibleToilet(Boolean hasAccessibleToilet) {
        this.hasAccessibleToilet = hasAccessibleToilet;
    }
    public Boolean getHasBrailleSignage() {
        return hasBrailleSignage;
    }

    public void setHasBrailleSignage(Boolean hasBrailleSignage) {
        this.hasBrailleSignage = hasBrailleSignage;
    }
    public Boolean getHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(Boolean hasElevator) {
        this.hasElevator = hasElevator;
    }


}

