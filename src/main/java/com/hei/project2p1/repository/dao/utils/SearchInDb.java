package com.hei.project2p1.repository.dao.utils;

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

}
