package com.nocountry.crm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> content;
    private int pageNumber;
    private boolean last;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
}
