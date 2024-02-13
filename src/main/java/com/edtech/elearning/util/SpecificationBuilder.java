package com.edtech.elearning.util;

import com.edtech.elearning.model.FilterCriteria;
import com.edtech.elearning.model.Filters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SpecificationBuilder<T> {

    private final Filters filters;

    public Specification<T> getSpecification() {
        if (filters != null && filters.getFilterCriteria() != null && !filters.getFilterCriteria().isEmpty()) {
            Map<FilterCriteria, Specification> specificationMap = new HashMap();

            for (FilterCriteria criteria : filters.getFilterCriteria()) {
                specificationMap.put(criteria, new FilterPredicate<>(criteria));
            }

            Specification specification = specificationMap.get(filters.getFilterCriteria().get(0));

            for (var i = 1; i < filters.getFilterCriteria().size(); i++) {
                FilterCriteria filterCriteria = filters.getFilterCriteria().get(i);

                Specification spec = specificationMap.get(filterCriteria);

                specification = Specification.where(specification).and(spec);
            }

            return specification;
        }

        return null;
    }
}
