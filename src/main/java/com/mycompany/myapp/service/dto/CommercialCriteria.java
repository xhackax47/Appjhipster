package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Commercial} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CommercialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commercials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstname;

    private StringFilter name;

    public CommercialCriteria() {
    }

    public CommercialCriteria(CommercialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstname = other.firstname == null ? null : other.firstname.copy();
        this.name = other.name == null ? null : other.name.copy();
    }

    @Override
    public CommercialCriteria copy() {
        return new CommercialCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstname() {
        return firstname;
    }

    public void setFirstname(StringFilter firstname) {
        this.firstname = firstname;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialCriteria that = (CommercialCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstname,
        name
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommercialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstname != null ? "firstname=" + firstname + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
            "}";
    }

}