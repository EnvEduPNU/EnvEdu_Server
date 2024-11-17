package com.example.demo.jpa.datacontrol.datachunk.model.parent;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class Data {
    @Column(columnDefinition = "BINARY(16)")
    private UUID dataUUID;
    private LocalDateTime saveDate;
    private String memo;
    private String title;
    private DataEnumTypes dataLabel;

    public void updateBasicAttribute(UUID uuid, LocalDateTime saveDate, String memo, String title, DataEnumTypes dataLabel) {
        this.dataUUID = uuid;
        this.saveDate = saveDate;
        this.memo = memo;
        this.title = title;
        this.dataLabel = dataLabel;
    }
}
