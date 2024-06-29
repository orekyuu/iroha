package net.orekyuu.iroha.integration.doma;

import org.seasar.doma.*;

@Entity
@Table(name = "users")
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  public Long id;

  public String name;
  public int age;

  public UserEntity() {}

  public UserEntity(Long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public UserEntity(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
