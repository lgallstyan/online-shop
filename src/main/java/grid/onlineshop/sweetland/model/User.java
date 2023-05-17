package grid.onlineshop.sweetland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grid.onlineshop.sweetland.token.Token;
import grid.onlineshop.sweetland.util.RoleSetConverter;
import grid.onlineshop.sweetland.util.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
//    @Convert(converter = RoleSetConverter.class)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Cart cart;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    private Address address;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL) // Always Bidirectional
    private List<Address> addressList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user") // Bidirectional
    private List<Orders> orderList;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = getRole();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.name()));
//        }
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;   ///otherwise not to be able to connect user
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
