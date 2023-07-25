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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class EmployeeDao {
    private EntityManager entityManager;

    public List<Employee> findByCriteria(String firstName, String lastName, String function, String gender, LocalDate entranceDateAfter, LocalDate entranceDateBefore,
                                         LocalDate leaveDateAfter, LocalDate leaveDateBefore,
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

        if (gender!= null && gender.length()>0){
            Employee.Gender g = gender.equals("H")? Employee.Gender.H:(gender.equals("F")?Employee.Gender.F:null);
            if (g!=null){
                predicates.add(builder.equal(root.get("gender"),g));
            }
        }

        if (entranceDateAfter != null ){
            predicates.add(builder.greaterThanOrEqualTo(root.get("hiringDate"),entranceDateAfter));
        }
        if (entranceDateBefore != null ){
            predicates.add(builder.lessThan(root.get("hiringDate"),entranceDateBefore));
        }


        if (leaveDateAfter!= null ){
            predicates.add(builder.greaterThanOrEqualTo(root.get("departureDate"),leaveDateAfter));
        }
        if (leaveDateBefore != null ){
            predicates.add(builder.lessThan(root.get("departureDate"),leaveDateBefore));
        }

        query
                .where(builder.and(predicates.toArray(new Predicate[0])))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return entityManager.createQuery(query)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
