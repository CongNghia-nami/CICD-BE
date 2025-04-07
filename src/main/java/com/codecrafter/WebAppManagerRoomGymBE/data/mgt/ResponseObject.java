package com.codecrafter.WebAppManagerRoomGymBE.data.mgt;
import lombok.Data;

@Data
public class ResponseObject<T> {

    private String status;
    private String messages;
    private T data;
}
