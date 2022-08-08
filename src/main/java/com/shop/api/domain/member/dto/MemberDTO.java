package com.shop.api.domain.member.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO implements UserDetails {
    @NotBlank(message = "UE102")
    private String memberId;
    @NotBlank(message = "UE103")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "UE104")
    private String memberPw;
    @NotBlank(message = "UE105")
    private String memberNm;
    @NotBlank(message = "UE106")
    private String memberPhoneNum;
    @NotBlank(message = "UE107")
    @Email(message = "UE108")
    private String memberEmail;
    @NotBlank(message = "UE109")
    private String memberAddr;
    @NotBlank(message = "UE110")
    private String memberBirth;
    private List<String> memberRoles;
    private String createTime;
    private String lastLogin;
    private String passChange;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.memberRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
