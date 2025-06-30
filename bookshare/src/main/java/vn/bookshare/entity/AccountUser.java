package vn.bookshare.entity;

import vn.bookshare.common.base.Auditable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.bookshare.common.enums.Role;

@Entity
@Table(name = "account_user")
@AllArgsConstructor
@NoArgsConstructor
public class AccountUser extends Auditable implements UserDetails, Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "username", length = 255)
    @Setter
    private String username;
    @Basic(optional = false)
    @Column(name = "password", length = 255, nullable = false)
    @Setter
    private String password;
    @Basic(optional = false)
    @Column(name = "enable", nullable = false)
    @Setter
    private boolean enable = true;
    @Basic(optional = false)
    @Column(name = "role", length = 255, nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Role role = Role.USER;
    @Basic(optional = true)
    @Column(name = "note", nullable = true)
    @Getter
    @Setter
    private String note;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

}
