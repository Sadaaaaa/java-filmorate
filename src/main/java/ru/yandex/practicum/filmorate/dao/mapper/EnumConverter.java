package ru.yandex.practicum.filmorate.dao.mapper;

import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import javax.persistence.AttributeConverter;

public class EnumConverter implements AttributeConverter<FriendshipStatus, String> {

    @Override
    public String convertToDatabaseColumn(FriendshipStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public FriendshipStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return FriendshipStatus.valueOf(dbData);
    }
}
