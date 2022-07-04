package com.hitomi.crawler.model;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HManga {

    private String name;
    private String artist;
    private String group;
    private String type;
    private String language;
    private String series;
    private List<String> tags;

}
