package com.hei.project2p1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Company implements Serializable {
    private String id;

    private String companyName;

    private String companyDescription;

    private String slogan;

    private String address;

    private String contactEmail;

    private List<Phone> phones;

    private byte[] logo;

    private String nif;

    private String stat;

    private String rcs;

}
