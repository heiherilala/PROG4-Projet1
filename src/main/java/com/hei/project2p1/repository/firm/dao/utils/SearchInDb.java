package com.hei.project2p1.repository.firm.dao.utils;

import com.hei.project2p1.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SearchInDb {
    public static Predicate predicateForMatchText(CriteriaBuilder builder, Root<?> root, String entityAttribute, String toSearch){
        return builder.or(
                builder.like(builder.lower(root.get(entityAttribute)), "%" + toSearch.toLowerCase() + "%"),
                builder.like(root.get(entityAttribute), "%" + toSearch + "%")
        );
    }

    public static Predicate predicateForEquals(CriteriaBuilder builder, Root<?> root, String entityAttribute, String toSearch){
        return builder.or(
                builder.equal(builder.lower(root.get(entityAttribute)), toSearch.toLowerCase()),
                builder.equal(root.get(entityAttribute), toSearch)
        );
    }
    public static Predicate predicateForEquals(CriteriaBuilder builder, Root<?> root, String entityAttribute, Employee.Gender toSearch){
        return builder.equal(root.get(entityAttribute), toSearch);
    }
}
