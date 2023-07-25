package com.hei.project2p1.repository.dao;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.repository.dao.utils.SearchInDb;
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

    public List<Employee> findByCriteria(String firstName, String lastName, String function, String gender,
                                         Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName!= null && firstName.length()>0){
                predicates.add(SearchInDb.predicateForMatchText(builder,root,"firstName",firstName));
        }
        if (lastName!= null && lastName.length()>0){
            predicates.add(SearchInDb.predicateForMatchText(builder,root,"lastName",lastName));
        }

        if (function!= null && function.length()>0){
            predicates.add(SearchInDb.predicateForMatchText(builder,root,"function",function));
        }
        /*
        if (gender!= null && gender.length()>0){
            if (gender.equals("H") || gender.equals("F")) {
                predicates.add(SearchInDb.predicateForEquals(builder, root, "gender", gender));
            }
        }
         */

        query
                .where(builder.and(predicates.toArray(new Predicate[0])))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return entityManager.createQuery(query)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
