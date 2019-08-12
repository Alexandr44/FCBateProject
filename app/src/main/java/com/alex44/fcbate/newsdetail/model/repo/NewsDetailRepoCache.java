package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;
import com.alex44.fcbate.newsdetail.model.room.RoomDeclarationDetail;
import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;
import com.alex44.fcbate.newsdetail.model.room.RoomPressDetail;

public class NewsDetailRepoCache implements INewsDetailRepoCache {

    @Override
    public NewsItemDetailDTO getNewsDetail(Long id) {
        final RoomNewsDetail roomNewsDetail = DatabaseRoom.getInstance().getNewsDetailDao().findById(id);
        if (roomNewsDetail == null) return null;
        final NewsItemDetailDTO newsDetailDTO = new NewsItemDetailDTO();
        newsDetailDTO.setBrief(roomNewsDetail.getBrief());
        newsDetailDTO.setContent(roomNewsDetail.getContent());
        newsDetailDTO.setDateCreated(roomNewsDetail.getDateCreated());
        newsDetailDTO.setId(roomNewsDetail.getId());
        newsDetailDTO.setPhotoUrl(roomNewsDetail.getPhotoUrl());
        newsDetailDTO.setTitle(roomNewsDetail.getTitle());
        return newsDetailDTO;
    }

    @Override
    public boolean putNewsDetail(NewsItemDetailDTO roomDeclarationDetail) {
        final RoomNewsDetail roomNewsDetail = new RoomNewsDetail();
        roomNewsDetail.setBrief(roomDeclarationDetail.getBrief());
        roomNewsDetail.setContent(roomDeclarationDetail.getContent());
        roomNewsDetail.setDateCreated(roomDeclarationDetail.getDateCreated());
        roomNewsDetail.setId(roomDeclarationDetail.getId());
        roomNewsDetail.setPhotoUrl(roomDeclarationDetail.getPhotoUrl());
        roomNewsDetail.setTitle(roomDeclarationDetail.getTitle());
        DatabaseRoom.getInstance().getNewsDetailDao().insert(roomNewsDetail);
        return true;
    }

    @Override
    public NewsItemDetailDTO getPressDetail(Long id) {
        final RoomPressDetail roomPressDetail = DatabaseRoom.getInstance().getPressDetailDao().findById(id);
        if (roomPressDetail == null) return null;
        final NewsItemDetailDTO newsDetailDTO = new NewsItemDetailDTO();
        newsDetailDTO.setBrief(roomPressDetail.getBrief());
        newsDetailDTO.setContent(roomPressDetail.getContent());
        newsDetailDTO.setDateCreated(roomPressDetail.getDateCreated());
        newsDetailDTO.setId(roomPressDetail.getId());
        newsDetailDTO.setPhotoUrl(roomPressDetail.getPhotoUrl());
        newsDetailDTO.setTitle(roomPressDetail.getTitle());
        return newsDetailDTO;
    }

    @Override
    public boolean putPressDetail(NewsItemDetailDTO itemDetailDTO) {
        final RoomPressDetail roomPressDetail = new RoomPressDetail();
        roomPressDetail.setBrief(itemDetailDTO.getBrief());
        roomPressDetail.setContent(itemDetailDTO.getContent());
        roomPressDetail.setDateCreated(itemDetailDTO.getDateCreated());
        roomPressDetail.setId(itemDetailDTO.getId());
        roomPressDetail.setPhotoUrl(itemDetailDTO.getPhotoUrl());
        roomPressDetail.setTitle(itemDetailDTO.getTitle());
        DatabaseRoom.getInstance().getPressDetailDao().insert(roomPressDetail);
        return true;
    }

    @Override
    public NewsItemDetailDTO getDeclarationsDetail(Long id) {
        final RoomDeclarationDetail roomDeclarationDetail = DatabaseRoom.getInstance().getDeclarationDetailDao().findById(id);
        if (roomDeclarationDetail == null) return null;
        final NewsItemDetailDTO newsDetailDTO = new NewsItemDetailDTO();
        newsDetailDTO.setBrief(roomDeclarationDetail.getBrief());
        newsDetailDTO.setContent(roomDeclarationDetail.getContent());
        newsDetailDTO.setDateCreated(roomDeclarationDetail.getDateCreated());
        newsDetailDTO.setId(roomDeclarationDetail.getId());
        newsDetailDTO.setPhotoUrl(roomDeclarationDetail.getPhotoUrl());
        newsDetailDTO.setTitle(roomDeclarationDetail.getTitle());
        return newsDetailDTO;
    }

    @Override
    public boolean putDeclarationsDetail(NewsItemDetailDTO itemDetailDTO) {
        final RoomDeclarationDetail roomDeclarationDetail = new RoomDeclarationDetail();
        roomDeclarationDetail.setBrief(itemDetailDTO.getBrief());
        roomDeclarationDetail.setContent(itemDetailDTO.getContent());
        roomDeclarationDetail.setDateCreated(itemDetailDTO.getDateCreated());
        roomDeclarationDetail.setId(itemDetailDTO.getId());
        roomDeclarationDetail.setPhotoUrl(itemDetailDTO.getPhotoUrl());
        roomDeclarationDetail.setTitle(itemDetailDTO.getTitle());
        DatabaseRoom.getInstance().getDeclarationDetailDao().insert(roomDeclarationDetail);
        return true;
    }

}

