package com.example.demo.datacontrol.datafolder.model;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataFolder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String folderName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private DataFolder parent;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<DataFolder> child = new ArrayList<>();

    public void updateDataFolder(String folderName, User owner, LocalDateTime createDate, LocalDateTime updateDate) {
        this.folderName = folderName;
        this.owner = owner;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
    public void updateParentDataFolder(DataFolder inputParentDataFolder) {
        parent = inputParentDataFolder;
    }
    public void updateFolderName(String folderName) {
        this.folderName = folderName;
        this.updateDate = LocalDateTime.now();
    }
    public void deleteThisParentDataFolder(){
        deleteParentDFS(this);
    }
    private boolean deleteParentDFS(DataFolder inputDataFolder){
        inputDataFolder.deleteParent();

        if (inputDataFolder.getChild().isEmpty())
            return true;

        for(DataFolder item:inputDataFolder.getChild()){
            deleteParentDFS(item);
        }
        return true;
    }
    private void deleteParent(){
        this.parent = null;
    }

}
