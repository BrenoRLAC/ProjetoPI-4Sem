package com.projeto.watchflix.repository;

import com.projeto.watchflix.model.Channel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChannelRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Map<String, Object>> findAllChannels() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Channel> root = criteriaQuery.from(Channel.class);

        criteriaQuery.multiselect(
                root.get("channelId"),
                root.get("profileImage"),
                root.get("firstName"),
                root.get("lastName"),
                root.get("fullName")
        );

        Predicate isActivePredicate = criteriaBuilder.equal(root.get("active"), 1);
        criteriaQuery.where(isActivePredicate);

        List<Object[]> channelData = em.createQuery(criteriaQuery).getResultList();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : channelData) {
            Map<String, Object> channelMap = new HashMap<>();
            channelMap.put("id", row[0]);
            channelMap.put("profileImage", row[1]);
            channelMap.put("firstName", row[2]);
            channelMap.put("lastName", row[3]);
            channelMap.put("fullName", row[4]);

            result.add(channelMap);
        }

        return result;
    }


    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public Object findChannelById(long channelId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Channel> root = criteriaQuery.from(Channel.class);

        criteriaQuery.multiselect(
                root.get("channelId"),
                root.get("profileImage"),
                root.get("firstName"),
                root.get("lastName"),
                root.get("fullName"),
                root.get("emailAddress")
        );


        Predicate isActivePredicate = criteriaBuilder.equal(root.get("active"), 1);
        Predicate videoIdPredicate = criteriaBuilder.equal(root.get("channelId"), channelId);
        criteriaQuery.where(criteriaBuilder.and(isActivePredicate, videoIdPredicate));

        TypedQuery<Object[]> query = em.createQuery(criteriaQuery);
        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
        }

        Object[] result = results.get(0);

        Map<String, Object> videoMap = new HashMap<>();
        videoMap.put("id", result[0]);
        videoMap.put("profileImage", result[1]);
        videoMap.put("firstName", result[2]);
        videoMap.put("fullName", result[3]);
        videoMap.put("lastName", result[4]);
        videoMap.put("emailAddress", result[5]);

        return videoMap;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteChannel(long id) {
        Channel channel = em.find(Channel.class, id);
        if (channel != null) {
            em.remove(channel);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void registerChannel(Channel c) {
        em.persist(c);


    }
}
