package com.projeto.watchflix.designpatterns.groupbylikes;

import java.util.List;
import java.util.Map;

public interface GroupByLikes {
    List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos);
}
