package com.example.onfieldtbs_android.utils.mappers;

import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentDate {
    public UUID id;
    public String message;
    public LocalDateTime createdAt;
    public Incidence incidence;
    public Technician technician;

    public CommentDate() {
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public Technician getTechnician() {
        return technician;
    }

    private static CommentDate toCommentWithDateType(Comment in){
        CommentDate out = new CommentDate();
        out.id = in.getId();
        out.message = in.getMessage();
        out.incidence = in.getIncidence();
        out.technician = in.getTechnician();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        out.createdAt = LocalDateTime.parse(in.getCreatedAt(), formatter);
        return out;
    }

    public static List<CommentDate> toListOfCommentWithDateType(List<Comment> comments){
        return comments.stream()
                .map(CommentDate::toCommentWithDateType)
                .sorted(Comparator.comparing(CommentDate::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

}
