package com.hei.project2p1.repository.cnaps.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity(name = "company")
/*
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contactEmail"},name = "unique_email"),
        @UniqueConstraint(columnNames = {"nif"},name = "unique_nif"),
        @UniqueConstraint(columnNames = {"stat"},name = "unique_stat"),
        @UniqueConstraint(columnNames = {"rcs"},name = "unique_rcs"),
})
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCnapsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank()
    //@Column(name = "company_name")
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String companyDescription;

    private String slogan;

    @NotBlank()
    private String address;

    @Email()
    private String contactEmail;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<PhoneCnapsEntity> phones;

    /*@Lob
    @Column(columnDefinition = "bytea")
    private byte[] logo;
     */
    private String nif;

    private String stat;

    private String rcs;

}
