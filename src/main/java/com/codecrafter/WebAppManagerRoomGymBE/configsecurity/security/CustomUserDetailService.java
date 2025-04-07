package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security;

import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    private final NguoiDungService nguoiDungService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = nguoiDungService.findByUserName(userName).orElseThrow();
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getVaiTro().getTenVaiTro()));
        logger.info("User roles: {}", authorities);
        return UserPrincipal.builder()
                .userId(user.getMaNguoiDung())
                .userName(user.getTenNguoiDung())
                .passWord(user.getMatKhauNguoiDung())
                .authorities(List.of(new SimpleGrantedAuthority(user.getVaiTro().getTenVaiTro())))
                .build()
                ;
    }
}
