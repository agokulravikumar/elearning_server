package com.edtech.elearning.model;

import com.edtech.elearning.constants.Constants;
import com.edtech.elearning.constants.MsgConstants;
import com.edtech.elearning.exception.ValidationException;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Filters {

    private List<FilterCriteria> filterCriteria = new ArrayList<>();

    private String[] filter;

    @SneakyThrows
    public void setFilter(String filterString) {
        String[] filters = filterString.split("\\$\\$");

        for (String filterItem : filters) {
            generateFilterCriteria(filterItem.split(Constants.COMMA));
        }
    }

    private void generateFilterCriteria(String[] filters) throws ValidationException {
        validateFilters(filters);

        Map<Integer, FilterCriteria> criteriaMap = new HashMap<>();

        for (var i = 0; i < filters.length; i += 3) {
            String operation = filters[i + 1];

            FilterCriteria criteria = new FilterCriteria();
            criteria.setId(i + Math.random());
            criteria.setKey(filters[i]);
            criteria.setOperation(operation);
            criteria.setValue(filters[i + 2]);

            criteriaMap.put(i, criteria);
        }

        filterCriteria.add(criteriaMap.get(0));
    }

    private void validateFilters(String[] filters) throws ValidationException {
        if (filters.length % 3 != 0) {
            throw new ValidationException(MsgConstants.INVALID_FILTER_CRITERIA);
        }

        for (String filterStr : filters) {
            if (StringUtils.isBlank(filterStr)) {
                throw new ValidationException(MsgConstants.EMPTY_OR_INVALID_FILTER);
            }
        }
    }
}
