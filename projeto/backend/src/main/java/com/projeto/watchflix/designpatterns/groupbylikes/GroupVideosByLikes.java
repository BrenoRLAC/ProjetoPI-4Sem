package com.projeto.watchflix.designpatterns.groupbylikes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum GroupVideosByLikes implements GroupByLikes {
    VIEWCOUNT5 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();

            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");

                if (viewCount != null && viewCount > 0 && viewCount <= 5) {
                    viewCountVideos.add(video);
                }
            }

            return viewCountVideos;
        }
    },
    VIEWCOUNT10 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();


            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");
                if (viewCount != null && viewCount > 5 && viewCount <= 10) {
                    viewCountVideos.add(video);
                }
            }

            return viewCountVideos;
        }
    },
    VIEWCOUNT15 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();


            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");
                if (viewCount != null && viewCount > 10 && viewCount <= 15) {
                    viewCountVideos.add(video);
                }
            }

            return viewCountVideos;
        }
    },
    VIEWCOUNT20 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();

            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");
                if (viewCount != null && viewCount > 15 && viewCount <= 20) {
                    viewCountVideos.add(video);
                }
            }


            return viewCountVideos;
        }
    },
    VIEWCOUNT25 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();


            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");
                if (viewCount != null && viewCount > 20 && viewCount < 25) {
                    viewCountVideos.add(video);
                }
            }

            return viewCountVideos;
        }
    },

    VIEWCOUNT30 {
        @Override
        public List<Map<String, Object>> calculateViewCount(List<Map<String, Object>> videos) {
            List<Map<String, Object>> viewCountVideos = new ArrayList<>();


            for (Map<String, Object> video : videos) {
                Integer viewCount = (Integer) video.get("viewCount");
                if (viewCount != null && viewCount > 30) {
                    viewCountVideos.add(video);
                }
            }

            return viewCountVideos;
        }
    },

}
