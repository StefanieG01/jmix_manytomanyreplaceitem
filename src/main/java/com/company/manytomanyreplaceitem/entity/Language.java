package com.company.manytomanyreplaceitem.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "LANGUAGE_")
@Entity(name = "Language_")
public class Language
{
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @InstanceName
    @Column(name = "LANGUAGE_", nullable = false)
    @NotNull
    private String name;

    public String getName() {return name;}

    public void setName(String language) {this.name = language;}

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}
}