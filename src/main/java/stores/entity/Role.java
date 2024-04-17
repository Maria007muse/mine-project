package stores.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;

   public Role() {
   }

   public Role(String name) {
      super();
      this.name = name;
   }
}