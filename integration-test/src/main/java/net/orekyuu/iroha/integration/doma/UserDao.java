package net.orekyuu.iroha.integration.doma;

import java.util.List;
import org.seasar.doma.*;

@Dao
public interface UserDao {

  @Insert
  int insert(UserEntity entity);

  @Update
  int update(UserEntity entity);

  @Update
  int delete(UserEntity entity);

  @Select
  @Sql("select * from users")
  List<UserEntity> selectAll();

  @Select
  @Sql("select * from users where id in /*ids*/(1, 2)")
  List<UserEntity> selectByIds(List<Integer> ids);

  @BatchInsert
  int[] batchInsert(List<UserEntity> entity);
}
