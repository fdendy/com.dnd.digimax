package com.dnd.digimax.feature.playback.pop.storage;

import java.util.List;

public class PopRepository {

    private final PopDao dao;

    public PopRepository(
            PopDao dao) {

        this.dao = dao;
    }

    public void save(
            PopEntity entity) {

        dao.insert(entity);
    }

    public List<PopEntity> getPending() {

        return dao.getPending();
    }

    public void markUploaded(
            long id) {

        dao.markUploaded(id);
    }
}