package com.sid.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.TreeSet;

@Getter
@Setter
public class ItemFiltersDto {

    private TreeSet<String> brands = new TreeSet<>();

    private TreeSet<String> sizes = new TreeSet<>();
}
