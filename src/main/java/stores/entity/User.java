package stores.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "users_table", uniqueConstraints =
       @UniqueConstraint(columnNames = "userName"))
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "user_name")
   private String userName;

   @Column(name = "full_name")
   private String fullName;

   private String street;
   private String city;
   private String zip;
   @Column(name = "phone_Number")
   private String phoneNumber;
   private String password;

   @ManyToMany(fetch = FetchType.EAGER, 
                   cascade = CascadeType.ALL)
   @JoinTable(name = "users_roles", 
       joinColumns = @JoinColumn(name = "user_id", 
         referencedColumnName = "id"), 
           inverseJoinColumns = @JoinColumn
              (name = "role_id", 
                 referencedColumnName = "id"))
   private Collection<Role> roles;


   public User() {

   }

   public User(String userName, String fullName,
         String street, String city, String zip, String phoneNumber, String password,
                   Collection<Role> roles) {

      this.userName = userName;
      this.fullName = fullName;
      this.street = street;
      this.city = city;
      this.zip = zip;
      this.phoneNumber = phoneNumber;
      this.password = password;
      this.roles = roles;
   }

}