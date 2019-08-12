package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.news.model.room.RoomDeclaration;
import com.alex44.fcbate.news.model.room.RoomNews;
import com.alex44.fcbate.news.model.room.RoomPress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomNewsRepoCache implements INewsRepoCache {
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public List<NewsItemDTO> getNews(int count) {
        final List<RoomNews> roomNewsList = DatabaseRoom.getInstance().getNewsDao().findLast(count);
        final List<NewsItemDTO> itemDTOList = new ArrayList<>();
        for (RoomNews roomNews : roomNewsList) {
            final Date date = new Date(roomNews.getCreated());
            final NewsItemDTO itemDTO = new NewsItemDTO(roomNews.getId(),
                    roomNews.getPhotoUrl(),
                    dateTimeFormat.format(date),
                    roomNews.getTitle(),
                    roomNews.getBrief());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public List<NewsItemDTO> getPress(int count) {
        final List<RoomPress> roomPressList = DatabaseRoom.getInstance().getPressDao().findLast(count);
        final List<NewsItemDTO> itemDTOList = new ArrayList<>();
        for (RoomPress roomPress : roomPressList) {
            final Date date = new Date(roomPress.getCreated());
            final NewsItemDTO itemDTO = new NewsItemDTO(roomPress.getId(),
                    roomPress.getPhotoUrl(),
                    dateTimeFormat.format(date),
                    roomPress.getTitle(),
                    roomPress.getBrief());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public List<NewsItemDTO> getDeclarations(int count) {
        final List<RoomDeclaration> roomDeclarationList = DatabaseRoom.getInstance().getDeclarationDao().findLast(count);
        final List<NewsItemDTO> itemDTOList = new ArrayList<>();
        for (RoomDeclaration roomDeclaration : roomDeclarationList) {
            final Date date = new Date(roomDeclaration.getCreated());
            final NewsItemDTO itemDTO = new NewsItemDTO(roomDeclaration.getId(),
                    roomDeclaration.getPhotoUrl(),
                    dateTimeFormat.format(date),
                    roomDeclaration.getTitle(),
                    roomDeclaration.getBrief());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public boolean putNews(List<NewsItemDTO> news) {
        final List<RoomNews> roomNewsList = new ArrayList<>();
        try {
            for (NewsItemDTO itemDTO : news) {
                final Date date = dateTimeFormat.parse(itemDTO.getCreated());
                final RoomNews roomNews = new RoomNews(itemDTO.getId(),
                        itemDTO.getPhotoUrl(),
                        date.getTime(),
                        itemDTO.getTitle(),
                        itemDTO.getBrief());
                roomNewsList.add(roomNews);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatabaseRoom.getInstance().getNewsDao().insert(roomNewsList);
        return true;
    }

    @Override
    public boolean putPress(List<NewsItemDTO> presses) {
        final List<RoomPress> roomPressList = new ArrayList<>();
        try {
            for (NewsItemDTO itemDTO : presses) {
                final Date date = dateTimeFormat.parse(itemDTO.getCreated());
                final RoomPress roomPress = new RoomPress(itemDTO.getId(),
                        itemDTO.getPhotoUrl(),
                        date.getTime(),
                        itemDTO.getTitle(),
                        itemDTO.getBrief());
                roomPressList.add(roomPress);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatabaseRoom.getInstance().getPressDao().insert(roomPressList);
        return true;
    }

    @Override
    public boolean putDeclarations(List<NewsItemDTO> declarations) {
        final List<RoomDeclaration> roomDeclarationList = new ArrayList<>();
        try {
            for (NewsItemDTO itemDTO : declarations) {
                final Date date = dateTimeFormat.parse(itemDTO.getCreated());
                final RoomDeclaration roomDeclaration = new RoomDeclaration(itemDTO.getId(),
                        itemDTO.getPhotoUrl(),
                        date.getTime(),
                        itemDTO.getTitle(),
                        itemDTO.getBrief());
                roomDeclarationList.add(roomDeclaration);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatabaseRoom.getInstance().getDeclarationDao().insert(roomDeclarationList);
        return true;
    }
}
