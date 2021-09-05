package com.boz.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;

	public PageData() {
        super(new ArrayList<T>());
    }

    public PageData(List<T> content) {
        super(content);
    }

    public PageData(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageData(Page<T> content) {
        this(content.getContent(), content.getPageable(), content.getTotalElements());
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageData(@JsonProperty("data") List<T> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") Long totalElements,
            @JsonProperty("pageable") JsonNode pageable,
            @JsonProperty("last") boolean last,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("sort") JsonNode sort,
            @JsonProperty("first") boolean first,
            @JsonProperty("numberOfElements") int numberOfElements) {
        this(content, size > 0 ? PageRequest.of(number, size) : Pageable.unpaged(), totalElements);
    }

    @Override
    @JsonIgnore
    public List<T> getContent() {
        return super.getContent();
    }

    public List<T> getData() {
        return super.getContent();
    }

}
