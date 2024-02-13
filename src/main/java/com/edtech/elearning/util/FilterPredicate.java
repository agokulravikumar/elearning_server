package com.edtech.elearning.util;

import com.edtech.elearning.enums.SpecialSymbol;
import com.edtech.elearning.model.FilterCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.edtech.elearning.enums.SpecialSymbol.*;

@RequiredArgsConstructor
public class FilterPredicate<T> implements Specification<T> {
    private final transient FilterCriteria filterCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (filterCriteria != null) {
            List<Predicate> predicates = new ArrayList<>();

            buildPredicates(root, builder, filterCriteria, predicates);

            return builder.and(predicates.toArray(Predicate[]::new));
        }

        return null;
    }

    private void buildPredicates(Root<T> root, CriteriaBuilder builder, FilterCriteria filterCriteria, List<Predicate> predicates) {
        Predicate predicate = getPredicates(root, builder, filterCriteria);
        if (predicate != null) {
            predicates.add(predicate);
        }
    }


    private Predicate getPredicates(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        Predicate predicate = null;
        if (GREATER_THAN_OR_EQUAL.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = checkForDate(root, builder, criteria, GREATER_THAN_OR_EQUAL);
        } else if (GREATER_THAN.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = checkForDate(root, builder, criteria, GREATER_THAN);
        } else if (LESS_THAN_OR_EQUAL.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = checkForDate(root, builder, criteria, LESS_THAN_OR_EQUAL);
        } else if (LESS_THAN.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = checkForDate(root, builder, criteria, LESS_THAN);
        } else if (EQUALS.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
        } else if (LIKE.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        } else if (NOT_LIKE.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = builder.notLike(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        } else if (IN.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = getInPredicate(root, builder, criteria);
        } else if (NOT_IN.getPlain().equalsIgnoreCase(criteria.getOperation())) {
            predicate = getInPredicate(root, builder, criteria).not();
        }

        return predicate;
    }

    private Predicate getInPredicate(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        CriteriaBuilder.In<Object> inClause = builder.in(root.get(criteria.getKey()));
        String[] values = criteria.getValue().split(TILDE.getPlain());
        for (String value : values) {
            if (root.get(criteria.getKey()).getJavaType().getName().equalsIgnoreCase(Integer.class.getName())) {
                inClause.value(Integer.parseInt(value.trim()));
            } else if (root.get(criteria.getKey()).getJavaType().getName().equalsIgnoreCase(Double.class.getName())) {
                inClause.value(Double.parseDouble(value.trim()));
            } else {
                inClause.value(value.trim());
            }
        }

        return inClause;
    }

    private Predicate checkForDate(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria, SpecialSymbol condition) {
        Predicate predicate = null;

        switch (condition) {
            case GREATER_THAN_OR_EQUAL:
                predicate = greaterThanOrEqual(root, builder, criteria);
                break;

            case GREATER_THAN:
                predicate = greaterThan(root, builder, criteria);
                break;

            case LESS_THAN_OR_EQUAL:
                predicate = lessThanOrEqual(root, builder, criteria);
                break;

            case LESS_THAN:
                predicate = lessThan(root, builder, criteria);
                break;
            default:
        }

        return predicate;
    }

    private Predicate lessThan(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        Predicate predicate;
        if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDate.class)) {
            predicate = builder.lessThan(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
        } else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDateTime.class)) {
            predicate = builder.lessThan(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
        } else {
            predicate = builder.lessThan(root.get(criteria.getKey()), criteria.getValue());
        }
        return predicate;
    }

    private Predicate lessThanOrEqual(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        Predicate predicate;
        if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDate.class)) {
            predicate = builder.lessThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
        } else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDateTime.class)) {
            predicate = builder.lessThanOrEqualTo(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
        } else {
            predicate = builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue());
        }
        return predicate;
    }

    private Predicate greaterThanOrEqual(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        Predicate predicate;
        if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDate.class)) {
            predicate = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
        } else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDateTime.class)) {
            predicate = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
        } else {
            predicate = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue());
        }
        return predicate;
    }

    private Predicate greaterThan(Root<T> root, CriteriaBuilder builder, FilterCriteria criteria) {
        Predicate predicate;
        if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDate.class)) {
            predicate = builder.greaterThan(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
        } else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(LocalDateTime.class)) {
            predicate = builder.greaterThan(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
        } else {
            predicate = builder.greaterThan(root.get(criteria.getKey()), criteria.getValue());
        }
        return predicate;
    }
}
