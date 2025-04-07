package com.codecrafter.WebAppManagerRoomGymBE.utils;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class getCurrentUser {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return userDetails.getUserId(); // Lấy ID từ đối tượng MyUserDetails
        }
        throw new IllegalStateException("Không thể lấy thông tin người dùng đang đăng nhập");
    }
}
