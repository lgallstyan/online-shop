package grid.onlineshop.sweetland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "address")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //sequence
    private Long id;

    @Column(name = "address_line")
    private String addressLine;

    private String country;

    private String province;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address") // Bidirectional
    private List<Orders> orderList;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "addressList") // Always Bidirectional
    private List<User> userList;

}
