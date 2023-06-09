package com.example.demo.device.dto.response;

import com.example.demo.device.model.UserDevice;
import lombok.Getter;

import java.util.*;

@Getter
public class RelatedUserDeviceListDTO {
    private final List<RelatedUserDeviceElemWithUsername> relatedUserDeviceList;

    public RelatedUserDeviceListDTO(List<UserDevice> devices) {
        Map<String, List<RelatedUserDeviceElem>> map = new HashMap<>();

        for(UserDevice device : devices) {
            if(!map.containsKey(device.getUser().getUsername())) {
                List<RelatedUserDeviceElem> elem = new ArrayList<>();
                elem.add(new RelatedUserDeviceElem(device.getName(), device.getMac()));
                map.put(device.getUser().getUsername(), elem);
                continue;
            }
            map.get(device.getUser().getUsername()).add(new RelatedUserDeviceElem(device.getName(), device.getMac()));
        }

        List<RelatedUserDeviceElemWithUsername> list = new ArrayList<>();
        Set<String> keys = map.keySet();
        for(String key : keys) {
            list.add(new RelatedUserDeviceElemWithUsername(map, key));
        }

        this.relatedUserDeviceList = list;
    }

    @Getter
    private static class RelatedUserDeviceElem {
        private final String deviceName;
        private final String mac;

        private RelatedUserDeviceElem(String deviceName, String mac) {
            this.deviceName = deviceName;
            this.mac = mac;
        }
    }

    @Getter
    private static class RelatedUserDeviceElemWithUsername {
        private final String username;
        private final List<RelatedUserDeviceElem> elements;

        private RelatedUserDeviceElemWithUsername(Map<String, List<RelatedUserDeviceElem>> map, String username) {
            this.username = username;
            this.elements = map.get(username);
        }
    }
}
