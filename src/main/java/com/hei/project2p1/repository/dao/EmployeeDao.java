package com.hei.project2p1.repository.dao;

import com.hei.project2p1.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class EmployeeDao {
    private EntityManager entityManager;

    public List<Employee> findByCriteria(String firstName, String lastName,
                                         Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName.length()>0){
            Predicate hasUserFirstName =
                    builder.or(
                            builder.like(builder.lower(root.get("firstName")), "%" + firstName + "%"),
                            builder.like(root.get("firstName"), "%" + firstName + "%")
                    );
            predicates.add(hasUserFirstName);
        }

        Predicate hasUserLastName =
                builder.or(
                        builder.like(builder.lower(root.get("lastName")), "%" + lastName + "%"),
                        builder.like(root.get("lastName"), "%" + lastName + "%")
                );

        query
                .where(builder.and())
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return entityManager.createQuery(query)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
